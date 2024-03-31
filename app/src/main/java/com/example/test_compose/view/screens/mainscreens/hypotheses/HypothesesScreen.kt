package com.example.test_compose.view.screens.mainscreens.hypotheses

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
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
            StatsCard(getQuotesStats = getQuotesStats)
        }
    }
}
