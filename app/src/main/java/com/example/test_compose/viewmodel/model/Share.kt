package com.example.test_compose.viewmodel.model

data class Share(
    val secid: String,
    val shortname: String,
    val lastPrice: Double,
    val openPrice: Double,
    val valToDay: Long
) {
    fun dayChangePrice() : Double {
        return (lastPrice - openPrice) / openPrice * 100
    }
}