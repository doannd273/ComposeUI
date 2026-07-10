package com.example.youtobecompose.ui.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.youtobecompose.ui.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeGraph

@Serializable
data object HomeDestination

fun NavGraphBuilder.homeGraph() {
    navigation<HomeGraph>(startDestination = HomeDestination) {
        composable<HomeDestination> {
            HomeRoute()
        }
    }
}
