package com.example.test_compose.view.navigation

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.test_compose.view.screens.HistoryScreen
import com.example.test_compose.view.screens.HomeScreen
import com.example.test_compose.view.screens.HypothesesScreen
import com.example.test_compose.view.screens.NewsScreen
import com.example.test_compose.view.screens.QuotesScreen
import com.example.test_compose.view.screens.SettingsScreen
import com.example.test_compose.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AppNavigation(settingsViewModel: SettingsViewModel) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination?.route

            var currentScreen = listOfNavItems().find { it.route == currentDestination }
            if (currentScreen == null) {
                currentScreen = settingsItem()
            }
            TopAppBar(title = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){
                    val item = settingsItem()
                    Text(currentScreen?.label ?: "Unknown")
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        }.padding(8.dp)

                    )


                }

            })
        },

        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currntDestination = navBackStackEntry?.destination

                listOfNavItems().forEach { navItem ->
                    NavigationBarItem(
                        selected = currntDestination?.hierarchy?.any { it.route == navItem.route} == true,
                        onClick = {
                                  navController.navigate(navItem.route) {
                                      popUpTo(navController.graph.findStartDestination().id) {
                                          saveState = true
                                      }

                                      launchSingleTop = true
                                      restoreState = true
                                  }
                        },
                        icon = {
                               Icon(
                                   imageVector = navItem.icon,
                                   contentDescription = null,

                               )
                        },
                        label = {
                            Text(text = navItem.label)
                        })

                }
            }
        }
    ) {paddingValues ->
        NavHost(navController = navController,
            startDestination = Screens.HomeScreen.name,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            composable(route = Screens.HomeScreen.name) {
                HomeScreen()
            }
            composable(route = Screens.NewsScreen.name) {
                NewsScreen()
            }
            composable(route = Screens.QuotesScreen.name) {
                QuotesScreen()
            }
            composable(route = Screens.HypothesesScreen.name) {
                HypothesesScreen()
            }
            composable(route = Screens.HistoryScreen.name) {
                HistoryScreen()
            }
            composable(route = Screens.SettingsScreen.name) {
                SettingsScreen(settingsViewModel)
            }

        }




    }
}