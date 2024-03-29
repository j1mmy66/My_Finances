package com.example.test_compose

import BiometricPromptSampleTheme
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.biometric.BiometricPrompt
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.test_compose.data.HistoryShareDatabase
import com.example.test_compose.data.MyShareDatabase
import com.example.test_compose.data.UserDatabase
import com.example.test_compose.view.navigation.AppNavigation
import com.example.test_compose.viewmodel.authentication.LoadState
import com.example.test_compose.viewmodel.authentication.MainState
import com.example.test_compose.viewmodel.authentication.MainViewModel
import com.example.test_compose.view.screens.authentication.PinScreen
import com.example.test_compose.viewmodel.GetExchanchgeRateViewModel
import com.example.test_compose.viewmodel.GetSharesService
import com.example.test_compose.viewmodel.HistoryShareViewModel
import com.example.test_compose.viewmodel.MyShareViewModel
import com.example.test_compose.viewmodel.SettingsViewModel


import dagger.hilt.android.AndroidEntryPoint

private val biometricsIgnoredErrors = listOf(
    BiometricPrompt.ERROR_NEGATIVE_BUTTON,
    BiometricPrompt.ERROR_CANCELED,
    BiometricPrompt.ERROR_USER_CANCELED,
    BiometricPrompt.ERROR_NO_BIOMETRICS
)

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private val viewModel: MainViewModel by viewModels()


    private val userDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "user.db"
        ).build()
    }

    private val historyShareDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            HistoryShareDatabase::class.java,
            "history_share"
        ).build()
    }

    private val historyShareViewModel by viewModels<HistoryShareViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HistoryShareViewModel(historyShareDatabase.dao) as T
                }
            }
        }
    )


    private val settingsViewModel by viewModels<SettingsViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SettingsViewModel(userDatabase.dao) as T
                }
            }
        }
    )

    private val myShareDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            MyShareDatabase::class.java,
            "my_share.db"
        ).fallbackToDestructiveMigration().build()
    }

    private val myShareViewModel by viewModels<MyShareViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>) : T {
                    return MyShareViewModel(myShareDatabase.dao, getSharesService) as T
                }
            }
        }
    )

    private val getSharesService = GetSharesService()

    private val getExchanchgeRateViewModel = GetExchanchgeRateViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
        setContent {
            val state: State<MainState> = viewModel.state.collectAsState()
            BiometricPromptSampleTheme {
                Surface(Modifier.background(MaterialTheme.colors.background)) {
                    LaunchedEffect(key1 = state.value.loadState) {
                        if (state.value.loadState == LoadState.SHOW_PIN) {
                            showBiometricPrompt()
                        }
                    }
                    Crossfade(
                        targetState = state.value.loadState, label = ""
                    ) { loadState ->
                        when (loadState) {
                            LoadState.SHOW_PIN -> {
                                PinScreen(
                                    state.value.pinState,
                                    viewModel
                                )
                            }

                            LoadState.SHOW_CONTENT -> AppNavigation(settingsViewModel,
                                myShareViewModel,
                                getSharesService,
                                applicationContext,
                                getExchanchgeRateViewModel,
                                historyShareViewModel)
                        }
                    }
                }
            }
        }
    }

    override fun onStop() {
        if (!isChangingConfigurations) {
            viewModel.onBackground()
        }
        super.onStop()
    }

    private fun showBiometricPrompt() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.pin_biometric_prompt_title))
            .setSubtitle(getString(R.string.pin_biometric_prompt_description))
            .setNegativeButtonText(getString(android.R.string.cancel))
            .build()

        val biometricPrompt = BiometricPrompt(
            this@MainActivity,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    if (errorCode !in biometricsIgnoredErrors) {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.pin_biometric_error, errString),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    viewModel.onBiometricUnlock()
                }

                override fun onAuthenticationFailed() {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.pin_biometric_authentication_error,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
        biometricPrompt.authenticate(promptInfo)
    }
}
