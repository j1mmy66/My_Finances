package com.example.test_compose.view.screens.mainscreens.news

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.test_compose.viewmodel.GetNewsViewModel


@Composable
fun NewsScreen(getNewsViewModel: GetNewsViewModel,
    navController: NavController
) {
    val items = getNewsViewModel.items.observeAsState(initial = emptyList())



    LazyColumn {
        items(items.value) { item ->
            New(item, getNewsViewModel, navController)
        }
    }
}


