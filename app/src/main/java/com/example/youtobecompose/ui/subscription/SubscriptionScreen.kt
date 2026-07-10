package com.example.youtobecompose.ui.subscription

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text

@Composable
fun SubscriptionRoute(
    modifier: Modifier = Modifier,
) {
    SubscriptionScreen(
        modifier = modifier,
    )
}

@Composable
internal fun SubscriptionScreen(
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Subscription",
        modifier = modifier,
    )
}
