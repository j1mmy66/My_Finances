package com.example.test_compose.view.screens.mainscreens.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_compose.data.models.HistoryShare
import com.example.test_compose.viewmodel.GetSharesService
import com.example.test_compose.viewmodel.HistoryShareViewModel
import com.example.test_compose.viewmodel.MyShareEvents
import com.example.test_compose.viewmodel.MyShareState

@Composable
fun ShareItem(
    state : MyShareState,
    index : Int,
    getSharesService: GetSharesService,
    onEvent: (MyShareEvents) -> Unit,
    historyShareViewModel: HistoryShareViewModel
) {
    val items = getSharesService.items.observeAsState(initial = emptyList())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "${state.myShares[index].secid}",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium))
            Text(text = "${state.myShares[index].count} shares",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium))


        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            val price = items.value.find { it.secid == state.myShares[index].secid }?.lastPrice
            if (price != null) {
                Text(text = "%.1f ₽".format(price * state.myShares[index].count),
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
                )
            }


        }
        IconButton(onClick = {
            historyShareViewModel.addShare(HistoryShare(state.myShares[index].secid,
                state.myShares[index].count,
                items.value.find { it.secid == state.myShares[index].secid }?.lastPrice ?: 0.0))
            onEvent(MyShareEvents.DeleteShare(state.myShares[index]))

        }) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "")
        }
    }
}