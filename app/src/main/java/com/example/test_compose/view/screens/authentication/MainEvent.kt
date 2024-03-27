package com.example.test_compose.view.screens.authentication

import com.example.test_compose.view.screens.authentication.mvi.Event
import com.example.test_compose.view.screens.authentication.mvi.MviIntent
import com.example.test_compose.view.screens.authentication.mvi.ReduceAction
import com.example.test_compose.view.screens.authentication.mvi.State


data class PinState(
    val pin: String,
    val pinButtonEnabled: Boolean,
    val pinError: Boolean,
)

enum class LoadState {
    SHOW_PIN,
    SHOW_CONTENT,
}

data class MainState(
    val loadState: LoadState,
    val pinState: PinState,
) : State {
    companion object {
        val initial = MainState(
            loadState = LoadState.SHOW_PIN,
            pinState = PinState(
                pin = "",
                pinButtonEnabled = false,
                pinError = false,
            ),
        )
    }
}

sealed class MainEvent : Event

sealed class MainMviIntent : MviIntent {
    data class PinUpdated(val pin: String) : MainMviIntent()
    object PinUnlockRequested : MainMviIntent()
    object BiometricUnlock : MainMviIntent()
    object OnBackground : MainMviIntent()
}

sealed class MainReduceAction : ReduceAction {
    data class PinUpdated(val pin: String, val buttonEnabled: Boolean) : MainReduceAction()
    object Lock : MainReduceAction()
    object Unlock : MainReduceAction()
    object PinError : MainReduceAction()
}
