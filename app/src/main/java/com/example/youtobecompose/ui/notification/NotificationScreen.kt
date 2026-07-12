package com.example.youtobecompose.ui.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.youtobecompose.R
import com.example.youtobecompose.model.NotificationTab
import com.example.youtobecompose.ui.notification.all.NotificationAllRoute
import com.example.youtobecompose.ui.notification.mentions.NotificationMentionsRoute
import com.example.youtobecompose.ui.theme.YoutobeComposeTheme

@Composable
fun NotificationRoute(
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = hiltViewModel(),
    selectedTab: NotificationTab,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onTabSelected: (NotificationTab) -> Unit,
) {
    NotificationScreen(
        modifier = modifier,
        selectedTab = selectedTab,
        onBackClick = onBackClick,
        onSearchClick = onSearchClick,
        onMoreClick = {},
        onTabSelected = onTabSelected
    )
}

@Composable
fun NotificationTopBar(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier = Modifier.size(48.dp),
            onClick = {
                onBackClick()
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
        }

        Text(
            text = stringResource(R.string.notifications_title),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        IconButton(
            modifier = Modifier.size(48.dp),
            onClick = {
                onSearchClick()
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
        }

        IconButton(
            modifier = Modifier.size(48.dp),
            onClick = {
                onMoreClick()
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more_vertical),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
        }
    }
}

@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    selectedTab: NotificationTab,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMoreClick: () -> Unit,
    onTabSelected: (NotificationTab) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                NotificationTopBar(
                    onBackClick = onBackClick,
                    onSearchClick = onSearchClick,
                    onMoreClick = onMoreClick
                )

                NotificationTabSelector(
                    selectedTab = selectedTab,
                    onTabSelected = onTabSelected
                )
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (selectedTab) {
                NotificationTab.All -> {
                    NotificationAllRoute()
                }

                NotificationTab.Mentions -> {
                    NotificationMentionsRoute()
                }
            }
        }
    }
}

@Composable
fun NotificationTabSelector(
    selectedTab: NotificationTab,
    onTabSelected: (NotificationTab) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NotificationTabItem(
            selected = selectedTab == NotificationTab.All,
            tabLabel = R.string.notifications_filter_all,
            onClick = {
                onTabSelected(NotificationTab.All)
            }
        )

        Spacer(modifier = Modifier.width(10.dp))

        NotificationTabItem(
            selected = selectedTab == NotificationTab.Mentions,
            tabLabel = R.string.notifications_filter_mentions,
            onClick = {
                onTabSelected(NotificationTab.Mentions)
            }
        )
    }
}

@Composable
fun NotificationTabItem(
    selected: Boolean,
    tabLabel: Int,
    onClick: () -> Unit,
) {
    val textColor = if (selected) Color.White else Color.Black
    val backgroundColor = if (selected) Color.Black else Color.White

    Box(
        modifier = Modifier
            .clip(
                shape = RoundedCornerShape(10.dp)
            )
            .background(color = backgroundColor)
            .border(width = 1.dp, shape = RoundedCornerShape(10.dp), color = Color.Black)
            .selectable(
                selected = selected,
                role = Role.Tab,
                onClick = onClick
            )
            .padding(vertical = 8.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(tabLabel),
            modifier = Modifier,
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationScreenPreview() {
    YoutobeComposeTheme {
        NotificationScreen(
            selectedTab = NotificationTab.All,
            onBackClick = {},
            onSearchClick = {},
            onMoreClick = {},
            onTabSelected = {}
        )
    }
}
