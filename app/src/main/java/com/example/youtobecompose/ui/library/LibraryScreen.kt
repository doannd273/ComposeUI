package com.example.youtobecompose.ui.library

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text

@Composable
fun LibraryRoute(
    modifier: Modifier = Modifier,
) {
    LibraryScreen(
        modifier = modifier,
    )
}

@Composable
internal fun LibraryScreen(
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Library",
        modifier = modifier,
    )
}
