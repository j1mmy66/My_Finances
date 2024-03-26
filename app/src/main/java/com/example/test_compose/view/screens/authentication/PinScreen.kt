package com.example.test_compose.view.screens.authentication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.test_compose.R

@Composable
fun PinScreen(
    // 1
    state: PinState,
    // 2
    pinCallbacks: PinCallbacks,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 3
        Column(
            modifier = Modifier.width(MaxTabletWidth),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 4
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier.padding(horizontal = MarginQuad),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(MarginDouble))
            // 5
            PasswordTextField(
                value = state.pin,
                onValueChange = { value -> pinCallbacks.onPinChange(value) },
                label = { Text(text = stringResource(id = R.string.pin)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                isError = state.pinError,
            )
            Spacer(modifier = Modifier.height(MarginQuad))

            Button(
                onClick = { pinCallbacks.onPinUnlockClick() },
                enabled = state.pinButtonEnabled,
                modifier = Modifier
                    .padding(end = MarginQuad)
                    .width(ButtonWidth)
                    .align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = android.R.string.ok)
                )
            }
        }
    }
}