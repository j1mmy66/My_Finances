package com.example.test_compose.viewmodel.authentication



interface PinCallbacks {
    fun onPinChange(pin: String)
    fun onPinUnlockClick()
}

val noOpPinCallbacks = object : PinCallbacks {
    override fun onPinChange(pin: String) = Unit
    override fun onPinUnlockClick() = Unit
}
