package com.example.test_compose.view.screens.authentication

import BiometricPromptSampleTheme
import android.app.Application
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test_compose.R
import com.example.test_compose.ui.theme.MarginDouble
import dagger.hilt.android.HiltAndroidApp


@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    label: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
) {
    var passwordVisible: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    PasswordTextField(
        value = value,
        onValueChange = onValueChange,
        passwordVisible = passwordVisible,
        onTogglePasswordVisibility = { passwordVisible = !passwordVisible },
        modifier = modifier,
        enabled = enabled,
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        label = label,
        colors = colors,
    )
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    passwordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    label: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
) {
    OutlinedTextField(
        value = value,
        label = label,
        enabled = enabled,
        isError = isError,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            IconButton(
                onClick = onTogglePasswordVisibility,
            ) {
                Crossfade(
                    targetState = passwordVisible,
                    label = "password_visibility",
                ) { visible ->
                    Icon(
                        painter = painterResource(
                            id = if (visible) {
                                R.drawable.ic_visibility_on
                            } else {
                                R.drawable.ic_visibility_off
                            }
                        ),
                        contentDescription = stringResource(R.string.content_desc_toggle_password_visibility),
                    )
                }
            }
        },
        onValueChange = onValueChange,
        modifier = modifier,
        colors = colors,
    )
}

@Preview(showBackground = true)
@Composable
private fun PasswordTextFieldPreview() {
    BiometricPromptSampleTheme {
        var value by remember {
            mutableStateOf("")
        }
        Surface(modifier = Modifier.width(360.dp)) {
            PasswordTextField(
                value = value,
                onValueChange = { newValue -> value = newValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MarginDouble),
                label = { Text(text = "PIN") },
            )
        }
    }
}

@HiltAndroidApp
class BiometricPromptApp : Application()