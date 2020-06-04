package com.alejandrohcruz.currency

import com.alejandrohcruz.currency.data.local.LocalConverter.reorderAndRecalculatedCurrenciesBasedOnNewBaseOne
import com.alejandrohcruz.currency.model.Currency
import com.alejandrohcruz.currency.model.CurrencyEnum.*
import org.junit.Test

import org.junit.Assert.*

class LocalConverterUnitTest {

    //region reorderAndRecalculatedCurrenciesBasedOnNewBaseOne
    @Test
    fun reorderAndRecalculatedCurrenciesBasedOnNewBaseOne_secondPosition_isCorrect() {
        val newBaseCurrency = AUD

        val input = listOf(
            Currency(EUR.name, 1.0, 0),
            Currency(AUD.name, 1.594, 1),
            Currency(BGN.name, 1.971, 2),
            Currency(BRL.name, 4.261, 3),
            Currency(CAD.name, 1.502, 4),
            Currency(CHF.name, 1.146, 5),
            Currency(CNY.name, 7.762, 6),
            Currency(CZK.name, 25.671, 7),
            Currency(DKK.name, 7.473, 8),
            Currency(GBP.name, 0.875, 9),
            Currency(HKD.name, 8.928, 10),
            Currency(HRK.name, 7.554, 11),
            Currency(HUF.name, 318.993, 12),
            Currency(IDR.name, 16078.501, 13),
            Currency(ILS.name, 4.141, 14),
            Currency(INR.name, 81.712, 15),
            Currency(ISK.name, 136.528, 16),
            Currency(JPY.name, 125.858, 17),
            Currency(KRW.name, 1275.764, 18),
            Currency(MXN.name, 21.824, 19),
            Currency(MYR.name, 4.641, 20),
            Currency(NOK.name, 9.939, 21),
            Currency(NZD.name, 1.665, 22),
            Currency(PHP.name, 60.36, 23),
            Currency(PLN.name, 4.4, 24),
            Currency(RON.name, 4.803, 25),
            Currency(RUB.name, 75.328, 26),
            Currency(SEK.name, 10.591, 27),
            Currency(SGD.name, 1.545, 28),
            Currency(THB.name, 36.014, 29),
            Currency(USD.name, 1.149, 30),
            Currency(ZAR.name, 15.9, 31)
        )

        val expected = listOf(
            Currency(EUR.name, 0.6273525721455457, 1),
            Currency(AUD.name, 1.0, 0),
            Currency(BGN.name, 1.2365119196988708, 2),
            Currency(BRL.name, 2.6731493099121706, 3),
            Currency(CAD.name, 0.9422835633626098, 4),
            Currency(CHF.name, 0.7189460476787953, 5),
            Currency(CNY.name, 4.869510664993726, 6),
            Currency(CZK.name, 16.104767879548305, 7),
            Currency(DKK.name, 4.688205771643664, 8),
            Currency(GBP.name, 0.5489335006273526, 9),
            Currency(HKD.name, 5.601003764115433, 10),
            Currency(HRK.name, 4.739021329987453, 11),
            Currency(HUF.name, 200.1210790464241, 12),
            Currency(IDR.name, 10086.88895859473, 13),
            Currency(ILS.name, 2.597867001254705, 14),
            Currency(INR.name, 51.26223337515684, 15),
            Currency(ISK.name, 85.65119196988707, 16),
            Currency(JPY.name, 78.95734002509411, 17),
            Currency(KRW.name, 800.35382685069, 18),
            Currency(MXN.name, 13.69134253450439, 19),
            Currency(MYR.name, 2.9115432873274782, 20),
            Currency(NOK.name, 6.235257214554579, 21),
            Currency(NZD.name, 1.0445420326223338, 22),
            Currency(PHP.name, 37.867001254705144, 23),
            Currency(PLN.name, 2.7603513174404015, 24),
            Currency(RON.name, 3.013174404015056, 25),
            Currency(RUB.name, 47.257214554579676, 26),
            Currency(SEK.name, 6.644291091593475, 27),
            Currency(SGD.name, 0.969259723964868, 28),
            Currency(THB.name, 22.593475533249688, 29),
            Currency(USD.name, 0.720828105395232, 30),
            Currency(ZAR.name, 9.974905897114178, 31)
        )

        val actual = reorderAndRecalculatedCurrenciesBasedOnNewBaseOne(newBaseCurrency, input)

        assertEquals(expected, actual)
    }

