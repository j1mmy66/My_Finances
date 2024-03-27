package com.example.test_compose.view.screens.authentication.mvi

import androidx.lifecycle.LifecycleOwner
import com.example.test_compose.view.screens.authentication.mvi.MviView

interface MviLifecycleView<S : State, E : Event> : MviView<S, E>, LifecycleOwner
