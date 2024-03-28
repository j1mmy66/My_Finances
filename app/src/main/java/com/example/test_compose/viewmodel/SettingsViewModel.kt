package com.example.test_compose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_compose.data.UserDao
import com.example.test_compose.data.models.User
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val dao: UserDao
) : ViewModel() {
    var firstName by mutableStateOf("")


    var lastName by mutableStateOf("")


    var user by mutableStateOf(User("", ""))
        private set

    fun updateFirstName(input: String) {
        firstName = input
        viewModelScope.launch {
            dao.upsert(User(firstName, lastName))
        }
    }

    fun updateLastName(input: String) {
        lastName = input
        viewModelScope.launch {
            dao.upsert(User(firstName, lastName))
        }
    }

    fun getUser(){
        viewModelScope.launch {
            user = dao.getUser() ?: User("", "")
        }
    }

    fun clearFields() {
        firstName = ""
        lastName = ""
        user = User("", "")
    }

    init {
        clearFields()
    }
}