package com.example.youtobecompose.ui.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.youtobecompose.ui.search.SearchRoute
import kotlinx.serialization.Serializable

@Serializable
data object SearchGraph

@Serializable
data object SearchDestination

fun NavHostController.navigateToSearch() {
    this.navigate(SearchGraph) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.searchGraph(
    onBackClick: () -> Unit,
) {
    navigation<SearchGraph>(startDestination = SearchDestination) {
        composable<SearchDestination> {
            SearchRoute(
                onBackClick = onBackClick
            )
        }
    }
}
