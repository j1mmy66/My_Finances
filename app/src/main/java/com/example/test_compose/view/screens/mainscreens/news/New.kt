package com.example.test_compose.view.screens.mainscreens.news

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.test_compose.viewmodel.GetNewsViewModel
import com.example.test_compose.viewmodel.model.News

@Composable
fun New(new : News,
        getNewsViewModel: GetNewsViewModel,
        navController: NavController
) {


    OutlinedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
            getNewsViewModel.state.value = new.id
            navController.navigate("ReadNewScreen")
        },
        border = BorderStroke(0.5.dp, Color.Gray),
        modifier = Modifier

            .fillMaxWidth(),
        shape = RoundedCornerShape(ZeroCornerSize),
        colors = CardColors(
            containerColor = Color(0xFFF0E8FF),
            contentColor = Color.Black,
            disabledContainerColor = Color.Black,
            disabledContentColor = Color.Black
        )

    ) {
        Text(text = new.title,
            modifier = Modifier.padding(
                start = 20.dp,
                top = 20.dp,
                end = 20.dp,
                bottom =20.dp
            ),
            style = TextStyle(fontSize = 16.sp)
        )
    }
}