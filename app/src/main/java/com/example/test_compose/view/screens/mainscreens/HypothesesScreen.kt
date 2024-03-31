package com.example.test_compose.view.screens.mainscreens

import android.graphics.PointF
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material3.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.CardColors
import androidx.compose.runtime.*

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import com.example.test_compose.viewmodel.GetQuotesStats
import com.example.test_compose.viewmodel.GetShareQuotesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HypothesesScreen(getShareQuotesService: GetShareQuotesService) {
    var secid by remember { mutableStateOf("") }
    var quotes by remember { mutableStateOf<List<Double>>(emptyList()) }

    val coroutineScope = rememberCoroutineScope()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = secid,
            onValueChange = { secid = it },
            label = { Text("Enter secid") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                coroutineScope.launch(Dispatchers.IO) {
                    quotes = getShareQuotesService.getShareQuotes(secid)
                }
            }),
            textStyle = TextStyle( color = Color.Black),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    quotes = GetShareQuotesService().getShareQuotes(secid)
                }
            }
        ) {
            Text("Get Share Quotes")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (quotes.isNotEmpty()) {
            LineChart(quotes)
        }
        if(quotes.isNotEmpty()) {
            val getQuotesStats = GetQuotesStats(quotes)
            statsCard(getQuotesStats = getQuotesStats)
        }
    }
}

@Composable
fun statsCard(getQuotesStats: GetQuotesStats) {
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