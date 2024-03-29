package com.example.test_compose.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.example.test_compose.R
import androidx.compose.ui.res.vectorResource


data class NavItem(
    val label : String,
    val icon : ImageVector,
    val route : String
)
@Composable
fun listOfNavItems() : List<NavItem> {
    return listOf(
        NavItem(
            label = "home",
            icon = Icons.Default.Home,
            route = Screens.HomeScreen.name
        ),

        NavItem(
            label = "news",
            icon = ImageVector.vectorResource(R.drawable.news_icon),
            route = Screens.NewsScreen.name
        ),
        NavItem(
            label = "quotes",
            icon = ImageVector.vectorResource(R.drawable.quotes_icon),
            route = Screens.QuotesScreen.name
        ),
        NavItem(
            label = "hypothesis",
            icon = ImageVector.vectorResource(R.drawable.hypothesis),
            route = Screens.HypothesesScreen.name,
        ),
        NavItem(
            label = "history",
            icon = ImageVector.vectorResource(R.drawable.history_icon),
            route = Screens.HistoryScreen.name
        )

    )
}

@Composable
fun settingsItem() : NavItem {
    return NavItem(
        label = "settings",
        icon = ImageVector.vectorResource(R.drawable.settings_icon),
        route = Screens.SettingsScreen.name
    )
}

fun AddSharesItem() : NavItem{
    return NavItem(
        label = "addShares",
        icon = Icons.Default.Share,
        route = Screens.AddSharesScreen.name
    )
}
