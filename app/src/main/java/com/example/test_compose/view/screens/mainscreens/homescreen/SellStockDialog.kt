package com.example.test_compose.view.screens.mainscreens.homescreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun SellStockDialog(
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var quantity by remember { mutableStateOf(0) }

    AlertDialog(
        backgroundColor = Color(0xFFF0E8FF),
        onDismissRequest = { onDismiss() },
        title = { Text("Введите количество акций для продажи") },
        text = {
            OutlinedTextField(
                value = quantity.toString(),
                onValueChange = { newValue ->
                    quantity = newValue.toIntOrNull()?.takeIf { it > 0 } ?: 0
                },
                label = { Text("Количество акций") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle( color = Color.Black),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )

            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(quantity)
                    onDismiss()
                },
                colors = ButtonColors(
                    containerColor = Color(0xFFB0E0E6),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFB0E0E6),
                    disabledContentColor = Color.White
                )
            ) {
                Text("Продать")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonColors(
                    containerColor = Color(0xFFB0E0E6),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFB0E0E6),
                    disabledContentColor = Color.White
                )
            ) {
                Text("Отмена")
            }
        }
    )
}