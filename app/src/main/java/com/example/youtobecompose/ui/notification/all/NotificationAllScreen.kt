package com.example.youtobecompose.ui.notification.all

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil3.compose.AsyncImage
import com.example.youtobecompose.R
import com.example.youtobecompose.model.NotificationGroupModel
import com.example.youtobecompose.model.NotificationModel
import com.example.youtobecompose.ui.theme.YoutobeComposeTheme

@Composable
fun NotificationAllRoute(
    modifier: Modifier = Modifier,
    viewModel: NotificationAllViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    NotificationAllScreen(
        modifier = modifier,
        state = state,
        onEnableClick = {
            viewModel.onEvent(NotificationAllEvent.EnableNotificationsClick)
        },
        onDismissClick = {
            viewModel.onEvent(NotificationAllEvent.DismissBannerClick)
        },
        onGroupClick = { groupId ->
            viewModel.onEvent(NotificationAllEvent.GroupClick(groupId = groupId))
        },
        onNotificationClick = { notification ->
            viewModel.onEvent(NotificationAllEvent.NotificationClick(notification = notification))
        },
        onNotificationMoreClick = { notification ->
            viewModel.onEvent(
                NotificationAllEvent.NotificationMoreClick(notification = notification)
            )
        }
    )

    LaunchedEffect(viewModel.effect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    NotificationAllEffect.DismissBannerClickSuccess -> {
                        Toast.makeText(context, "DismissBannerClickSuccess", Toast.LENGTH_SHORT)
                            .show()
                    }

                    NotificationAllEffect.EnableNotificationsClickSuccess -> {
                        Toast.makeText(
                            context,
                            "EnableNotificationsClickSuccess",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is NotificationAllEffect.NotificationClickSuccess -> {
                        Toast.makeText(
                            context,
                            "NotificationClickSuccess: ${effect.notificationTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is NotificationAllEffect.NotificationMoreClickSuccess -> {
                        Toast.makeText(
                            context,
                            "NotificationMoreClickSuccess: ${effect.notificationTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationAllScreen(
    modifier: Modifier = Modifier,
    state: NotificationAllState,
    onEnableClick: () -> Unit,
    onDismissClick: () -> Unit,
    onGroupClick: (String) -> Unit,
    onNotificationClick: (NotificationModel) -> Unit,
    onNotificationMoreClick: (NotificationModel) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item(
            key = "notification-banner",
            contentType = "notification-banner"
        ) {
            EnableNotificationBanner(
                onEnableClick = onEnableClick,
                onDismissClick = onDismissClick
            )
        }

        state.groups.forEach { group ->
            item(
                key = "notification-group-${group.groupId}",
                contentType = "notification-group"
            ) {
                NotificationHeaderChannel(
                    group = group,
                    onGroupClick = {
                        onGroupClick(group.groupId)
                    }
                )
            }

            if (group.isExpand) {
                items(
                    items = group.notifications,
                    key = { notification -> notification.id  },
                    contentType = { "notification-item" }
                ) { item ->
                    NotificationItem(
                        notification = item,
                        onItemClick = {
                            onNotificationClick(item)
                        },
                        onMoreClick = {
                            onNotificationMoreClick(item)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EnableNotificationBanner(
    onEnableClick: () -> Unit,
    onDismissClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_notifications),
            contentDescription = "",
            modifier = Modifier.size(24.dp),
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(R.string.notifications_enable_title),
                modifier = Modifier,
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(R.string.notifications_enable_description),
                modifier = Modifier,
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextButton(
                modifier = Modifier.clip(
                    RoundedCornerShape(15.dp)
                ).border(
                    width = 1.dp, color = Color.Red,
                    shape = RoundedCornerShape(15.dp)
                ).background(color = Color.White),
                onClick = {
                    onEnableClick()
                },
            ) {
                Text(
                    text = stringResource(R.string.notifications_enable_button),
                    color = Color.Red,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = {
                onDismissClick()
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notifications_close),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationAllScreenPreview() {
    val state = NotificationAllState(
        groups = listOf(
            NotificationGroupModel(
                groupId = "pewpew",
                channelName = "PEWPEW",
                avatarUrl = "https://picsum.photos/100",
                isExpand = true,
                notifications = listOf(
                    NotificationModel(
                        id = "notification-1",
                        title = "Anh thấy mùa 17 hay mùa 16 hay hơn? -ADMIN POST- #pewpew #pewnoy",
                        timeText = "3 tuần trước",
                        thumbnailUrl = "https://picsum.photos/320/180",
                    ),
                    NotificationModel(
                        id = "notification-2",
                        title = "Video mới từ kênh bạn theo dõi đã sẵn sàng để xem",
                        timeText = "1 ngày trước",
                        thumbnailUrl = "https://picsum.photos/321/180",
                    )
                )
            )
        )
    )

    YoutobeComposeTheme {
        NotificationAllScreen(
            state = state,
            onEnableClick = {},
            onDismissClick = {},
            onGroupClick = {},
            onNotificationClick = {},
            onNotificationMoreClick = {}
        )
    }
}

@Composable
fun NotificationItem(
    notification: NotificationModel,
    onItemClick: () -> Unit,
    onMoreClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            }
            .padding(all = 8.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = notification.title,
                modifier = Modifier,
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black,
            )

            Text(
                text = notification.timeText,
                modifier = Modifier,
                style = MaterialTheme.typography.labelMedium,
                color = Color.DarkGray,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        AsyncImage(
            model = notification.thumbnailUrl,
            contentDescription = "",
            error = painterResource(R.drawable.ic_background_short),
            modifier = Modifier
                .size(width = 144.dp, height = 81.dp),
            contentScale = ContentScale.Crop,
        )

        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = {
                onMoreClick()
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more_vertical),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationItemPreview() {
    val notification = NotificationModel(
        id = "notification-1",
        title = "Anh thấy mùa 17 hay mùa 16 hay hơn? -ADMIN POST- #pewpew #pewnoy",
        timeText = "3 tuần trước",
        thumbnailUrl = "https://picsum.photos/320/180",
    )

    YoutobeComposeTheme {
        NotificationItem(
            notification = notification,
            onItemClick = {},
            onMoreClick = {}
        )
    }
}

@Composable
fun NotificationHeaderChannel(
    group: NotificationGroupModel,
    onGroupClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = group.avatarUrl,
            contentDescription = "",
            error = painterResource(R.drawable.ic_avatar),
            placeholder = painterResource(R.drawable.ic_avatar),
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = group.channelName,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = {
                onGroupClick()
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_library_arrow_drop_down),
                contentDescription = "",
                modifier = Modifier.size(24.dp).rotate(
                    if (group.isExpand) 0f else -90f
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationHeaderChannelPreview() {
    val group = NotificationGroupModel(
        groupId = "pewpew",
        channelName = "PEWPEW",
        avatarUrl = "https://picsum.photos/100",
        isExpand = true,
        notifications = listOf(),
    )

    YoutobeComposeTheme {
        NotificationHeaderChannel(
            group = group,
            onGroupClick = {}
        )
    }

}