    @Test
    fun reorderAndRecalculatedCurrenciesBasedOnNewBaseOne_lastPosition_isCorrect() {
        val newBaseCurrency = ZAR

        val input = listOf(
            Currency(EUR.name, 1.0, position = 0),
            Currency(AUD.name, 1.605, position = 1),
            Currency(BGN.name, 1.963, position = 2),
            Currency(BRL.name, 4.25, position = 3),
            Currency(CAD.name, 1.502, position = 4),
            Currency(CHF.name, 1.157, position = 5),
            Currency(CNY.name, 7.674, position = 6),
            Currency(CZK.name, 25.713, position = 7),
            Currency(DKK.name, 7.567, position = 8),
            Currency(GBP.name, 0.881, position = 9),
            Currency(HKD.name, 9.014, position = 10),
            Currency(HRK.name, 7.516, position = 11),
            Currency(HUF.name, 323.356, position = 12),
            Currency(IDR.name, 16040.383, position = 13),
            Currency(ILS.name, 4.131, position = 14),
            Currency(INR.name, 80.845, position = 15),
            Currency(ISK.name, 136.36, position = 16),
            Currency(JPY.name, 125.442, position = 17),
            Currency(KRW.name, 1284.593, position = 18),
            Currency(MXN.name, 22.061, position = 19),
            Currency(MYR.name, 4.635, position = 20),
            Currency(NOK.name, 9.896, position = 21),
            Currency(NZD.name, 1.656, position = 22),
            Currency(PHP.name, 60.25, position = 23),
            Currency(PLN.name, 4.337, position = 24),
            Currency(RON.name, 4.82, position = 25),
            Currency(RUB.name, 75.17, position = 26),
            Currency(SEK.name, 10.511, position = 27),
            Currency(SGD.name, 1.556, position = 28),
            Currency(THB.name, 36.007, position =29),
            Currency(USD.name, 1.137, position = 30),
            Currency(ZAR.name, 16.031, position = 31)
        )

        val expected = listOf(
            Currency(EUR.name, 0.06237914041544508, position = 1),
            Currency(AUD.name, 0.10011852036678935, position = 2),
            Currency(BGN.name, 0.1224502526355187, position = 3),
            Currency(BRL.name, 0.2651113467656416, position = 4),
            Currency(CAD.name, 0.0936934689039985, position = 5),
            Currency(CHF.name, 0.07217266546066996, position = 6),
            Currency(CNY.name, 0.47869752354812556, position = 7),
            Currency(CZK.name, 1.6039548375023394, position = 8),
            Currency(DKK.name, 0.47202295552367296, position = 9),
            Currency(GBP.name, 0.054956022706007114, position = 10),
            Currency(HKD.name, 0.5622855717048219, position = 11),
            Currency(HRK.name, 0.4688416193624852, position = 12),
            Currency(HUF.name, 20.170669328176658, position = 13),
            Currency(IDR.name, 1000.5853034745182, position = 14),
            Currency(ILS.name, 0.25768822905620364, position = 15),
            Currency(INR.name, 5.043041606886658, position = 16),
            Currency(ISK.name, 8.506019587050092, position = 17),
            Currency(JPY.name, 7.824964131994262, position = 18),
            Currency(KRW.name, 80.13180712369785, position = 19),
            Currency(MXN.name, 1.376146216705134, position = 20),
            Currency(MYR.name, 0.2891273158255879, position = 21),
            Currency(NOK.name, 0.6173039735512446, position = 22),
            Currency(NZD.name, 0.10329985652797705, position = 23),
            Currency(PHP.name, 3.758343210030566, position = 24),
            Currency(PLN.name, 0.2705383319817853, position = 25),
            Currency(RON.name, 0.3006674568024453, position = 26),
            Currency(RUB.name, 4.689039985029006, position = 27),
            Currency(SEK.name, 0.6556671449067432, position = 28),
            Currency(SGD.name, 0.09706194248643256, position = 29),
            Currency(THB.name, 2.246085708938931, position = 30),
            Currency(USD.name, 0.07092508265236105, position = 31),
            Currency(ZAR.name,  1.0, 0)
        )

        val actual = reorderAndRecalculatedCurrenciesBasedOnNewBaseOne(newBaseCurrency, input)

        assertEquals(expected, actual)
    }

