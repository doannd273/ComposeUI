package com.example.youtobecompose.ui.shorts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text

@Composable
fun ShortsRoute(
    modifier: Modifier = Modifier,
) {
    ShortsScreen(
        modifier = modifier,
    )
}

@Composable
internal fun ShortsScreen(
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Shorts",
        modifier = modifier,
    )
}
