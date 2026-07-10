package com.example.youtobecompose.ui.subscription.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.youtobecompose.ui.subscription.SubscriptionRoute
import kotlinx.serialization.Serializable

@Serializable
data object SubscriptionGraph

@Serializable
data object SubscriptionDestination

fun NavGraphBuilder.subscriptionGraph() {
    navigation<SubscriptionGraph>(startDestination = SubscriptionDestination) {
        composable<SubscriptionDestination> {
            SubscriptionRoute()
        }
    }
}
