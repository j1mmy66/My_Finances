package com.example.test_compose.view.screens.mainscreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_compose.viewmodel.GetSharesService
import com.example.test_compose.viewmodel.model.Share

@Composable
fun QuotesScreen(getSharesService: GetSharesService) {

    val items = getSharesService.items.observeAsState(initial = emptyList())



    LazyColumn {
        items(items.value) { item ->
            MyCard(item = item)
        }
    }
}

@Composable
fun MyCard(item: Share) {
    OutlinedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        border = BorderStroke(0.5.dp, Color.Gray),
        modifier = Modifier
            .height(54.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(ZeroCornerSize),
        colors = CardColors(
            containerColor = Color(0xFFF0E8FF),
            contentColor = Color.Black,
            disabledContainerColor = Color.Black,
            disabledContentColor = Color.Black
        )

    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            LeftInfo(item = item)
            RightInfo(item = item)
        }
    }
}


@Composable
fun LeftInfo(item: Share){
    Column(
        modifier = Modifier.padding(start = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = item.shortname,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(top = 3.dp))
        Text(text = item.secid,
            modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun RightInfo(item: Share) {
    val textColor = if (item.dayChangePrice() > 0) Color.Green else Color.Red
    Column(
        modifier = Modifier.padding(end = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${item.lastPrice}р",
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp)
        )
        Text(
            text = String.format("%.1f%%", item.dayChangePrice()),
            textAlign = TextAlign.Right,
            style = TextStyle(color = textColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}