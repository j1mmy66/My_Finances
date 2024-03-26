package com.example.test_compose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.test_compose.data.models.MyShare

data class MyShareState(
    val myShares : List<MyShare> = emptyList(),
    val secid: MutableState<String> = mutableStateOf(""),
    val number : MutableState<Int> = mutableIntStateOf(0)
)