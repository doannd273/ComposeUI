package com.example.youtobecompose.ui.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.youtobecompose.ui.profile.ProfileRoute
import kotlinx.serialization.Serializable

@Serializable
data object ProfileGraph

@Serializable
data object ProfileDestination

fun NavHostController.navigateToProfile() {
    this.navigate(ProfileGraph) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.profileGraph(onBackClick: () -> Unit,) {
    navigation<ProfileGraph>(startDestination = ProfileDestination) {
        composable<ProfileDestination> {
            ProfileRoute(onBackClick = onBackClick)
        }
    }
}
