package com.example.test_compose.viewmodel.authentication

import androidx.lifecycle.DefaultLifecycleObserver
import com.example.test_compose.viewmodel.authentication.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val MinPinLength = 4
private const val MaxPinLength = 16
private const val CorrectPin = "1234"

@HiltViewModel
class MainViewModel @Inject constructor(
) : MviViewModel<MainState, MainEvent, MainMviIntent, MainReduceAction>(
    MainState.initial
), PinCallbacks, DefaultLifecycleObserver {

    private val unlockPin = CorrectPin

    override suspend fun executeIntent(intent: MainMviIntent) {
        when (intent) {
            is MainMviIntent.PinUpdated -> {
                if (intent.pin.length <= MaxPinLength)
                    handle(
                        MainReduceAction.PinUpdated(
                            pin = intent.pin,
                            buttonEnabled = intent.pin.length >= MinPinLength,
                        )
                    )
            }
            MainMviIntent.PinUnlockRequested -> if (currentState.pinState.pin == unlockPin) {
                handle(MainReduceAction.Unlock)
            } else {
                handle(MainReduceAction.PinError)
            }
            MainMviIntent.BiometricUnlock -> handle(MainReduceAction.Unlock)
            MainMviIntent.OnBackground -> handle(MainReduceAction.Lock)
        }
    }

    override fun reduce(state: MainState, reduceAction: MainReduceAction): MainState =
        when (reduceAction) {
            is MainReduceAction.PinUpdated -> state.copy(
                pinState = state.pinState.copy(
                    pin = reduceAction.pin,
                    pinButtonEnabled = reduceAction.buttonEnabled,
                    pinError = false,
                ),
            )
            MainReduceAction.PinError -> state.copy(
                pinState = state.pinState.copy(
                    pinError = true,
                )
            )
            MainReduceAction.Unlock -> state.copy(
                loadState = LoadState.SHOW_CONTENT,
                pinState = state.pinState.copy(
                    pin = "",
                    pinError = false,
                ),
            )
            MainReduceAction.Lock -> state.copy(
                loadState = LoadState.SHOW_PIN,
                pinState = state.pinState.copy(
                    pin = "",
                    pinButtonEnabled = false,
                    pinError = false,
                ),
            )
        }

    override fun onPinChange(pin: String) = onIntent(MainMviIntent.PinUpdated(pin))

    override fun onPinUnlockClick() = onIntent(MainMviIntent.PinUnlockRequested)

    fun onBiometricUnlock() = onIntent(MainMviIntent.BiometricUnlock)

    fun onBackground() = onIntent(MainMviIntent.OnBackground)
}