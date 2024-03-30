package com.example.test_compose.view.screens.mainscreens.homescreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.test_compose.data.models.MyShare
import com.example.test_compose.viewmodel.GetSharesService
import com.example.test_compose.viewmodel.MyShareEvents
import com.example.test_compose.viewmodel.MyShareState

@Composable
fun AddShareScreen(
    state: MyShareState,
    navController: NavController,
    onEvent: (MyShareEvents) -> Unit,
    getSharesService: GetSharesService,
    applicationContext: Context
) {
    val items = getSharesService.items.observeAsState(initial = emptyList())
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                val price = items.value.find { it.secid == state.secid.value }?.lastPrice
                if (price == null) {
                    Toast.makeText(applicationContext, "no such share",Toast.LENGTH_SHORT ).show()
                }else {
                    state.curPricePerOne.value = price
                    state.moneySpend.value = price * state.number.value
                    onEvent(
                        MyShareEvents.BuyShares(
                            myShare = MyShare(
                                state.secid.value,
                                state.number.value,
                                state.number.value * price,
                                price
                            )
                        )
                    )
                }
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = "")

            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)

        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.secid.value,
                onValueChange = {
                    state.secid.value = it},
                label = { Text("secid") },
                textStyle = TextStyle( color = Color.Black),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.number.value.toString(),
                onValueChange = {
                    try {
                        if (it.toInt() <= 0) {
                            throw NumberFormatException()
                        }
                        state.number.value = it.toInt()


                    } catch (e: NumberFormatException) {

                        Toast.makeText(applicationContext, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                    }},

                label = { Text("number") },
                textStyle = TextStyle( color = Color.Black),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )

            )
        }

    }
}
