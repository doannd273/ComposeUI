package com.example.youtobecompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.youtobecompose.ui.home.navigation.HomeGraph
import com.example.youtobecompose.ui.home.navigation.homeGraph
import com.example.youtobecompose.ui.library.navigation.libraryGraph
import com.example.youtobecompose.ui.notification.navigation.navigateToNotificationTab
import com.example.youtobecompose.ui.notification.navigation.notificationGraph
import com.example.youtobecompose.ui.profile.navigation.profileGraph
import com.example.youtobecompose.ui.search.navigation.navigateToSearch
import com.example.youtobecompose.ui.search.navigation.searchGraph
import com.example.youtobecompose.ui.shorts.navigation.shortsGraph
import com.example.youtobecompose.ui.subscription.navigation.subscriptionGraph

@Composable
fun MainAppHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeGraph
    ) {
        homeGraph()
        shortsGraph()
        subscriptionGraph()
        libraryGraph()
        profileGraph(
            onBackClick = {
                navController.popBackStack()
            }
        )
        notificationGraph(
            onBackClick = {
                navController.popBackStack()
            },
            onSearchClick = {
                navController.navigateToSearch()
            },
            onTabSelected = { notificationTab ->
                navController.navigateToNotificationTab(notificationTab)
            }
        )
        searchGraph(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}
