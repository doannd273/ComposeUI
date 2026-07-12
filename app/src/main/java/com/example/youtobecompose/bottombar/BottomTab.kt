package com.example.youtobecompose.bottombar

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.youtobecompose.R
import com.example.youtobecompose.ui.home.navigation.HomeGraph
import com.example.youtobecompose.ui.library.navigation.LibraryGraph
import com.example.youtobecompose.ui.shorts.navigation.ShortsGraph
import com.example.youtobecompose.ui.subscription.navigation.SubscriptionGraph
import kotlin.reflect.KClass

data class BottomTab(
    val label: Int,
    val icon: Int,
    val destination: Any,
    val destinationClass: KClass<out Any>
)

fun getBottomTabs(): List<BottomTab> {

    return listOf(

        BottomTab(
            label = R.string.nav_home,
            icon = R.drawable.ic_home,
            destination = HomeGraph,
            destinationClass = HomeGraph::class
        ),

        BottomTab(
            label = R.string.nav_shorts,
            icon = R.drawable.ic_shorts_outline,
            destination = ShortsGraph,
            destinationClass = ShortsGraph::class
        ),

        BottomTab(
            label = R.string.nav_subscription,
            icon = R.drawable.ic_subscription,
            destination = SubscriptionGraph,
            destinationClass = SubscriptionGraph::class
        ),

        BottomTab(
            label = R.string.nav_library,
            icon = R.drawable.ic_library,
            destination = LibraryGraph,
            destinationClass = LibraryGraph::class
        ),
    )
}

fun isTabSelected(
    navDestination: NavDestination?,
    destinationClass: KClass<out Any>,
): Boolean {
    if (navDestination == null) {
        return false
    }
    return navDestination.hasAnyRoute(destinationClass)
}

fun isTabShortSelected(
    navDestination: NavDestination?,
): Boolean {
    if (navDestination == null) {
        return false
    }
    return navDestination.hasAnyRoute(ShortsGraph::class)
}

fun isTabOpenBottomBar(
    navDestination: NavDestination?,
): Boolean {
    if (navDestination == null) {
        return false
    }
    return navDestination.hasAnyRoute(
        HomeGraph::class,
        ShortsGraph::class,
        SubscriptionGraph::class,
        LibraryGraph::class
    )
}

fun isTabOpenTopAppBar(
    navDestination: NavDestination?,
): Boolean {
    if (navDestination == null) {
        return false
    }
    return navDestination.hasAnyRoute(
        HomeGraph::class,
        SubscriptionGraph::class,
        LibraryGraph::class
    )
}

private fun NavDestination.hasAnyRoute(
    vararg routeClasses: KClass<out Any>,
): Boolean {
    return hierarchy.any { destination ->
        routeClasses.any { routeClass ->
            destination.hasRoute(routeClass)
        }
    }
}
