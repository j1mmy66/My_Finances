package com.example.test_compose.view.screens.mainscreens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_compose.data.models.HistoryShare
import com.example.test_compose.view.screens.mainscreens.homescreen.ShareItem
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
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(shares) { share ->
            HistoryShareItem(share)
        }

    }
}

@Composable
fun HistoryShareItem(share: HistoryShare) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start

        ) {
            Text(
                text = share.secid,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
            )
            Text(
                text = "${share.count} shares",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
            )


        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End

        ) {

            Text(
                text = "%.1f ₽".format(share.count * share.curPricePerOne),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
            )


        }
    }
}