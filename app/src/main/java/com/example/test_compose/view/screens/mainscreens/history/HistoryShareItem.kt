package com.example.test_compose.view.screens.mainscreens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_compose.data.models.HistoryShare


@Composable
fun HistoryShareItem(share: HistoryShare) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFFFDAB9))
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

            val color = if (share.curPricePerOne > 0) {
                Color.Green
            }
            else {
                Color.Black
            }
            Text(
                text = "%.1f ₽".format(share.count * share.curPricePerOne),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium, color = color)
            )


        }
    }
}