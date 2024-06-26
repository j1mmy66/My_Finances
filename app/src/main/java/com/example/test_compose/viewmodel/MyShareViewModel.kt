package com.example.test_compose.viewmodel

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_compose.data.MyShareDao
import com.example.test_compose.data.models.MyShare
import com.example.test_compose.viewmodel.model.Share
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyShareViewModel(
    private val dao: MyShareDao,
    private val getSharesService: GetSharesService
) : ViewModel() {

    val myShares =
        dao.getShares().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(MyShareState())
    val state = combine(_state, myShares) { state, myShares ->
        state.copy(
            myShares = myShares
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MyShareState())




    val sumValue :  LiveData<Double> get() = _sumValue

    val _sumValue = MutableLiveData<Double>()

    fun calculateSum() {
        viewModelScope.launch {
            val shares = myShares.value
            var sum = 0.0
            for (share in shares) {
                sum += (getSharesService.items.value?.find { it.secid == share.secid }?.lastPrice?.times(
                    share.count
                ))
                    ?: 0.0
            }
            _sumValue.value = sum
        }
    }








    fun onEvent(events: MyShareEvents) {
        when (events) {
            is MyShareEvents.BuyShares -> {
                val myShare = MyShare(
                    secid = state.value.secid.value,
                    count = state.value.number.value,
                    moneySpend = state.value.moneySpend.value,
                    curPricePerOne = state.value.curPricePerOne.value
                )

                viewModelScope.launch {
                    dao.addOrUpdateShare(myShare)
                }

                _state.update {
                    it.copy(
                        secid = mutableStateOf(""),
                        number = mutableIntStateOf(0),
                        moneySpend = mutableStateOf(0.0),
                        curPricePerOne = mutableStateOf(0.0)
                    )
                }
            }
            is MyShareEvents.DeleteShare -> {
                viewModelScope.launch {
                    dao.deleteShare(events.myShare)
                }
            }

        }
    }

}