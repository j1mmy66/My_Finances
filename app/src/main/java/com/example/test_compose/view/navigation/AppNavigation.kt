package com.example.test_compose.view.navigation

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.test_compose.R
import com.example.test_compose.view.screens.NoInternetScreen
import com.example.test_compose.view.screens.SettingsScreen
import com.example.test_compose.view.screens.mainscreens.history.HistoryScreen
import com.example.test_compose.view.screens.mainscreens.hypotheses.HypothesesScreen
import com.example.test_compose.view.screens.mainscreens.news.NewsScreen
import com.example.test_compose.view.screens.mainscreens.quotes.QuotesScreen
import com.example.test_compose.view.screens.mainscreens.news.ReadNewScreen
import com.example.test_compose.view.screens.mainscreens.homescreen.AddShareScreen
import com.example.test_compose.view.screens.mainscreens.homescreen.HomeScreen
import com.example.test_compose.viewmodel.GetExchanchgeRateViewModel
import com.example.test_compose.viewmodel.GetNewsViewModel
import com.example.test_compose.viewmodel.GetShareQuotesService
import com.example.test_compose.viewmodel.GetSharesService
import com.example.test_compose.viewmodel.HistoryShareViewModel
import com.example.test_compose.viewmodel.MyShareViewModel
import com.example.test_compose.viewmodel.SettingsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    settingsViewModel: SettingsViewModel,
    myShareViewModel: MyShareViewModel,
    getSharesService: GetSharesService,
    applicationContext: Context,
    getExchanchgeRateViewModel: GetExchanchgeRateViewModel,
    historyShareViewModel: HistoryShareViewModel,
    getShareQuotesService: GetShareQuotesService,
    getNewsViewModel: GetNewsViewModel
) {
    val navController = rememberNavController()
    val state by myShareViewModel.state.collectAsState()
    val context = LocalContext.current
    val connectivityManager =
        context.getSystemService(ConnectivityManager::class.java)


    var isNetworkAvailable by remember { mutableStateOf(false) }


    DisposableEffect(Unit) {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isNetworkAvailable = true
            }

            override fun onLost(network: Network) {
                isNetworkAvailable = false
            }
        }
        connectivityManager?.registerDefaultNetworkCallback(networkCallback)
        onDispose {
            connectivityManager?.unregisterNetworkCallback(networkCallback)
        }
    }
    if(isNetworkAvailable) {

    Scaffold(
        containerColor = Color(0xFFF0E8FF),
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination?.route

            var currentScreen = listOfNavItems().find { it.route == currentDestination }
            if (currentDestination == "AddShareScreen") {
                currentScreen = addSharesItem()
            }
            if (currentDestination == "SettingsScreen") {
                currentScreen = settingsItem()
            }
            if(currentDestination == "ReadNewScreen") {
                currentScreen =  readNewItem()
            }

            TopAppBar(title = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val item = settingsItem()
                    Text(currentScreen?.label ?: "Unknown",fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 3.dp))
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                            .padding(8.dp)

                    )


                }

            },
                colors = TopAppBarColors(
                    scrolledContainerColor = Color(0xFFE6E6FA),
                    containerColor = Color(0xFFE6E6FA),
                    navigationIconContentColor = Color.Black,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )

            )
        },

        bottomBar = {
            NavigationBar(
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currntDestination = navBackStackEntry?.destination

                listOfNavItems().forEach { navItem ->
                    NavigationBarItem(

                        selected = currntDestination?.hierarchy?.any { it.route == navItem.route } == true,
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
                        }
                    )

                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
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
                    applicationContext = applicationContext,
                    getNewsViewModel = getNewsViewModel
                )
            }
            composable(route = Screens.NewsScreen.name) {
                NewsScreen(getNewsViewModel, navController)
            }
            composable(route = Screens.QuotesScreen.name) {
                QuotesScreen(getSharesService)
            }
            composable(route = Screens.HypothesesScreen.name) {
                HypothesesScreen(getShareQuotesService)
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
                    applicationContext = applicationContext,
                    historyShareViewModel = historyShareViewModel
                )
            }
            composable("ReadNewScreen") {
                ReadNewScreen(getNewsViewModel= getNewsViewModel)
            }


        }


    }
    }
    else {
        NoInternetScreen()
    }
}


