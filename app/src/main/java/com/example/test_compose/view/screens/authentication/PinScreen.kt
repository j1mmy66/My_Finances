package com.example.test_compose.view.screens.authentication


import BiometricPromptSampleTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.test_compose.R
import com.example.test_compose.ui.theme.ButtonWidth
import com.example.test_compose.ui.theme.MarginDouble
import com.example.test_compose.ui.theme.MarginQuad
import com.example.test_compose.ui.theme.MaxTabletWidth
import com.example.test_compose.viewmodel.authentication.PinCallbacks
import com.example.test_compose.viewmodel.authentication.PinState
import com.example.test_compose.viewmodel.authentication.noOpPinCallbacks


@Composable
fun PinScreen(
    state: PinState,
    pinCallbacks: PinCallbacks,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.width(MaxTabletWidth),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "My Finances",
                modifier = Modifier.padding(horizontal = MarginQuad),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(MarginDouble))
            PasswordTextField(
                value = state.pin,
                onValueChange = { value -> pinCallbacks.onPinChange(value) },
                label = { Text(text = stringResource(id = R.string.pin)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                keyboardActions = KeyboardActions(
                    onDone = {
                        pinCallbacks.onPinUnlockClick()
                    }
                ),
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

@Preview(showBackground = true)
@Composable
private fun PinScreenPreview() {
    BiometricPromptSampleTheme {
        PinScreen(
            state = PinState(
                pin = "",
                pinButtonEnabled = false,
                pinError = false,
            ),
            pinCallbacks = noOpPinCallbacks,
        )
    }
}
