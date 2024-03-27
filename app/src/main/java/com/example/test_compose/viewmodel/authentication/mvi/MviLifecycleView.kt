package com.example.test_compose.viewmodel.authentication.mvi

import androidx.lifecycle.LifecycleOwner

interface MviLifecycleView<S : State, E : Event> : MviView<S, E>, LifecycleOwner
