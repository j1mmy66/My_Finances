package com.example.test_compose.view.screens.mainscreens.history

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.test_compose.viewmodel.GetSharesService
import com.example.test_compose.viewmodel.HistoryShareViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HistoryScreen(
    historyShareViewModel: HistoryShareViewModel,
    getSharesService: GetSharesService,

    ) {
    val shares by historyShareViewModel.shares.collectAsState(initial = emptyList())
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp).background(Color(0xFFF0E8FF)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(shares) { share ->
            HistoryShareItem(share)
        }

    }
}
