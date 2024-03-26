package com.example.test_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.test_compose.view.navigation.AppNavigation

import com.example.test_compose.ui.theme.Test_ComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Test_ComposeTheme {
                AppNavigation()

            }
        }
    }
}

