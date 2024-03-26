package com.example.test_compose.view.screens.authentication

data class PinState(
    val pin: String,
    val pinButtonEnabled: Boolean,
    val pinError: Boolean,
)

interface PinCallbacks {
    fun onPinChange(pin: String)
    fun onPinUnlockClick()
}
