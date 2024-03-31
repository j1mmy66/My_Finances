package com.example.test_compose.view.screens.mainscreens.hypotheses

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LineChart(quotess: List<Double>) {
    val quotes = quotess.map { it.toFloat() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        colors = CardColors(
            containerColor = Color(0xFFFFDAB9),
            contentColor = Color.Black,
            disabledContainerColor = Color.Black,
            disabledContentColor = Color.Black
        )

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentSize(align = Alignment.BottomStart)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                val distance = size.width / (quotes.size + 1)
                var currentX = 0F
                val maxValue = quotes.maxOrNull() ?: 0F
                val points = mutableListOf<PointF>()

                quotes.forEachIndexed { index, data ->
                    if (quotes.size >= index + 2) {
                        val y0 = (maxValue - data) * (size.height / maxValue)
                        val x0 = currentX + distance
                        points.add(PointF(x0, y0))
                        currentX += distance
                    }
                }

                for (i in 0 until points.size - 1) {
                    drawLine(
                        start = Offset(points[i].x, points[i].y),
                        end = Offset(points[i + 1].x, points[i + 1].y),
                        color = Color(0xFF3F51B5),
                        strokeWidth = 8f
                    )
                }
            }
        }
    }
}