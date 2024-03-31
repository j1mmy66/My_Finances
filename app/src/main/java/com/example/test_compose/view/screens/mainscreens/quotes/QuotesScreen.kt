package com.example.test_compose.view.screens.mainscreens.quotes

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.test_compose.viewmodel.GetSharesService

@Composable
fun QuotesScreen(getSharesService: GetSharesService) {

    val items = getSharesService.items.observeAsState(initial = emptyList())



    LazyColumn {
        items(items.value) { item ->
            MyCard(item = item)
        }
    }
}

