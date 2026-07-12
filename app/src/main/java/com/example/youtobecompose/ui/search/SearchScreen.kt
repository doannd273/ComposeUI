package com.example.youtobecompose.ui.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    SearchScreen(
        modifier = modifier,
    )
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
) {

}
