package com.example.youtobecompose.ui.library

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.youtobecompose.model.RecentVideoModel

@Composable
fun LibraryRoute(
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LibraryScreen(
        modifier = modifier,
        uiState = uiState,
        onRecentVideoDetailClick = {
            viewModel.onEvent(LibraryEvent.RecentVideoDetailClick(recentVideo = it))
        },
        onHistoryItemClick = {
            viewModel.onEvent(LibraryEvent.HistoryItemClick)
        },
        onYourVideoItemClick = {
            viewModel.onEvent(LibraryEvent.YourVideoItemClick)
        },
        onDownloadItemClick = {
            viewModel.onEvent(LibraryEvent.DownloadItemClick)
        },
        onYourMovieItemClick = {
            viewModel.onEvent(LibraryEvent.YourMovieItemClick)
        },
        onWatchLaterItemClick = {
            viewModel.onEvent(LibraryEvent.WatchLaterItemClick)
        },
        onCreatePlaylistClick = {
            viewModel.onEvent(LibraryEvent.CreatePlaylistClick)
        }
    )

    LaunchedEffect(viewModel.effect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is LibraryEffect.RecentVideoDetailSuccess -> {
                        Toast.makeText(
                            context,
                            "RecentVideoDetailSuccess: ${effect.videoTitle}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    LibraryEffect.HistoryItemSuccess -> {
                        Toast.makeText(context, "HistoryItemSuccess", Toast.LENGTH_SHORT).show()
                    }

                    LibraryEffect.YourVideoItemSuccess -> {
                        Toast.makeText(context, "YourVideoItemSuccess", Toast.LENGTH_SHORT).show()
                    }

                    LibraryEffect.DownloadItemSuccess -> {
                        Toast.makeText(context, "DownloadItemSuccess", Toast.LENGTH_SHORT).show()
                    }

                    LibraryEffect.YourMovieItemSuccess -> {
                        Toast.makeText(context, "YourMovieItemSuccess", Toast.LENGTH_SHORT).show()
                    }

                    LibraryEffect.WatchLaterItemSuccess -> {
                        Toast.makeText(context, "WatchLaterItemSuccess", Toast.LENGTH_SHORT).show()
                    }

                    LibraryEffect.CreatePlaylistSuccess -> {
                        Toast.makeText(context, "CreatePlaylistSuccess", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    uiState: LibraryState,
    onRecentVideoDetailClick: (RecentVideoModel) -> Unit,
    onHistoryItemClick: () -> Unit,
    onYourVideoItemClick: () -> Unit,
    onDownloadItemClick: () -> Unit,
    onYourMovieItemClick: () -> Unit,
    onWatchLaterItemClick: () -> Unit,
    onCreatePlaylistClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item(
            key = "recent_section",
            contentType = "recent_section"
        ) {
            RecentSection(
                recentVideos = uiState.recentVideos,
                onRecentVideoDetailClick = onRecentVideoDetailClick
            )
        }

        item(
            key = "menu_divider",
            contentType = "divider"
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.padding(vertical = 20.dp),
            )
        }

        item(
            key = "menu_history",
            contentType = "menu_item"
        ) {
            MenuLibraryItem(
                menuIcon = R.drawable.ic_library_history,
                menuLabel = R.string.library_history,
                onHistoryItemClick = onHistoryItemClick
            )
        }

        item(
            key = "menu_your_videos",
            contentType = "menu_item"
        ) {
            MenuLibraryItem(
                menuIcon = R.drawable.ic_library_your_videos,
                menuLabel = R.string.library_your_videos,
                onHistoryItemClick = onYourVideoItemClick
            )
        }

        item(
            key = "menu_downloads",
            contentType = "menu_item"
        ) {
            MenuLibraryItem(
                menuIcon = R.drawable.ic_library_download,
                menuLabel = R.string.library_downloads,
                menuSubLabel = R.string.library_downloads_count,
                trailingIcon = R.drawable.ic_library_download_done,
                onHistoryItemClick = onDownloadItemClick
            )
        }

        item(
            key = "menu_yours_movie",
            contentType = "menu_item"
        ) {
            MenuLibraryItem(
                menuIcon = R.drawable.ic_library_movies,
                menuLabel = R.string.library_your_movies,
                onHistoryItemClick = onYourMovieItemClick
            )
        }

        item(
            key = "menu_watch_later",
            contentType = "menu_item"
        ) {
            MenuLibraryItem(
                menuIcon = R.drawable.ic_library_watch_later,
                menuLabel = R.string.library_watch_later,
                menuSubLabel = R.string.library_watch_later_count,
                onHistoryItemClick = onWatchLaterItemClick
            )
        }

        item(
            key = "playlist_divider",
            contentType = "divider"
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.padding(vertical = 10.dp),
            )
        }

        item(
            key = "new_playlist",
            contentType = "new_playlist",
        ) {
            NewPlaylistItem(
                onCreatePlaylistClick = onCreatePlaylistClick,
            )
        }
    }
}

@Composable
fun NewPlaylistItem(
    onCreatePlaylistClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        PlaylistHeader()

        Spacer(modifier = Modifier.height(20.dp))

        AddNewPlaylist(
            onCreatePlaylistClick = onCreatePlaylistClick,
        )

        Spacer(modifier = Modifier.height(20.dp))

        LikeVideoItem()
    }
}

