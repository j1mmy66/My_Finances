package com.example.test_compose.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.test_compose.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    DisposableEffect(Unit) {
        onDispose {
            settingsViewModel.clearFields()
        }

    }
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF0E8FF)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Edit full name here")
        Text("Just write new in the relevant")
        OutlinedTextField(value = settingsViewModel.firstName,
            onValueChange = {firstName -> settingsViewModel.updateFirstName(firstName) },
            label = { Text("firstName")})
        OutlinedTextField(value = settingsViewModel.lastName,
            onValueChange = {lastName -> settingsViewModel.updateLastName(lastName) },
            label = { Text("lastName")})


    }
}