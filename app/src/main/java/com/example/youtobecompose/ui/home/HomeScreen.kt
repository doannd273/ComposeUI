package com.example.youtobecompose.ui.home

import android.widget.Toast
import androidx.compose.foundation.Image
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
import com.example.youtobecompose.ui.home.model.Filter
import com.example.youtobecompose.ui.home.model.HomeFeed
import com.example.youtobecompose.ui.home.model.ShortModel
import com.example.youtobecompose.ui.home.model.VideoModel
import com.example.youtobecompose.ui.theme.YoutobeComposeTheme

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    HomeScreen(
        modifier = modifier,
        state = state,
        onExploreClick = {
            viewModel.onEvent(HomeEvent.ExploreClick)
        },
        onFilterItemClick = {
            viewModel.onEvent(HomeEvent.FilterClick(it))
        },
        onVideoClick = {
            viewModel.onEvent(HomeEvent.VideoClick(it))
        },
        onVideoMoreClick = {
            viewModel.onEvent(HomeEvent.VideoMoreClick(it))
        },
        onShortClick = {
            viewModel.onEvent(HomeEvent.ShortClick(it))
        },
        onShortMoreClick = {
            viewModel.onEvent(HomeEvent.ShortMoreClick(it))
        }
    )

    LaunchedEffect(viewModel.effect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    HomeEffect.ExploreSuccess -> {
                        Toast.makeText(context, "ExploreSuccess", Toast.LENGTH_SHORT).show()
                    }

                    is HomeEffect.FilterSuccess -> {
                        Toast.makeText(
                            context,
                            "FilterSuccess: ${effect.filterName}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is HomeEffect.VideoClickSuccess -> {
                        Toast.makeText(
                            context,
                            "VideoClickSuccess: ${effect.videoTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is HomeEffect.VideoMoreClickSuccess -> {
                        Toast.makeText(
                            context,
                            "VideoMoreClickSuccess: ${effect.videoTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is HomeEffect.ShortClickSuccess -> {
                        Toast.makeText(
                            context,
                            "ShortClickSuccess: ${effect.shortTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is HomeEffect.ShortMoreClickSuccess -> {
                        Toast.makeText(
                            context,
                            "ShortMoreClickSuccess: ${effect.shortTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onExploreClick: () -> Unit,
    onFilterItemClick: (Filter) -> Unit,
    onVideoClick: (VideoModel) -> Unit,
    onVideoMoreClick: (VideoModel) -> Unit,
    onShortClick: (ShortModel) -> Unit,
    onShortMoreClick: (ShortModel) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().background(color = Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        FilterTopBar(
            filters = state.filters,
            onExploreClick = onExploreClick,
            onFilterItemClick = onFilterItemClick,
        )

        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(0.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            items(
                items = state.feedItems,
                key = { it.key },
                contentType = { item ->
                    when (item) {
                        is HomeFeed.Video -> "video"
                        is HomeFeed.Shorts -> "shorts"
                    }
                }
            ) { item ->
                when (item) {
                    is HomeFeed.Video -> {
                        VideoFeedItem(
                            video = item.video,
                            onVideoClick = onVideoClick,
                            onVideoMoreClick = onVideoMoreClick
                        )

                        HorizontalDivider(
                            thickness = 6.dp,
                            color = Color.LightGray
                        )
                    }

                    is HomeFeed.Shorts -> {
                        ShortsSection(
                            shorts = item.shorts,
                            onShortClick = onShortClick,
                            onShortMoreClick = onShortMoreClick,
                        )

                        HorizontalDivider(
                            thickness = 6.dp,
                            color = Color.LightGray
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    val filters: List<Filter> = listOf(
        Filter(id = 1, name = "All", isSelected = true),
        Filter(id = 2, name = "Mixed", isSelected = false),
        Filter(id = 3, name = "Music", isSelected = false),
        Filter(id = 4, name = "Graphic", isSelected = false),
        Filter(id = 5, name = "Film", isSelected = false),
        Filter(id = 6, name = "Live", isSelected = false),
    )

    val feedItems: List<HomeFeed> = listOf(
        HomeFeed.Video(
            video = VideoModel(
                id = "video-1",
                thumbnailUrl = "https://picsum.photos/id/1015/480/270",
                avatarUrl = "https://i.pravatar.cc/200?img=12",
                title = "The Beauty of Existence - Heart Touching Nasheed",
                metadata = "19M views • Jul 1, 2016",
            )
        ),
        HomeFeed.Shorts(
            sectionId = "shorts-1",
            shorts = listOf(
                ShortModel(
                    id = "short-1",
                    thumbnailUrl = "https://picsum.photos/id/1025/240/426",
                    title = "Cute dog playing outside",
                    channelAvatarUrl = "https://i.pravatar.cc/200?img=11",
                    channelName = "@ChannelName",
                    likeCount = "2.3k",
                    dislikeLabel = "Dislike",
                    commentCount = "44",
                    shareLabel = "Share",
                    soundThumbnailUrl = "https://i.pravatar.cc/200?img=11",
                    isSubscribed = false,
                ),
                ShortModel(
                    id = "short-2",
                    thumbnailUrl = "https://picsum.photos/id/1011/240/426",
                    title = "Beautiful nature in the morning",
                    channelAvatarUrl = "https://i.pravatar.cc/200?img=22",
                    channelName = "@NatureDaily",
                    likeCount = "856K",
                    dislikeLabel = "Dislike",
                    commentCount = "128",
                    shareLabel = "Share",
                    soundThumbnailUrl = "https://i.pravatar.cc/200?img=22",
                    isSubscribed = false,
                ),
                ShortModel(
                    id = "short-3",
                    thumbnailUrl = "https://picsum.photos/id/1035/240/426",
                    title = "A peaceful day near the ocean",
                    channelAvatarUrl = "https://i.pravatar.cc/200?img=33",
                    channelName = "@OceanLife",
                    likeCount = "2.4M",
                    dislikeLabel = "Dislike",
                    commentCount = "302",
                    shareLabel = "Share",
                    soundThumbnailUrl = "https://i.pravatar.cc/200?img=33",
                    isSubscribed = false,
                ),
            )
        ),
        HomeFeed.Video(
            video = VideoModel(
                id = "video-2",
                thumbnailUrl = "https://picsum.photos/id/1043/480/270",
                avatarUrl = "https://i.pravatar.cc/200?img=32",
                title = "Compose UI Patterns for Modern Android Apps",
                metadata = "428K views • 2 weeks ago",
            )
        ),
        HomeFeed.Shorts(
            sectionId = "shorts-2",
            shorts = listOf(
                ShortModel(
                    id = "short-4",
                    thumbnailUrl = "https://picsum.photos/id/1062/240/426",
                    title = "Street food moments around the city",
                    channelAvatarUrl = "https://i.pravatar.cc/200?img=44",
                    channelName = "@StreetBites",
                    likeCount = "634K",
                    dislikeLabel = "Dislike",
                    commentCount = "76",
                    shareLabel = "Share",
                    soundThumbnailUrl = "https://i.pravatar.cc/200?img=44",
                    isSubscribed = false,
                ),
                ShortModel(
                    id = "short-5",
                    thumbnailUrl = "https://picsum.photos/id/1074/240/426",
                    title = "Quick desk setup transformation",
                    channelAvatarUrl = "https://i.pravatar.cc/200?img=55",
                    channelName = "@SetupLab",
                    likeCount = "978K",
                    dislikeLabel = "Dislike",
                    commentCount = "211",
                    shareLabel = "Share",
                    soundThumbnailUrl = "https://i.pravatar.cc/200?img=55",
                    isSubscribed = false,
                ),
                ShortModel(
                    id = "short-6",
                    thumbnailUrl = "https://picsum.photos/id/1084/240/426",
                    title = "Relaxing sunset travel clip",
                    channelAvatarUrl = "https://i.pravatar.cc/200?img=66",
                    channelName = "@TravelMood",
                    likeCount = "3.1M",
                    dislikeLabel = "Dislike",
                    commentCount = "509",
                    shareLabel = "Share",
                    soundThumbnailUrl = "https://i.pravatar.cc/200?img=66",
                    isSubscribed = false,
                ),
            )
        ),
        HomeFeed.Video(
            video = VideoModel(
                id = "video-3",
                thumbnailUrl = "https://picsum.photos/id/1067/480/270",
                avatarUrl = "https://i.pravatar.cc/200?img=45",
                title = "Building a Clean Android Architecture from Scratch",
                metadata = "92K views • 3 days ago",
            )
        ),
    )

    YoutobeComposeTheme {
        HomeScreen(
            state = HomeState(
                filters = filters,
                feedItems = feedItems,
            ),
            onExploreClick = {},
            onFilterItemClick = {},
            onVideoClick = {},
            onVideoMoreClick = {},
            onShortClick = {},
            onShortMoreClick = {},
        )
    }
}

@Composable
fun FilterTopBar(
    filters: List<Filter>,
    onExploreClick: () -> Unit,
    onFilterItemClick: (Filter) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 4.dp, vertical = 8.dp
            ),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ExploreView(
            modifier = Modifier.padding(start = 8.dp),
            onExploreClick = onExploreClick
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .width(1.dp)
                .height(32.dp)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.width(8.dp))

        LazyRow(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(
                items = filters,
                key = { it.id },
            ) { filter ->
                FilterItem(
                    filter = filter,
                    filterItemClick = {
                        onFilterItemClick(filter)
                    }
                )
            }
        }
    }
}

@Composable
fun ExploreView(
    modifier: Modifier = Modifier,
    onExploreClick: () -> Unit,
) {
    val shapeBackground = RoundedCornerShape(3.dp)

    Row(
        modifier = modifier
            .clip(shape = shapeBackground)
            .background(
                color = Color.LightGray,
                shape = shapeBackground
            )
            .clickable {
                onExploreClick()
            }
            .padding(all = 6.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_explore),
            contentDescription = "",
            modifier = Modifier.size(24.dp),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(R.string.chip_explore),
            modifier = Modifier,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun FilterItem(
    filter: Filter,
    filterItemClick: () -> Unit,
) {
    val colorBackground = if (filter.isSelected) Color.Black else Color.LightGray
    val shapeBackground = RoundedCornerShape(size = 16.dp)

    Box(
        modifier = Modifier
            .background(color = colorBackground, shape = shapeBackground)
            .clip(shapeBackground)
            .clickable {
                filterItemClick()
            }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = filter.name,
            style = MaterialTheme.typography.labelLarge,
            color = if (filter.isSelected) Color.White else Color.Black,
        )
    }
}

@Composable
fun VideoFeedItem(
    video: VideoModel,
    onVideoClick: (VideoModel) -> Unit,
    onVideoMoreClick: (VideoModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onVideoClick(video)
            }.padding(vertical = 8.dp, horizontal = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        AsyncImage(
            model = video.thumbnailUrl,
            contentDescription = "",
            error = painterResource(R.drawable.ic_background_error),
            placeholder = painterResource(R.drawable.ic_background_error),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .aspectRatio(16f / 9f),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White
                )
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Start,
        ) {
            AsyncImage(
                model = video.avatarUrl,
                contentDescription = "",
                error = painterResource(R.drawable.ic_profile_avatar),
                placeholder = painterResource(R.drawable.ic_profile_avatar),
                modifier = Modifier.size(80.dp).background(
                    color = Color.LightGray,
                    shape = CircleShape,
                ).clip(CircleShape),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = video.title,
                    modifier = Modifier,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = video.metadata,
                    modifier = Modifier,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.DarkGray,
                )
            }

            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = {
                    onVideoMoreClick(video)
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
}

@Preview(showBackground = true)
@Composable
private fun VideoFeedItemPreview() {
    YoutobeComposeTheme {
        VideoFeedItem(
            video = VideoModel(
                id = "video-1",
                thumbnailUrl = "https://picsum.photos/id/1015/480/270",
                avatarUrl = "https://i.pravatar.cc/200?img=12",
                title = "The Beauty of Existence - Heart Touching Nasheed",
                metadata = "19M views • Jul 1, 2016",
            ),
            onVideoClick = {},
            onVideoMoreClick = {},
        )
    }
}

@Composable
fun ShortVideoCard(
    short: ShortModel,
    onShortClick: (ShortModel) -> Unit,
    onShortMoreClick: (ShortModel) -> Unit
) {
    Box(
        modifier = Modifier
            .size(
                width = 144.dp,
                height = 256.dp,
            )
            .aspectRatio(9f / 16f)
            .background(Color.LightGray)
            .clickable {
                onShortClick(short)
            },
    ) {
        AsyncImage(
            model = short.thumbnailUrl,
            error = painterResource(R.drawable.ic_background_short),
            placeholder = painterResource(R.drawable.ic_background_short),
            contentDescription = "",
            modifier = Modifier.size(
                width = 144.dp,
                height = 256.dp,
            ),
            contentScale = ContentScale.Crop,
        )

        IconButton(
            modifier = Modifier
                .size(24.dp)
                .align(
                    Alignment.TopEnd
                ),
            onClick = {
                onShortMoreClick(short)
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more_vertical),
                contentDescription = "",
                modifier = Modifier.background(
                    color = Color.Transparent
                ),
            )
        }

        Column(
            modifier = Modifier.align(
                Alignment.BottomStart
            ).padding(horizontal = 4.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = short.title,
                modifier = Modifier,
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = short.channelName,
                modifier = Modifier,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShortVideoCardPreview() {
    YoutobeComposeTheme {
        ShortVideoCard(
            short = ShortModel(
                id = "short-1",
                thumbnailUrl = "https://picsum.photos/id/1015/320/180",
                title = "DIY Toys | Satisfying And Relaxing | DIY Tiktok Compilation",
                channelAvatarUrl = "https://i.pravatar.cc/200?img=11",
                channelName = "@ChannelName",
                likeCount = "2.3k",
                dislikeLabel = "Dislike",
                commentCount = "44",
                shareLabel = "Share",
                soundThumbnailUrl = "https://i.pravatar.cc/200?img=11",
                isSubscribed = false,
            ),
            onShortClick = {},
            onShortMoreClick = {},
        )
    }
}

@Composable
fun ShortsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_shorts),
            contentDescription = "",
            modifier = Modifier.size(40.dp),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = stringResource(R.string.section_shorts),
            modifier = Modifier,
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ShortsHeaderPreview() {
    YoutobeComposeTheme {
        ShortsHeader()
    }
}

@Composable
fun ShortsSection(
    shorts: List<ShortModel>,
    onShortClick: (ShortModel) -> Unit,
    onShortMoreClick: (ShortModel) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
    ) {
        ShortsHeader()

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(
                items = shorts,
                key = { it.id },
            ) { short ->
                ShortVideoCard(
                    short = short,
                    onShortClick = onShortClick,
                    onShortMoreClick = onShortMoreClick,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShortsSectionPreview() {
    YoutobeComposeTheme {
        ShortsSection(
            shorts = listOf(
                ShortModel(
                    id = "short-1",
                    thumbnailUrl = "https://picsum.photos/id/1025/240/426",
                    title = "Cute dog playing outside",
                    channelAvatarUrl = "https://i.pravatar.cc/200?img=11",
                    channelName = "@ChannelName",
                    likeCount = "2.3k",
                    dislikeLabel = "Dislike",
                    commentCount = "44",
                    shareLabel = "Share",
                    soundThumbnailUrl = "https://i.pravatar.cc/200?img=11",
                    isSubscribed = false,
                ),
                ShortModel(
                    id = "short-2",
                    thumbnailUrl = "https://picsum.photos/id/1011/240/426",
                    title = "Beautiful nature in the morning",
                    channelAvatarUrl = "https://i.pravatar.cc/200?img=22",
                    channelName = "@NatureDaily",
                    likeCount = "856K",
                    dislikeLabel = "Dislike",
                    commentCount = "128",
                    shareLabel = "Share",
                    soundThumbnailUrl = "https://i.pravatar.cc/200?img=22",
                    isSubscribed = false,
                ),
                ShortModel(
                    id = "short-3",
                    thumbnailUrl = "https://picsum.photos/id/1035/240/426",
                    title = "A peaceful day near the ocean",
                    channelAvatarUrl = "https://i.pravatar.cc/200?img=33",
                    channelName = "@OceanLife",
                    likeCount = "2.4M",
                    dislikeLabel = "Dislike",
                    commentCount = "302",
                    shareLabel = "Share",
                    soundThumbnailUrl = "https://i.pravatar.cc/200?img=33",
                    isSubscribed = false,
                ),
            ),
            onShortClick = {},
            onShortMoreClick = {}
        )
    }
}
