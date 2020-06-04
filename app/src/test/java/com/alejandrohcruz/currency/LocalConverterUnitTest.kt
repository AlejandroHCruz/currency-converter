package com.alejandrohcruz.currency

import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.local.LocalConverter.convertResponseToLocalBaseCurrency
import com.alejandrohcruz.currency.data.local.LocalConverter.convertToLocalDatabaseCurrencyObjects
import com.alejandrohcruz.currency.data.local.LocalConverter.reorderAndRecalculatedCurrenciesBasedOnNewBaseOne
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
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

    //region convertToLocalDatabaseCurrencyObjects
    @Test
    fun convertToLocalDatabaseCurrencyObjects_isCorrect() {

        val remoteRates = Resource.Success(
            data = RatesModel(
                KRW.name,
                mutableMapOf<String, Double>().apply {
                    this[EUR.name] = 7.790789261487714E-4
                    this[AUD.name] = 0.001250421676468778
                    this[BGN.name] = 0.0015449135105530137
                    this[BRL.name] = 0.003318876225393766
                    this[CAD.name] = 0.0011873162834507276
                    this[CHF.name] = 8.982780018495334E-4
                    this[CNY.name] = 0.005989558784231754
                    this[CZK.name] = 0.02037603023449497
                    this[DKK.name] = 0.005879708655644777
                    this[GBP.name] = 6.902639285678115E-4
                    this[HKD.name] = 0.0070156057299696865
                    this[HRK.name] = 0.005873476024235587
                    this[HUF.name] = 0.24854487533568564
                    this[IDR.name] = 12.499958319277452
                    this[ILS.name] = 0.0031848746500961773
                    this[INR.name] = 0.06353544458528461
                    this[ISK.name] = 0.1052644700276651
                    this[JPY.name] = 0.09828937640185514
                    this[MXN.name] = 0.016964443616889497
                    this[MYR.name] = 0.003599344638807324
                    this[NOK.name] = 0.007702753342832903
                    this[NZD.name] = 0.001281584833514729
                    this[PHP.name] = 0.04628507900249851
                    this[PLN.name] = 0.003420156485793106
                    this[RON.name] = 0.0036959504256497714
                    this[RUB.name] = 0.05942346601307139
                    this[SEK.name] = 0.008293295168853672
                    this[SGD.name] = 0.001195886151638364
                    this[THB.name] = 0.027903490818944398
                    this[USD.name] = 8.873708968834506E-4
                    this[ZAR.name] = 0.012469937291937235
                }
            )
        )

        val cachedCurrencies = listOf(
            Currency(KRW.name, 1.0, position = 0),
            Currency(EUR.name, 7.763221737020863E-4, position = 1),
            Currency(AUD.name, 0.001241339155749636, position = 2),
            Currency(BGN.name, 0.0015254730713245997, position = 3),
            Currency(BRL.name, 0.0032683163512857833, position = 4),
            Currency(CAD.name, 0.0011637069383794276, position = 5),
            Currency(CHF.name, 8.842309558466763E-4, position = 6),
            Currency(CNY.name, 0.0059450752062105774, position = 7),
            Currency(CZK.name, 0.01997865114022319, position = 8),
            Currency(DKK.name, 0.005900048520135856, position = 9),
            Currency(GBP.name, 6.823871906841339E-4, position = 10),
            Currency(HKD.name, 0.006965938864628821, position = 11),
            Currency(HRK.name, 0.0057952450266860744, position = 12),
            Currency(HUF.name, 0.24753343037360503, position = 13),
            Currency(IDR.name, 12.418058806404657, position = 14),
            Currency(ILS.name, 0.0031976710334788933, position = 15),
            Currency(INR.name, 0.06291004366812226, position = 16),
            Currency(ISK.name, 0.10525376031052887, position = 17),
            Currency(JPY.name, 0.0984570596797671, position = 18),
            Currency(MXN.name, 0.017093837942746236, position = 19),
            Currency(MYR.name, 0.0036502668607472097, position = 20),
            Currency(NOK.name, 0.007648326055312954, position = 21),
            Currency(NZD.name, 0.0012910237748665696, position = 22),
            Currency(PHP.name, 0.04621756428918001, position = 23),
            Currency(PLN.name, 0.0033746724890829694, position = 24),
            Currency(RON.name, 0.003723241145075206, position = 25),
            Currency(RUB.name, 0.05901756428918001, position = 26),
            Currency(SEK.name, 0.008272489082969433, position = 27),
            Currency(SGD.name, 0.0011924308588064046, position = 28),
            Currency(THB.name, 0.027546239689471126, position = 29),
            Currency(USD.name, 8.857836001940805E-4, position = 30),
            Currency(ZAR.name, 0.012491023774866569, position = 31)
        )

        val expected = listOf(
            Currency(KRW.name, 1.0, position = 0),
            Currency(EUR.name, 7.790789261487714E-4, position = 1),
            Currency(AUD.name, 0.001250421676468778, position = 2),
            Currency(BGN.name, 0.0015449135105530137, position = 3),
            Currency(BRL.name, 0.003318876225393766, position = 4),
            Currency(CAD.name, 0.0011873162834507276, position = 5),
            Currency(CHF.name, 8.982780018495334E-4, position = 6),
            Currency(CNY.name, 0.005989558784231754, position = 7),
            Currency(CZK.name, 0.02037603023449497, position = 8),
            Currency(DKK.name, 0.005879708655644777, position = 9),
            Currency(GBP.name, 6.902639285678115E-4, position = 10),
            Currency(HKD.name, 0.0070156057299696865, position = 11),
            Currency(HRK.name, 0.005873476024235587, position = 12),
            Currency(HUF.name, 0.24854487533568564, position = 13),
            Currency(IDR.name, 12.499958319277452, position = 14),
            Currency(ILS.name, 0.0031848746500961773, position = 15),
            Currency(INR.name, 0.06353544458528461, position = 16),
            Currency(ISK.name, 0.1052644700276651, position = 17),
            Currency(JPY.name, 0.09828937640185514, position = 18),
            Currency(MXN.name, 0.016964443616889497, position = 19),
            Currency(MYR.name, 0.003599344638807324, position = 20),
            Currency(NOK.name, 0.007702753342832903, position = 21),
            Currency(NZD.name, 0.001281584833514729, position = 22),
            Currency(PHP.name, 0.04628507900249851, position = 23),
            Currency(PLN.name, 0.003420156485793106, position = 24),
            Currency(RON.name, 0.0036959504256497714, position = 25),
            Currency(RUB.name, 0.05942346601307139, position = 26),
            Currency(SEK.name, 0.008293295168853672, position = 27),
            Currency(SGD.name, 0.001195886151638364, position = 28),
            Currency(THB.name, 0.027903490818944398, position = 29),
            Currency(USD.name, 8.873708968834506E-4, position = 30),
            Currency(ZAR.name, 0.012469937291937235, position = 31)
        )

        val actual = convertToLocalDatabaseCurrencyObjects(remoteRates, cachedCurrencies)

        assertEquals(expected, actual)

    }
    //endregion

    //region convertResponseToLocalBaseCurrency
    @Test
    fun convertResponseToLocalBaseCurrency_Korean_isCorrect() {
        val localBaseCurrency = KRW

        val remoteResponse = RatesModel(EUR.name, mutableMapOf<String, Double>().apply {
            this[AUD.name] = 1.582
            this[BGN.name] = 1.97
            this[BRL.name] = 4.23
            this[CAD.name] = 1.511
            this[CHF.name] = 1.138
            this[CNY.name] = 7.656
            this[CZK.name] = 25.856
            this[DKK.name] = 7.477
            this[GBP.name] = 0.89
            this[HKD.name] = 9.033
            this[HRK.name] = 7.508
            this[HUF.name] = 319.452
            this[IDR.name] = 16285.798
            this[ILS.name] = 4.146
            this[INR.name] = 80.942
            this[ISK.name] = 136.75
            this[JPY.name] = 125.106
            this[KRW.name] = 1281.65
            this[MXN.name] = 22.087
            this[MYR.name] = 4.62
            this[NOK.name] = 9.893
            this[NZD.name] = 1.676
            this[PHP.name] = 59.511
            this[PLN.name] = 4.407
            this[RON.name] = 4.81
            this[RUB.name] = 75.853
            this[SEK.name] = 10.668
            this[SGD.name] = 1.561
            this[THB.name] = 35.317
            this[USD.name] = 1.142
            this[ZAR.name] = 16.03
        })

        val expected = RatesModel(KRW.name, mutableMapOf<String, Double>().apply {
            this[EUR.name] = 7.802442164397456E-4
            this[AUD.name] = 0.0012343463504076776
            this[BGN.name] = 0.001537081106386299
            this[BRL.name] = 0.0033004330355401244
            this[CAD.name] = 0.0011789490110404555
            this[CHF.name] = 8.879179183084304E-4
            this[CNY.name] = 0.005973549721062692
            this[CZK.name] = 0.020173994460266062
            this[DKK.name] = 0.005833886006319978
            this[GBP.name] = 6.944173526313736E-4
            this[HKD.name] = 0.007047946007100222
            this[HRK.name] = 0.00585807357702961
            this[HUF.name] = 0.24925057543010962
            this[IDR.name] = 12.706899699605977
            this[ILS.name] = 0.003234892521359185
            this[INR.name] = 0.06315452736706588
            this[ISK.name] = 0.10669839659813521
            this[JPY.name] = 0.09761323294191081
            this[MXN.name] = 0.017233254008504662
            this[MYR.name] = 0.003604728279951625
            this[NOK.name] = 0.007718956033238404
            this[NZD.name] = 0.0013076893067530135
            this[PHP.name] = 0.0464331135645457
            this[PLN.name] = 0.003438536261849959
            this[RON.name] = 0.003752974681075176
            this[RUB.name] = 0.05918386454960402
            this[SEK.name] = 0.008323645300979206
            this[SGD.name] = 0.001217961221862443
            this[THB.name] = 0.027555884992002497
            this[USD.name] = 8.910388951741894E-4
            this[ZAR.name] = 0.012507314789529123
        })

        val actual = convertResponseToLocalBaseCurrency(localBaseCurrency, remoteResponse)

        assertEquals(expected, actual)
    }

    @Test
    fun convertResponseToLocalBaseCurrency_Indonesian_isCorrect() {
        val localBaseCurrency = IDR

        val remoteResponse = RatesModel(EUR.name, mutableMapOf<String, Double>().apply {
            this[AUD.name] = 1.607
            this[BGN.name] = 1.978
            this[BRL.name] = 4.198
            this[CAD.name] = 1.507
            this[CHF.name] = 1.144
            this[CNY.name] = 7.655
            this[CZK.name] = 26.054
            this[DKK.name] = 7.554
            this[GBP.name] = 0.884
            this[HKD.name] = 9.04
            this[HRK.name] = 7.507
            this[HUF.name] = 319.499
            this[IDR.name] = 16025.911
            this[ILS.name] = 4.137
            this[INR.name] = 81.66
            this[ISK.name] = 134.329
            this[JPY.name] = 127.312
            this[KRW.name] = 1283.79
            this[MXN.name] = 21.952
            this[MYR.name] = 4.64
            this[NOK.name] = 9.921
            this[NZD.name] = 1.673
            this[PHP.name] = 59.315
            this[PLN.name] = 4.357
            this[RON.name] = 4.762
            this[RUB.name] = 75.664
            this[SEK.name] = 10.616
            this[SGD.name] = 1.556
            this[THB.name] = 35.539
            this[USD.name] = 1.146
            this[ZAR.name] = 16.0
        })

        val expected = RatesModel(IDR.name, mutableMapOf<String, Double>().apply {
            this[EUR.name] = 6.23989488023489E-5
            this[AUD.name] = 1.0027511072537468E-4
            this[BGN.name] = 1.2342512073104612E-4
            this[BRL.name] = 2.619507870722607E-4
            this[CAD.name] = 9.403521584513978E-5
            this[CHF.name] = 7.138439742988713E-5
            this[CNY.name] = 4.776639530819808E-4
            this[CZK.name] = 0.001625742212096398
            this[DKK.name] = 4.713616592529436E-4
            this[GBP.name] = 5.5160670741276425E-5
            this[HKD.name] = 5.64086497173234E-4
            this[HRK.name] = 4.6842890865923315E-4
            this[HUF.name] = 0.019936401743401673
            this[ILS.name] = 2.5814445119531736E-4
            this[INR.name] = 0.005095498159199811
            this[ISK.name] = 0.008381988393670726
            this[JPY.name] = 0.007944134969924643
            this[KRW.name] = 0.08010714648296749
            this[MXN.name] = 0.001369781724109163
            this[MYR.name] = 2.8953112244289885E-4
            this[NOK.name] = 6.190599710681034E-4
            this[NZD.name] = 1.0439344134632971E-4
            this[PHP.name] = 0.0037011936482113247
            this[PLN.name] = 2.718722199318342E-4
            this[RON.name] = 2.971437941967854E-4
            this[RUB.name] = 0.004721354062180927
            this[SEK.name] = 6.624272404857359E-4
            this[SGD.name] = 9.709276433645488E-5
            this[THB.name] = 0.0022175962414866775
            this[USD.name] = 7.150919532749183E-5
            this[ZAR.name] = 9.983831808375824E-4
        })

        val actual = convertResponseToLocalBaseCurrency(localBaseCurrency, remoteResponse)

        assertEquals(expected, actual)
    }
    //endregion
}