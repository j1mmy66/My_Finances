package com.example.test_compose.view.navigation

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.test_compose.view.screens.mainscreens.homescreen.AddShareScreen
import com.example.test_compose.view.screens.mainscreens.HistoryScreen
import com.example.test_compose.view.screens.mainscreens.homescreen.HomeScreen
import com.example.test_compose.view.screens.mainscreens.HypothesesScreen
import com.example.test_compose.view.screens.mainscreens.NewsScreen
import com.example.test_compose.view.screens.mainscreens.QuotesScreen
import com.example.test_compose.view.screens.SettingsScreen
import com.example.test_compose.viewmodel.GetExchanchgeRateViewModel
import com.example.test_compose.viewmodel.GetSharesService
import com.example.test_compose.viewmodel.HistoryShareViewModel
import com.example.test_compose.viewmodel.MyShareViewModel
import com.example.test_compose.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    settingsViewModel: SettingsViewModel,
    myShareViewModel: MyShareViewModel,
    getSharesService: GetSharesService,
    applicationContext: Context,
    getExchanchgeRateViewModel: GetExchanchgeRateViewModel,
    historyShareViewModel: HistoryShareViewModel
) {
    val navController = rememberNavController()
    val state by myShareViewModel.state.collectAsState()

    Scaffold(
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination?.route

            var currentScreen = listOfNavItems().find { it.route == currentDestination }
            if (currentDestination == "AddShareScreen") {
                currentScreen = AddSharesItem()
            }
            if (currentDestination == "SettingsScreen") {
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
                HomeScreen(
                    state = state,
                    navController = navController,
                    onEvent = myShareViewModel::onEvent,
                    getSharesService = getSharesService,
                    settingsViewModel = settingsViewModel,
                    myShareViewModel = myShareViewModel,
                    getExchanchgeRateViewModel = getExchanchgeRateViewModel,
                    historyShareViewModel = historyShareViewModel,
                    applicationContext = applicationContext
                )
            }
            composable(route = Screens.NewsScreen.name) {
                NewsScreen()
            }
            composable(route = Screens.QuotesScreen.name) {
                QuotesScreen(getSharesService)
            }
            composable(route = Screens.HypothesesScreen.name) {
                HypothesesScreen()
            }
            composable(route = Screens.HistoryScreen.name) {
                HistoryScreen(historyShareViewModel, getSharesService)
            }
            composable(route = Screens.SettingsScreen.name) {
                SettingsScreen(settingsViewModel)
            }
            composable("AddShareScreen") {
                AddShareScreen(
                    state = state,
                    navController = navController,
                    onEvent = myShareViewModel::onEvent,
                    getSharesService = getSharesService,
                    applicationContext = applicationContext
                )
            }


        }




    }
}