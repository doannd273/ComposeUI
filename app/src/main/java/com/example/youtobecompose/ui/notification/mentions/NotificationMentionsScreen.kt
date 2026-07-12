package com.example.youtobecompose.ui.notification.mentions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.youtobecompose.R

@Composable
fun NotificationMentionsRoute(
    modifier: Modifier = Modifier,
) {
    NotificationMentionsScreen(
        modifier = modifier,
    )
}

@Composable
fun NotificationMentionsScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.notifications_mentions_screen_title),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
        )
    }
}
