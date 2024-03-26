package com.example.test_compose.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_compose.data.MyShareDao
import com.example.test_compose.data.models.MyShare
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MySharesViewModel(
    private val dao: MyShareDao
) : ViewModel() {

    private val myShares =
        dao.getShares().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(MyShareState())
    val state = combine(_state, myShares) { state, myShares ->
        state.copy(
            myShares = myShares
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MyShareState())

    fun onEvent(events: MyShareEvents) {
        when (events) {
            is MyShareEvents.BuyShares -> {
                val myShare = MyShare(
                    secid = state.value.secid.value,
                    count = state.value.number.value,
                    moneySpend = 0.0,
                    curPricePerOne = 0
                )

                viewModelScope.launch {
                    dao.addOrUpdateShare(myShare)
                }

                _state.update {
                    it.copy(
                        secid = mutableStateOf(""),
                        number = mutableIntStateOf(0)
                    )
                }
            }

        }
    }

}