package com.example.test_compose.viewmodel

import kotlin.math.sqrt

class GetQuotesStats(
    val quotes : List<Double>
) {
    fun getAverage() : Double{
        return quotes.average()
    }

    fun standartDeviation() : Double {
        val mean = quotes.average()
        return sqrt(quotes.map { (it - mean) * (it - mean) }.average())
    }

    fun min() : Double{
        return quotes.min()
    }

    fun max() : Double {
        return quotes.max()
    }

    fun profitability() : Double {
        return ((quotes[quotes.size - 1] / quotes[0] - 1) * 100)
    }
}