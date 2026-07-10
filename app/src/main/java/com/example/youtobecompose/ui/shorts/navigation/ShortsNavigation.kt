package com.example.youtobecompose.ui.shorts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.youtobecompose.ui.shorts.ShortsRoute
import kotlinx.serialization.Serializable

@Serializable
data object ShortsGraph

@Serializable
data object ShortsDestination

fun NavGraphBuilder.shortsGraph(
    onBackClick: () -> Unit,
) {
    navigation<ShortsGraph>(startDestination = ShortsDestination) {
        composable<ShortsDestination> {
            ShortsRoute(
                onBackClick = onBackClick,
            )
        }
    }
}
