package com.example.test_compose.view.screens.mainscreens.hypotheses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.test_compose.viewmodel.GetQuotesStats


@Composable
fun StatsCard(getQuotesStats: GetQuotesStats) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),

        colors = CardColors(
            containerColor = Color(0xFFFFDAB9),
            contentColor = Color.Black,
            disabledContainerColor = Color.Black,
            disabledContentColor = Color.Black
        )
    ) {
        Column {
            Text(
                text = "Average value: ${"%.2f".format(getQuotesStats.getAverage())}₽",
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp, bottom = 2.dp)
            )
            Text(
                text = "Standard deviation: ${"%.2f".format(getQuotesStats.standartDeviation())}₽",
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp, bottom = 2.dp)
            )
            Text(
                text = "Minimum value: ${"%.2f".format(getQuotesStats.min())}₽",
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp, bottom = 2.dp)
            )
            Text(
                text = "Maximum value: ${"%.2f".format(getQuotesStats.max())}₽",
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp, bottom = 2.dp)
            )
            Text(
                text = "Profitability for the year: ${"%.2f".format(getQuotesStats.profitability())}%",
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp, bottom = 2.dp)
            )
        }
    }
}