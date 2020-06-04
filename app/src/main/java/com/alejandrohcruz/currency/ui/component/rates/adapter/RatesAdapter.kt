package com.alejandrohcruz.currency.ui.component.rates.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alejandrohcruz.currency.databinding.RowRateBinding
import com.alejandrohcruz.currency.model.Currency
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.ui.base.listeners.RecyclerItemListener
import com.alejandrohcruz.currency.ui.component.rates.activity.RatesViewModel
import com.alejandrohcruz.currency.utils.L
import kotlin.collections.ArrayList

class RatesAdapter(
    private val ratesViewModel: RatesViewModel
) :
    RecyclerView.Adapter<RateViewHolder>() {

    //region properties
    private val TAG = this.javaClass.simpleName

    private var currenciesList: List<Currency> = ArrayList()
    private var currenciesPendingList: List<Currency>? = null

    private var rowBeingEdited: Int? = null

    private var isBeingScrolled = false

    private var attachedRecyclerView : RecyclerView? = null

    private var scrollingStateListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(
            recyclerView: RecyclerView,
            state: Int
        ) {
            isBeingScrolled = when (state) {
                RecyclerView.SCROLL_STATE_DRAGGING -> true
                RecyclerView.SCROLL_STATE_IDLE -> false
                RecyclerView.SCROLL_STATE_SETTLING -> true
                else -> false
            }
            if (!isBeingScrolled) {
                currenciesPendingList?.let { onRatesUpdated(it) }
            }
        }
    }
    //endregion

    private val onItemInteractionListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(
            currency: CurrencyEnum,
            position: Int,
            newBaseMultiplier: Double?
        ) {
            if (ratesViewModel.shouldAllowItemToBeClicked(position)) {

                // TODO: Use coroutines
                Handler().postDelayed({

                    //region handle data
                    // Collections.swap(currenciesList, position, 0)
                    ratesViewModel.setBaseCurrency(currency, newBaseMultiplier ?: 1.0)
                    //endregion

                    //region handle focus
                    rowBeingEdited?.let {
                        // When changing the base currency, the first row should be the one selected to edit
                        attachedRecyclerView?.findViewHolderForAdapterPosition(0)?.itemView?.requestFocus()
                        // Make sure the next adapter refresh will refresh the first item too as it will change
                        rowBeingEdited = null
                    }
                    //endregion

                    //region scroll to the top, so the moved row is visible
                    (attachedRecyclerView?.layoutManager as? LinearLayoutManager?)?.apply {

                        val isScrolledToTheTop = findFirstCompletelyVisibleItemPosition() == 0

                        if (!isScrolledToTheTop) {
                            // Smooth, delayed scroll for better UX
                            Handler().postDelayed({
                                attachedRecyclerView?.let {
                                    smoothScrollToPosition(
                                        attachedRecyclerView,
                                        RecyclerView.State(),
                                        0
                                    )
                                }
                            }, 100L)
                        } else {
                            // Immediate transition, as we were already at the top
                            scrollToPosition(0)
                        }
                    }
                    //endregion
                }, 200L)
            }
        }

        override fun onTextBeingEdited(position: Int) {
            rowBeingEdited = position
        }

        override fun onTextNotBeingEdited(position: Int) {
            if (rowBeingEdited == position) rowBeingEdited = null
        }

        override fun onBaseMultiplierChanged(newBaseMultiplier: Double) {
            val previousBaseMultiplier = ratesViewModel.volatileBaseMultiplierLiveData.value
            if (newBaseMultiplier != previousBaseMultiplier) {
                ratesViewModel.setBaseMultiplierImmediately(newBaseMultiplier)
                if (previousBaseMultiplier != null) {
                    // Only refresh the UI after the first time the multiplier is defined
                    updateAllRowsExceptTheOneSelected()
                }
            }
        }
    }

    //region bind / create ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val itemBinding = RowRateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RateViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        if (currenciesList.size > position) {
            holder.bind(
                ratesViewModel.volatileBaseMultiplierLiveData.value ?: 1.0,
                currenciesList[position].title,
                currenciesList[position].rate, // if (position != 0) currenciesList[position].rate else 1.0,
                rowBeingEdited == position,
                onItemInteractionListener
            )
        } else {
            L.e(
                TAG,
                "position: $position is larger than the size of rates: ${currenciesList.size}"
            )
        }
    }
    //endregion

    //region lifecycle

    override fun onAttachedToRecyclerView(recyclerView : RecyclerView) {
        attachedRecyclerView = recyclerView
        attachedRecyclerView?.addOnScrollListener(scrollingStateListener)
    }

    override fun onDetachedFromRecyclerView(recyclerView : RecyclerView){
        attachedRecyclerView?.removeOnScrollListener(scrollingStateListener)
        attachedRecyclerView = null
    }
    //endregion

    override fun getItemCount(): Int {
        return currenciesList.size
    }

    /**
     * Called when the data is updated in the ViewModel and the Activity is observing it
     */
    fun onRatesUpdated(nonEmptyCachedCurrencies: List<Currency>) {
        if (isBeingScrolled) {
            // Don't update the values while scrolling, will be applied when done
            currenciesPendingList = nonEmptyCachedCurrencies
        } else {
            currenciesPendingList = null
            // Set the values and update the UI
            if (currenciesList != nonEmptyCachedCurrencies) {
                L.i(TAG, "Updating the currencies in the UI")
                currenciesList = nonEmptyCachedCurrencies
                updateAllRowsExceptTheOneSelected()
            }
        }
    }

    private fun updateAllRowsExceptTheOneSelected() {

        if (rowBeingEdited == null) {
            // Refresh all the elements
            notifyDataSetChanged()
        } else {
            // Don't refresh the row whose EditText is being edited
            rowBeingEdited?.let {
                val start: Int
                val end: Int
                val last = currenciesList.size.minus(1)

                when (it) {
                    -1 -> {
                        // invalid case
                        return
                    }
                    0 -> {
                        // don't refresh the first row
                        start = 1
                        end = last
                    }
                    last -> {
                        // don't refresh the last row
                        start = 0
                        end = last.minus(1)
                    }
                    else -> {
                        // Don't refresh a row in the middle by breaking the refresh in 2:
                        // 1. First part is from start until one before the selected one
                        start = 0
                        end = it
                        // 2. Second part is this the one below the selected one until the last one
                        notifyItemRangeChanged(it.plus(1), last)
                    }

                }
                if (start == end) {
                    notifyItemChanged(start)
                } else {
                    notifyItemRangeChanged(start, end)
                }
            }
        }
    }
}

