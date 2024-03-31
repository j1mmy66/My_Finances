package com.example.test_compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_compose.data.HistoryShareDao
import com.example.test_compose.data.models.HistoryShare
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryShareViewModel(
    private val dao : HistoryShareDao
): ViewModel() {
    private val refreshIntervalMs = 5000L
    private val _shares = MutableStateFlow<List<HistoryShare>>(emptyList())
    val shares: StateFlow<List<HistoryShare>> = _shares

    init {
        viewModelScope.launch {
            dao.getShares()
                .flatMapLatest { shares ->
                    // Создаем Flow с оператором zip, который будет запускать запрос каждые refreshIntervalMs миллисекунд
                    flow {
                        while (true) {
                            emit(shares)
                            kotlinx.coroutines.delay(refreshIntervalMs)
                        }
                    }
                }
                .collect { shares ->
                    _shares.value = shares
                }
        }
    }

    fun addShare(historyShare: HistoryShare) {
        viewModelScope.launch {
            dao.insert(historyShare)
        }
    }

}