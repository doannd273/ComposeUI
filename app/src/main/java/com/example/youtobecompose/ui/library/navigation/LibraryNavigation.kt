package com.example.youtobecompose.ui.library.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.youtobecompose.ui.library.LibraryRoute
import kotlinx.serialization.Serializable

@Serializable
data object LibraryGraph

@Serializable
data object LibraryDestination

fun NavGraphBuilder.libraryGraph() {
    navigation<LibraryGraph>(startDestination = LibraryDestination) {
        composable<LibraryDestination> {
            LibraryRoute()
        }
    }
}
