package com.example.youtobecompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.youtobecompose.ui.home.navigation.HomeGraph
import com.example.youtobecompose.ui.home.navigation.homeGraph
import com.example.youtobecompose.ui.library.navigation.libraryGraph
import com.example.youtobecompose.ui.shorts.navigation.ShortsGraph
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
        shortsGraph(
            onBackClick = {
                navController.navigate(HomeGraph) {
                    popUpTo<ShortsGraph> {
                        inclusive = true
                    }
                }
            }
        )
        subscriptionGraph()
        libraryGraph()
    }
}
