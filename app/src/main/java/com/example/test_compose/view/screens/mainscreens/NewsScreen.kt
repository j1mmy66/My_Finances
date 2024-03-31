package com.example.test_compose.view.screens.mainscreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_compose.viewmodel.GetNewsViewModel
import com.example.test_compose.viewmodel.model.News
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle


@Composable
fun NewsScreen(getNewsViewModel: GetNewsViewModel) {
    val items = getNewsViewModel.items.observeAsState(initial = emptyList())



    LazyColumn {
        items(items.value) { item ->
            New(item)
        }
    }
}

@Composable
fun New(new : News) {
    OutlinedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        border = BorderStroke(0.5.dp, Color.Gray),
        modifier = Modifier

            .fillMaxWidth(),
        shape = RoundedCornerShape(ZeroCornerSize),
        colors = CardColors(
            containerColor = Color(0xFFF0E8FF),
            contentColor = Color.Black,
            disabledContainerColor = Color.Black,
            disabledContentColor = Color.Black
        )

    ) {
        Text(text = new.title,
            modifier = Modifier.padding(
                start = 14.dp,
                top = 8.dp,
                end = 14.dp,
                bottom = 8.dp
            ),
            style = TextStyle(fontSize = 16.sp)
            )
    }
}