package com.example.youtobecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.youtobecompose.bottombar.getBottomTabs
import com.example.youtobecompose.bottombar.isTabSelected
import com.example.youtobecompose.navigation.MainAppHost
import com.example.youtobecompose.ui.home.navigation.HomeGraph
import com.example.youtobecompose.ui.theme.YoutobeComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YoutobeComposeTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            MainTopBar(
                onCastClick = {},
                onNotificationsClick = {},
                onSearchClick = {},
                onAccountClick = {},
            )
        },
        bottomBar = {
            MainBottomBar(
                navController = navController
            )
        }) { innerPadding ->
        MainAppHost(
            modifier = Modifier.padding(paddingValues = innerPadding),
            navController = navController,
        )
    }
}

@Composable
fun MainBottomBar(
    navController: NavHostController
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        getBottomTabs().forEach { bottomTab ->
            val isSelected = isTabSelected(currentDestination, bottomTab.destinationClass)
            NavigationBarItem(
                selected = isSelected, colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = Color.Black,
                    unselectedIconColor = Color.Unspecified,
                    indicatorColor = Color.Transparent
                ), onClick = {
                    navController.navigate(bottomTab.destination) {
                        popUpTo<HomeGraph> {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }, icon = {
                    Icon(
                        painter = painterResource(id = bottomTab.icon),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp),
                    )
                }, label = {
                    Text(
                        text = stringResource(id = bottomTab.label),
                        modifier = Modifier,
                        style = MaterialTheme.typography.labelLarge,
                    )
                })

        }
    }
}

@Composable
fun MainTopBar(
    onCastClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onSearchClick: () -> Unit,
    onAccountClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconLogo(modifier = Modifier.weight(1f))
            ActionTopBar(
                onCastClick = onCastClick,
                onNotificationsClick = onNotificationsClick,
                onSearchClick = onSearchClick,
                onAccountClick = onAccountClick,
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray,
        )
    }


}

@Composable
fun ActionTopBar(
    onCastClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onSearchClick: () -> Unit,
    onAccountClick: () -> Unit,
) {
    IconButton(onClick = onCastClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_cast),
            contentDescription = stringResource(id = R.string.action_cast),
            modifier = Modifier.size(24.dp),
        )
    }

    IconButton(onClick = onNotificationsClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_notifications),
            contentDescription = stringResource(id = R.string.action_notifications),
            modifier = Modifier.size(24.dp),
        )
    }

    IconButton(onClick = onSearchClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = stringResource(id = R.string.action_search),
            modifier = Modifier.size(24.dp),
        )
    }

    IconButton(onClick = onAccountClick) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = stringResource(id = R.string.action_account),
            modifier = Modifier
                .size(24.dp)
                .background(
                    shape = CircleShape, color = Color.White
                )
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun IconLogo(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_youtube_play),
            contentDescription = "",
            modifier = Modifier
                .size(56.dp)
                .padding(5.dp),
            contentScale = ContentScale.Fit,
        )
        Text(
            text = stringResource(id = R.string.youtube_brand),
            modifier = Modifier.padding(2.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}