@Composable
fun LikeVideoItem() {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_background_short),
            contentDescription = "",
            modifier = Modifier.size(40.dp),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(R.string.library_liked_videos),
                modifier = Modifier,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = stringResource(R.string.library_liked_videos_count),
                modifier = Modifier,
                style = MaterialTheme.typography.labelLarge,
                color = Color.DarkGray,
            )
        }
    }
}

@Composable
fun AddNewPlaylist(
    onCreatePlaylistClick: () -> Unit,
) {
    Row(
        modifier = Modifier.clickable {
            onCreatePlaylistClick()
        },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_library_playlist_add),
            contentDescription = "",
            modifier = Modifier.size(24.dp),
            tint = Color.Blue
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = stringResource(R.string.library_new_playlist),
            modifier = Modifier,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Blue,
        )
    }
}

@Composable
fun PlaylistHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.library_playlists),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.library_recent),
                modifier = Modifier,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_library_arrow_drop_down),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Composable
fun MenuLibraryItem(
    menuIcon: Int,
    menuLabel: Int,
    menuSubLabel: Int = 0,
    trailingIcon: Int = 0,
    onHistoryItemClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onHistoryItemClick()
            }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = menuIcon),
            contentDescription = "",
            modifier = Modifier.size(30.dp),
        )

        Spacer(modifier = Modifier.width(20.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(menuLabel),
                modifier = Modifier,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
            )

            if (menuSubLabel != 0) {
                Text(
                    text = stringResource(menuSubLabel),
                    modifier = Modifier,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.DarkGray,
                )
            }
        }

        if (trailingIcon != 0) {
            Icon(
                painter = painterResource(id = trailingIcon),
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                tint = Color.Unspecified,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LibraryScreenPreview() {
    val videos = listOf(
        RecentVideoModel(
            id = "video_1",
            title = "Heart Touching Nasheed #Shorts",
            channelName = "An Naffe",
            thumbnailUrl = "https://picsum.photos/seed/video1/480/270",
            duration = "0:50",
            progress = 0.95f,
        ),
        RecentVideoModel(
            id = "video_2",
            title = "Beautiful Nature Relaxing Video",
            channelName = "Nature Channel",
            thumbnailUrl = "https://picsum.photos/seed/video2/480/270",
            duration = "1:20",
            progress = 0.65f,
        ),
        RecentVideoModel(
            id = "video_3",
            title = "Exploring the Dark Forest",
            channelName = "Adventure",
            thumbnailUrl = "https://picsum.photos/seed/video3/480/270",
            duration = "2:15",
            progress = 0.3f,
        ),
    )

    LibraryScreen(
        uiState = LibraryState(recentVideos = videos),
        onRecentVideoDetailClick = {},
        onHistoryItemClick = {},
        onYourVideoItemClick = {},
        onDownloadItemClick = {},
        onYourMovieItemClick = {},
        onWatchLaterItemClick = {},
        onCreatePlaylistClick = {},
    )
}

@Composable
fun RecentSection(
    recentVideos: List<RecentVideoModel>,
    onRecentVideoDetailClick: (RecentVideoModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(R.string.library_recent),
            modifier = Modifier.padding(vertical = 6.dp),
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
        )

        Spacer(
            modifier = Modifier.height(4.dp),
        )

        LazyRow(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = recentVideos,
                key = { it.id },
                contentType = { "recent-video" },
            ) { item ->
                RecentVideoItem(
                    recentVideo = item,
                    onRecentVideoDetailClick = {
                        onRecentVideoDetailClick(item)
                    }, onRecentMoreClick = {})
            }
        }
    }
}

@Composable
fun RecentVideoItem(
    recentVideo: RecentVideoModel,
    onRecentVideoDetailClick: () -> Unit,
    onRecentMoreClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .wrapContentHeight()
            .background(color = Color.White)
            .clickable {
                onRecentVideoDetailClick()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                model = recentVideo.thumbnailUrl,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_background_short),
                placeholder = painterResource(R.drawable.ic_background_short)
            )

            Text(
                text = recentVideo.duration,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(
                        color = Color.Black, shape = RoundedCornerShape(0.dp)
                    )
                    .padding(all = 4.dp),
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
            )

            LinearProgressIndicator(
                progress = { recentVideo.progress },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(3.dp),
                color = Color.Red
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = recentVideo.title,
                    modifier = Modifier,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = recentVideo.channelName,
                    modifier = Modifier,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.DarkGray,
                    maxLines = 1
                )
            }

            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = {
                    onRecentMoreClick()
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
private fun RecentSectionPreview() {
    val videos = listOf(
        RecentVideoModel(
            id = "video_1",
            title = "Heart Touching Nasheed #Shorts",
            channelName = "An Naffe",
            thumbnailUrl = "https://picsum.photos/seed/video1/480/270",
            duration = "0:50",
            progress = 0.95f,
        ),
        RecentVideoModel(
            id = "video_2",
            title = "Beautiful Nature Relaxing Video",
            channelName = "Nature Channel",
            thumbnailUrl = "https://picsum.photos/seed/video2/480/270",
            duration = "1:20",
            progress = 0.65f,
        ),
        RecentVideoModel(
            id = "video_3",
            title = "Exploring the Dark Forest",
            channelName = "Adventure",
            thumbnailUrl = "https://picsum.photos/seed/video3/480/270",
            duration = "2:15",
            progress = 0.3f,
        ),
    )

    RecentSection(
        recentVideos = videos,
        onRecentVideoDetailClick = {}
    )
}