    @Test
    fun reorderAndRecalculatedCurrenciesBasedOnNewBaseOne_middleValue_isCorrect() {
        val newBaseCurrency = KRW

        val input = listOf(
            Currency(EUR.name, 1.0, position = 0),
            Currency(AUD.name, 1.592, position = 1),
            Currency(BGN.name, 1.985, position = 2),
            Currency(BRL.name, 4.25, position = 3),
            Currency(CAD.name, 1.502, position = 4),
            Currency(CHF.name, 1.15, position = 5),
            Currency(CNY.name, 7.778, position = 6),
            Currency(CZK.name, 26.003, position = 7),
            Currency(DKK.name, 7.597, position = 8),
            Currency(GBP.name, 0.893, position = 9),
            Currency(HKD.name, 8.877, position = 10),
            Currency(HRK.name, 7.546, position = 11),
            Currency(HUF.name, 319.995, position = 12),
            Currency(IDR.name, 16125.377, position = 13),
            Currency(ILS.name, 4.164, position = 14),
            Currency(INR.name, 80.923, position = 15),
            Currency(ISK.name, 134.637, position = 16),
            Currency(JPY.name, 124.957, position = 17),
            Currency(KRW.name, 1272.922, position = 18),
            Currency(MXN.name, 22.118, position = 19),
            Currency(MYR.name, 4.68, position = 20),
            Currency(NOK.name, 9.839, position = 21),
            Currency(NZD.name, 1.648, position = 22),
            Currency(PHP.name, 60.073, position = 23),
            Currency(PLN.name, 4.372, position = 24),
            Currency(RON.name, 4.796, position = 25),
            Currency(RUB.name, 76.183, position = 26),
            Currency(SEK.name, 10.532, position = 27),
            Currency(SGD.name, 1.56, position = 28),
            Currency(THB.name, 35.443, position =29),
            Currency(USD.name, 1.129, position = 30),
            Currency(ZAR.name, 15.912, position = 31)
        )

        val expected = listOf(
            Currency(EUR.name, 7.855940898185435E-4, position = 1),
            Currency(AUD.name, 0.001250665790991121, position = 2),
            Currency(BGN.name, 0.001559404268289809, position = 3),
            Currency(BRL.name, 0.00333877488172881, position = 4),
            Currency(CAD.name, 0.0011799623229074522, position = 5),
            Currency(CHF.name, 9.03433203291325E-4, position = 6),
            Currency(CNY.name, 0.00611035083060863, position = 7),
            Currency(CZK.name, 0.020427803117551584, position = 8),
            Currency(DKK.name, 0.005968158300351475, position = 9),
            Currency(GBP.name, 7.015355222079594E-4, position = 10),
            Currency(HKD.name, 0.00697371873531921, position = 11),
            Currency(HRK.name, 0.005928093001770728, position = 12),
            Currency(HUF.name, 0.25138618077148484, position = 13),
            Currency(IDR.name, 12.668000867295875, position = 14),
            Currency(ILS.name, 0.0032712137900044146, position = 15),
            Currency(INR.name, 0.06357263053038599, position = 16),
            Currency(ISK.name, 0.10577003147089922, position = 17),
            Currency(JPY.name, 0.09816548068145572, position = 18),
            Currency(KRW.name, 1.0, position = 0),
            Currency(MXN.name, 0.017375770078606544, position = 19),
            Currency(MYR.name, 0.0036765803403507836, position = 20),
            Currency(NOK.name, 0.00772946024972465, position = 21),
            Currency(NZD.name, 0.0012946590600209596, position = 22),
            Currency(PHP.name, 0.04719299375766937, position = 23),
            Currency(PLN.name, 0.003434617360686672, position = 24),
            Currency(RON.name, 0.003767709254769734, position = 25),
            Currency(RUB.name, 0.05984891454464611, position = 26),
            Currency(SEK.name, 0.0082738769539689, position = 27),
            Currency(SGD.name, 0.0012255267801169279, position = 28),
            Currency(THB.name, 0.027843811325438634, position = 29),
            Currency(USD.name, 8.869357274051355E-4, position = 30),
            Currency(ZAR.name, 0.012500373157192666, position = 31)
        )

        val actual = reorderAndRecalculatedCurrenciesBasedOnNewBaseOne(newBaseCurrency, input)

        assertEquals(expected, actual)
    }
    //endregion
}