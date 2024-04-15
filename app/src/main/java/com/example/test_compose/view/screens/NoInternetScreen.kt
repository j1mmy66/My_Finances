package com.example.test_compose.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test_compose.R

@Preview
@Composable
fun NoInternetScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFFE6E6FA)),
        contentAlignment = Alignment.Center
    ) {

        val imageModifier = Modifier
            .size(200.dp)
        ImageWithModifier(
            painter = painterResource(id = R.drawable.ic_no_internet),
            modifier = imageModifier,
            contentDescription = "Отсутствует подключение к интернету"
        )
    }
}

@Composable
fun ImageWithModifier(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier)

}