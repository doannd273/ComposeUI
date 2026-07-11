package com.example.youtobecompose.ui.subscription

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil3.compose.AsyncImage
import com.example.youtobecompose.R
import com.example.youtobecompose.model.Filter
import com.example.youtobecompose.model.VideoModel
import com.example.youtobecompose.ui.home.FilterItem
import com.example.youtobecompose.ui.theme.YoutobeComposeTheme

@Composable
fun SubscriptionRoute(
    modifier: Modifier = Modifier,
    viewModel: SubscriptionViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    SubscriptionScreen(
        modifier = modifier,
        state = state,
        onChannelClick = {
            viewModel.onEvent(SubscriptionEvent.ChannelClick(channel = it))
        },
        onAllChannelsClick = {
            viewModel.onEvent(SubscriptionEvent.AllChannelsClick)
        },
        onFilterClick = {
            viewModel.onEvent(SubscriptionEvent.FilterClick(filter = it))
        },
        onVideoClick = {
            viewModel.onEvent(SubscriptionEvent.VideoClick(video = it))
        },
        onVideoMoreClick = {
            viewModel.onEvent(SubscriptionEvent.VideoMoreClick(video = it))
        },
    )

    LaunchedEffect(viewModel.effect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is SubscriptionEffect.ChannelClickSuccess -> {
                        Toast.makeText(
                            context,
                            "ChannelClickSuccess: ${effect.channelName}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    SubscriptionEffect.AllChannelsClickSuccess -> {
                        Toast.makeText(context, "AllChannelsClickSuccess", Toast.LENGTH_SHORT)
                            .show()
                    }

                    is SubscriptionEffect.FilterClickSuccess -> {
                        Toast.makeText(
                            context,
                            "FilterClickSuccess: ${effect.filterName}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is SubscriptionEffect.VideoClickSuccess -> {
                        Toast.makeText(
                            context,
                            "VideoClickSuccess: ${effect.videoTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is SubscriptionEffect.VideoMoreClickSuccess -> {
                        Toast.makeText(
                            context,
                            "VideoMoreClickSuccess: ${effect.videoTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}

@Composable
fun SubscriptionScreen(
    modifier: Modifier = Modifier,
    state: SubscriptionState,
    onChannelClick: (SubscriptionChannelModel) -> Unit,
    onAllChannelsClick: () -> Unit,
    onFilterClick: (Filter) -> Unit,
    onVideoClick: (VideoModel) -> Unit,
    onVideoMoreClick: (VideoModel) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(bottom = 12.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        item(
            key = "channels",
            contentType = "channels",
        ) {
            SubscriptionChannelSection(
                channels = state.channels,
                onChannelClick = onChannelClick,
                onAllChannelsClick = onAllChannelsClick,
            )
        }

        item(
            key = "channel_divider",
            contentType = "divider",
        ) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 24.dp),
                thickness = 1.dp,
                color = Color.LightGray,
            )
        }

        item(
            key = "filters",
            contentType = "filters",
        ) {
            SubscriptionFilterSection(
                filters = state.filters,
                onFilterClick = onFilterClick,
            )
        }

        items(
            items = state.videos,
            key = { it.id },
            contentType = { "subscription_video" },
        ) { video ->
            SubscriptionVideoItem(
                video = video,
                onVideoClick = onVideoClick,
                onVideoMoreClick = onVideoMoreClick,
            )
        }
    }
}

@Composable
private fun SubscriptionChannelSection(
    channels: List<SubscriptionChannelModel>,
    onChannelClick: (SubscriptionChannelModel) -> Unit,
    onAllChannelsClick: () -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.spacedBy(22.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(
            items = channels,
            key = { it.id },
        ) { channel ->
            SubscriptionChannelItem(
                channel = channel,
                onChannelClick = onChannelClick,
            )
        }

        item(
            key = "all_channels",
        ) {
            Text(
                text = stringResource(R.string.subscription_all_channels),
                modifier = Modifier.clickable(onClick = onAllChannelsClick),
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF065FD4),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun SubscriptionChannelItem(
    channel: SubscriptionChannelModel,
    onChannelClick: (SubscriptionChannelModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .width(84.dp)
            .clickable {
                onChannelClick(channel)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Box(
            modifier = Modifier.size(74.dp),
        ) {
            AsyncImage(
                model = channel.avatarUrl,
                contentDescription = "",
                error = painterResource(R.drawable.ic_profile_avatar),
                placeholder = painterResource(R.drawable.ic_profile_avatar),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )

            if (channel.hasNewContent) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .align(Alignment.BottomEnd)
                        .clip(CircleShape)
                        .background(Color(0xFF065FD4)),
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = channel.name,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.labelLarge,
            color = Color.DarkGray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun SubscriptionFilterSection(
    filters: List<Filter>,
    onFilterClick: (Filter) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = filters,
            key = { it.id },
        ) { filter ->
            FilterItem(
                filter = filter,
                filterItemClick = {
                    onFilterClick(filter)
                },
            )
        }
    }
}

@Composable
private fun SubscriptionVideoItem(
    video: VideoModel,
    onVideoClick: (VideoModel) -> Unit,
    onVideoMoreClick: (VideoModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                onVideoClick(video)
            },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
        ) {
            AsyncImage(
                model = video.thumbnailUrl,
                contentDescription = "",
                error = painterResource(R.drawable.ic_background_error),
                placeholder = painterResource(R.drawable.ic_background_error),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )

            ShortsBadge(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 18.dp, bottom = 14.dp),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
        ) {
            AsyncImage(
                model = video.avatarUrl,
                contentDescription = "",
                error = painterResource(R.drawable.ic_profile_avatar),
                placeholder = painterResource(R.drawable.ic_profile_avatar),
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = video.title,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = video.metadata,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            IconButton(
                modifier = Modifier.size(42.dp),
                onClick = {
                    onVideoMoreClick(video)
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_more_vertical),
                    contentDescription = "",
                    modifier = Modifier.size(28.dp),
                )
            }
        }
    }
}

@Composable
private fun ShortsBadge(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_shorts),
            contentDescription = "",
            modifier = Modifier.size(16.dp),
            tint = Color.Unspecified,
        )

        Text(
            text = stringResource(R.string.subscription_shorts_badge),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SubscriptionScreenPreview() {
    YoutobeComposeTheme {
        SubscriptionScreen(
            state = SubscriptionState(
                channels = listOf(
                    SubscriptionChannelModel(
                        id = "channel-1",
                        avatarUrl = "https://i.pravatar.cc/200?img=13",
                        name = "Like Nastya",
                        hasNewContent = true,
                    ),
                    SubscriptionChannelModel(
                        id = "channel-2",
                        avatarUrl = "https://i.pravatar.cc/200?img=14",
                        name = "Bassera",
                        hasNewContent = true,
                    ),
                ),
                filters = listOf(
                    Filter(id = 1, name = "All", isSelected = true),
                    Filter(id = 2, name = "Today", isSelected = false),
                    Filter(id = 3, name = "Yesterday", isSelected = false),
                ),
                videos = listOf(
                    VideoModel(
                        id = "subscription-video-1",
                        thumbnailUrl = "https://picsum.photos/id/1076/720/405",
                        avatarUrl = "https://i.pravatar.cc/200?img=13",
                        title = "Heart Touching Nasheed #Shorts",
                        metadata = "19,210,251 viewsJul • 1, 2016",
                    ),
                )
            ),
            onChannelClick = {},
            onAllChannelsClick = {},
            onFilterClick = {},
            onVideoClick = {},
            onVideoMoreClick = {},
        )
    }
}
