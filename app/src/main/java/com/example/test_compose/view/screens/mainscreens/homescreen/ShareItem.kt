package com.example.test_compose.view.screens.mainscreens.homescreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_compose.R
import com.example.test_compose.data.models.HistoryShare
import com.example.test_compose.data.models.MyShare
import com.example.test_compose.viewmodel.GetSharesService
import com.example.test_compose.viewmodel.HistoryShareViewModel
import com.example.test_compose.viewmodel.MyShareEvents
import com.example.test_compose.viewmodel.MyShareState
import com.example.test_compose.viewmodel.MyShareViewModel

@Composable
fun ShareItem(
    state : MyShareState,
    index : Int,
    getSharesService: GetSharesService,
    onEvent: (MyShareEvents) -> Unit,
    historyShareViewModel: HistoryShareViewModel,
    applicationContext: Context,
    myShareViewModel: MyShareViewModel
) {
    val items = getSharesService.items.observeAsState(initial = emptyList())
    var dialogOpen by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFFFDAB9))
            .padding(12.dp)

    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = state.myShares[index].secid,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium))
            Text(text = "${state.myShares[index].count} shares",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium))


        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            val price = items.value.find { it.secid == state.myShares[index].secid }?.lastPrice
            if (price != null) {
                Text(text = "%.1f₽".format(price * state.myShares[index].count),
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
                )
            }


        }

        IconButton(onClick = { dialogOpen = true }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_sell),
                contentDescription = "Продать акции",
                tint = Color.Black
            )
        }


    }
    fun sellStock(quantity: Int, applicationContext: Context) {
        if(state.myShares[index].count == quantity) {
            historyShareViewModel.addShare(
                HistoryShare(
                    state.myShares[index].secid,
                    quantity,
                    items.value.find { it.secid == state.myShares[index].secid }?.lastPrice ?: 0.0
                )
            )
            onEvent(MyShareEvents.DeleteShare(state.myShares[index]))
        } else if(state.myShares[index].count > quantity) {
            val secid = state.myShares[index].secid

            val count = state.myShares[index].count - quantity
            val moneySpend = state.myShares[index].moneySpend - state.myShares[index].curPricePerOne * quantity
            val currPrice = state.myShares[index].curPricePerOne
            onEvent(MyShareEvents.DeleteShare(state.myShares[index]))

            myShareViewModel.state.value.secid.value =secid
            myShareViewModel.state.value.number.value = count
            myShareViewModel.state.value.moneySpend.value = moneySpend
            myShareViewModel.state.value.curPricePerOne.value = currPrice
            onEvent(MyShareEvents.BuyShares(MyShare(secid, count, moneySpend, currPrice)))
            historyShareViewModel.addShare(
                HistoryShare(
                    state.myShares[index].secid,
                    quantity,
                    (items.value.find { it.secid == state.myShares[index].secid }?.lastPrice ?: 0.0) * (-1)
                )
            )



        } else {
            Toast.makeText(applicationContext, "You do not have such amount of shares", Toast.LENGTH_SHORT).show()
        }
    }
    if (dialogOpen) {
        SellStockDialog(
            onConfirm = { quantity ->
                sellStock(quantity, applicationContext)
                dialogOpen = false
            },
            onDismiss = { dialogOpen = false }
        )
    }


}

