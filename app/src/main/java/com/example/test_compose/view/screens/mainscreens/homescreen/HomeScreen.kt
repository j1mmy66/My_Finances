package com.example.test_compose.view.screens.mainscreens.homescreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.test_compose.data.models.MyShare
import com.example.test_compose.viewmodel.GetExchanchgeRateViewModel
import com.example.test_compose.viewmodel.GetSharesService
import com.example.test_compose.viewmodel.MyShareEvents
import com.example.test_compose.viewmodel.MyShareState
import com.example.test_compose.viewmodel.MyShareViewModel
import com.example.test_compose.viewmodel.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.test_compose.viewmodel.GetNewsViewModel
import com.example.test_compose.viewmodel.HistoryShareViewModel
import kotlinx.coroutines.flow.StateFlow


@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen(
    state: MyShareState,
    navController: NavController,
    onEvent: (MyShareEvents) -> Unit,
    getSharesService: GetSharesService,
    settingsViewModel: SettingsViewModel,
    myShareViewModel: MyShareViewModel,
    getExchanchgeRateViewModel: GetExchanchgeRateViewModel,
    historyShareViewModel: HistoryShareViewModel,
    applicationContext: Context,
    getNewsViewModel: GetNewsViewModel
) {


    fun getData() {

        CoroutineScope(Dispatchers.IO).launch {
            getSharesService.getShares()
        }

    }

    fun getNews() {
        CoroutineScope(Dispatchers.IO).launch {
            getNewsViewModel.getNews()
        }
    }

    fun getRates() {

        CoroutineScope(Dispatchers.IO).launch {
            getExchanchgeRateViewModel.getExchangeRate()

        }
    }

    val items = getSharesService.items.observeAsState(initial = emptyList())


    val sumValue = remember { mutableStateOf(0.0) }
    LaunchedEffect(myShareViewModel.myShares) {
        val shares = myShareViewModel.myShares.value
        var sum = 0.0
        for (share in shares) {
            sum += (getSharesService.items.value?.find { it.secid == share.secid }?.lastPrice?.times(
                share.count
            ))
                ?: 0.0
        }
        sumValue.value = sum
    }






    LaunchedEffect(true) {
        getData()
    }


    LaunchedEffect(true) {
        getRates()
    }

    LaunchedEffect(true) {
        getNews()
    }


    Scaffold(
        containerColor = Color(0xFFF0E8FF),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    state.secid.value = ""
                    state.number.value = 0
                    navController.navigate("AddShareScreen")
                },
                content = {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
                },
                containerColor = Color(0xFFB0E0E6)
            )
        }

    ) { paddingValues ->
        Column {


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                colors = CardColors(
                    containerColor = Color(0xFFFFDAB9),
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Black,
                    disabledContentColor = Color.Black
                )


            ) {
                settingsViewModel.getUser()
                Column(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                ) {

                    Text(
                        text = "${settingsViewModel.user.firstName}  ${settingsViewModel.user.lastName}",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    )
                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(
                        text = "Total sum: ${"%.1f".format(sumValue.value)}₽",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    )
                }


            }

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
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "ExchangeRate",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "$${"%.1f".format(getExchanchgeRateViewModel.items.value?.get(0) ?: 0.0)}",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    )
                    Text(
                        text = "€${"%.1f".format(getExchanchgeRateViewModel.items.value?.get(1) ?: 0.0)}",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    )
                    Text(
                        text = "¥${"%.1f".format(getExchanchgeRateViewModel.items.value?.get(2) ?: 0.0)}",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    )
                }
                Spacer(modifier = Modifier.padding(4.dp))

            }

            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.myShares.size) { index ->
                    ShareItem(
                        state = state,
                        index = index, getSharesService,
                        onEvent = onEvent,
                        historyShareViewModel = historyShareViewModel,
                        applicationContext = applicationContext,
                        myShareViewModel = myShareViewModel
                    )
                }

            }
        }


    }
}








