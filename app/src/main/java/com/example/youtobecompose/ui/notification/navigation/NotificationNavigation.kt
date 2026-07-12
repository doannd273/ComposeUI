package com.example.youtobecompose.ui.notification.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.youtobecompose.model.NotificationTab
import com.example.youtobecompose.ui.notification.NotificationRoute
import kotlinx.serialization.Serializable

@Serializable
data object NotificationGraph

@Serializable
data object NotificationAllDestination

@Serializable
data object NotificationMentionsDestination

fun NavHostController.navigateToNotification() {
    this.navigate(NotificationGraph) {
        launchSingleTop = true
    }
}

fun NavHostController.navigateToNotificationTab(notificationTab: NotificationTab) {
    val destination = when (notificationTab) {
        NotificationTab.All -> NotificationAllDestination
        NotificationTab.Mentions -> NotificationMentionsDestination
    }

    this.navigate(destination) {
        popUpTo<NotificationGraph> {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavGraphBuilder.notificationGraph(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onTabSelected: (NotificationTab) -> Unit,
) {
    navigation<NotificationGraph>(startDestination = NotificationAllDestination) {
        composable<NotificationAllDestination> {
            NotificationRoute(
                selectedTab = NotificationTab.All,
                onBackClick = onBackClick,
                onSearchClick = onSearchClick,
                onTabSelected = onTabSelected,
            )
        }

        composable<NotificationMentionsDestination> {
            NotificationRoute(
                selectedTab = NotificationTab.Mentions,
                onBackClick = onBackClick,
                onSearchClick = onSearchClick,
                onTabSelected = onTabSelected,
            )
        }
    }
}
