package com.example.test_compose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.test_compose.data.models.MyShare

data class MyShareState(
    var myShares : List<MyShare> = emptyList(),
    var secid: MutableState<String> = mutableStateOf(""),
    var number : MutableState<Int> = mutableStateOf(0),
    val moneySpend: MutableState<Double> = mutableStateOf(0.0),
    val curPricePerOne: MutableState<Double> = mutableStateOf(0.0)
)