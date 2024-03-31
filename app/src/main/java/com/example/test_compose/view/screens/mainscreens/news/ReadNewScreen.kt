package com.example.test_compose.view.screens.mainscreens.news

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.test_compose.viewmodel.GetNewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ReadNewScreen(getNewsViewModel: GetNewsViewModel) {
    fun getNew() {
        CoroutineScope(Dispatchers.IO).launch {
            getNewsViewModel.getNewsById(getNewsViewModel.state.value)
        }
    }

    LaunchedEffect(true) {
        getNew()
    }


    LazyColumn {
        item {
            if (getNewsViewModel.new_state.value != "") {
                Text(
                    text = getNewsViewModel.new_state.value,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}