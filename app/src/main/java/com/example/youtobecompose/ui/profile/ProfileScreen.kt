package com.example.youtobecompose.ui.profile

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil3.compose.AsyncImage
import com.example.youtobecompose.R
import com.example.youtobecompose.ui.theme.YoutobeComposeTheme

private val ProfileBackground = Color.White
private val ProfileTextPrimary = Color.Black
private val ProfileTextSecondary = Color(0xFF606060)
private val ProfileActionBackground = Color(0xFFF2F2F2)
private val ProfileDivider = Color(0xFFE0E0E0)

private fun ProfileTab.titleRes(): Int {
    return when (this) {
        ProfileTab.HOME -> R.string.channel_tab_home
        ProfileTab.VIDEOS -> R.string.channel_tab_videos
        ProfileTab.PLAYLISTS -> R.string.channel_tab_playlists
        ProfileTab.POSTS -> R.string.channel_tab_posts
    }
}

@Composable
fun ProfileRoute(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    ProfileScreen(
        modifier = modifier,
        state = state,
        onBackClick = onBackClick,
        onSearchClick = {
            viewModel.onEvent(ProfileEvent.SearchClick)
        },
        onMoreClick = {
            viewModel.onEvent(ProfileEvent.MoreClick)
        },
        onAnalyticsClick = {
            viewModel.onEvent(ProfileEvent.AnalyticsClick)
        },
        onEditChannelClick = {
            viewModel.onEvent(ProfileEvent.EditChannelClick)
        },
        onOpenVideosClick = {
            viewModel.onEvent(ProfileEvent.OpenVideosClick)
        },
        onVideoClick = { video ->
            viewModel.onEvent(ProfileEvent.VideoClick(video = video))
        },
        onVideoMoreClick = { video ->
            viewModel.onEvent(ProfileEvent.VideoMoreClick(video = video))
        },
        onTabSelected = { tab ->
            viewModel.onEvent(ProfileEvent.TabSelected(tab = tab))
        },
    )

    LaunchedEffect(viewModel.effect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    ProfileEffect.SearchClickSuccess -> {
                        Toast.makeText(context, "SearchClickSuccess", Toast.LENGTH_SHORT).show()
                    }

                    ProfileEffect.MoreClickSuccess -> {
                        Toast.makeText(context, "MoreClickSuccess", Toast.LENGTH_SHORT).show()
                    }

                    ProfileEffect.AnalyticsClickSuccess -> {
                        Toast.makeText(context, "AnalyticsClickSuccess", Toast.LENGTH_SHORT).show()
                    }

                    ProfileEffect.EditChannelClickSuccess -> {
                        Toast.makeText(context, "EditChannelClickSuccess", Toast.LENGTH_SHORT)
                            .show()
                    }

                    ProfileEffect.OpenVideosClickSuccess -> {
                        Toast.makeText(context, "OpenVideosClickSuccess", Toast.LENGTH_SHORT).show()
                    }

                    is ProfileEffect.VideoClickSuccess -> {
                        Toast.makeText(
                            context,
                            "VideoClickSuccess: ${context.getString(effect.videoTitleRes)}",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }

                    is ProfileEffect.VideoMoreClickSuccess -> {
                        Toast.makeText(
                            context,
                            "VideoMoreClickSuccess: ${context.getString(effect.videoTitleRes)}",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }

                    is ProfileEffect.TabSelectedSuccess -> {
                        Toast.makeText(
                            context,
                            "TabSelectedSuccess: ${context.getString(effect.tab.titleRes())}",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileState,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onAnalyticsClick: () -> Unit = {},
    onEditChannelClick: () -> Unit = {},
    onOpenVideosClick: () -> Unit = {},
    onVideoClick: (ProfileVideoUiModel) -> Unit = {},
    onVideoMoreClick: (ProfileVideoUiModel) -> Unit = {},
    onTabSelected: (ProfileTab) -> Unit = {},
) {
    Scaffold(
        modifier = modifier.fillMaxSize().background(Color.White),
        containerColor = ProfileBackground,
        topBar = {
            ProfileTopBar(
                onBackClick = onBackClick,
                onSearchClick = onSearchClick,
                onMoreClick = onMoreClick,
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(ProfileBackground)
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp),
        ) {
            item(
                key = "profile-header",
                contentType = "profile-header"
            ) {
                ProfileHeader(
                    bannerUrl = state.bannerUrl,
                    avatarUrl = state.avatarUrl,
                )
            }

            item(
                key = "channel_actions",
                contentType = "channel_actions",
            ) {
                ProfileActionRow(
                    onAnalyticsClick = onAnalyticsClick,
                    onEditChannelClick = onEditChannelClick,
                )
            }
            
            stickyHeader(
                key = "profile-tab",
                contentType = "profile-tab"
            ) {
                ProfileTabRow(
                    selectedTab = state.selectedTab,
                    onTabSelected = onTabSelected,
                )
            }

            item(
                key = "profile-tab-content-${state.selectedTab}",
                contentType = "profile-tab-content",
            ) {
                ProfileTabContent(
                    selectedTab = state.selectedTab,
                    homeVideos = state.homeVideos,
                    onOpenVideosClick = onOpenVideosClick,
                    onVideoClick = onVideoClick,
                    onVideoMoreClick = onVideoMoreClick,
                )
            }
        }
    }
}

@Composable
private fun ProfileTabContent(
    selectedTab: ProfileTab,
    homeVideos: List<ProfileVideoUiModel>,
    onOpenVideosClick: () -> Unit,
    onVideoClick: (ProfileVideoUiModel) -> Unit,
    onVideoMoreClick: (ProfileVideoUiModel) -> Unit,
) {
    when (selectedTab) {
        ProfileTab.HOME -> {
            ProfileHomeTabContent(
                videos = homeVideos,
                onOpenVideosClick = onOpenVideosClick,
                onVideoClick = onVideoClick,
                onVideoMoreClick = onVideoMoreClick,
            )
        }

        ProfileTab.VIDEOS,
        ProfileTab.PLAYLISTS,
        ProfileTab.POSTS -> {
            ProfileSimpleTabContent(tab = selectedTab)
        }
    }
}

@Composable
private fun ProfileHomeTabContent(
    videos: List<ProfileVideoUiModel>,
    onOpenVideosClick: () -> Unit,
    onVideoClick: (ProfileVideoUiModel) -> Unit,
    onVideoMoreClick: (ProfileVideoUiModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(ProfileBackground)
            .padding(top = 14.dp, bottom = 18.dp),
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onOpenVideosClick)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.channel_section_videos),
                color = ProfileTextPrimary,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = stringResource(R.string.channel_icon_open_videos),
                modifier = Modifier.size(24.dp),
                tint = ProfileTextPrimary,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        videos.forEach { video ->
            ProfileVideoListItem(
                video = video,
                onVideoClick = onVideoClick,
                onVideoMoreClick = onVideoMoreClick,
            )
        }
    }
}

@Composable
private fun ProfileSimpleTabContent(
    tab: ProfileTab,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(ProfileBackground),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = when (tab) {
                ProfileTab.HOME -> stringResource(R.string.channel_tab_home)
                ProfileTab.VIDEOS -> stringResource(R.string.channel_tab_videos)
                ProfileTab.PLAYLISTS -> stringResource(R.string.channel_tab_playlists)
                ProfileTab.POSTS -> stringResource(R.string.channel_tab_posts)
            },
            color = ProfileTextPrimary,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}

@Composable
private fun ProfileVideoListItem(
    video: ProfileVideoUiModel,
    onVideoClick: (ProfileVideoUiModel) -> Unit,
    onVideoMoreClick: (ProfileVideoUiModel) -> Unit,
) {
    val title = stringResource(video.titleRes)
    val metadata = stringResource(video.metadataRes)
    val duration = stringResource(video.durationRes)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onVideoClick(video)
            }
            .padding(horizontal = 24.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .width(158.dp)
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(8.dp)),
        ) {
            AsyncImage(
                model = video.thumbnailUrl,
                contentDescription = title,
                error = painterResource(R.drawable.ic_background_error),
                placeholder = painterResource(R.drawable.ic_background_error),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )

            Text(
                text = duration,
                color = Color.White,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 6.dp, bottom = 6.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.86f),
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(horizontal = 4.dp, vertical = 1.dp),
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = title,
                color = ProfileTextPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = metadata,
                color = ProfileTextSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        IconButton(
            modifier = Modifier.size(32.dp),
            onClick = {
                onVideoMoreClick(video)
            },
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_more_vertical),
                contentDescription = stringResource(R.string.channel_icon_video_more),
                modifier = Modifier.size(22.dp),
                tint = ProfileTextPrimary,
            )
        }
    }
}

@Composable
fun ProfileTabRow(
    selectedTab: ProfileTab,
    onTabSelected: (ProfileTab) -> Unit,
) {
    val tabs = listOf(
        ProfileTab.HOME to stringResource(R.string.channel_tab_home),
        ProfileTab.VIDEOS to stringResource(R.string.channel_tab_videos),
        ProfileTab.PLAYLISTS to stringResource(R.string.channel_tab_playlists),
        ProfileTab.POSTS to stringResource(R.string.channel_tab_posts),
    )
    val selectedTabIndex = tabs.indexOfFirst { (tab, _) ->
        tab == selectedTab
    }.coerceAtLeast(0)

    PrimaryScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier.fillMaxWidth(),
        containerColor = ProfileBackground,
        contentColor = ProfileTextPrimary,
        edgePadding = 4.dp,
        divider = {
            HorizontalDivider(
                thickness = 1.dp,
                color = ProfileDivider,
            )
        },
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(
                    selectedTabIndex = selectedTabIndex,
                    matchContentSize = true,
                ),
                width = Dp.Unspecified,
                color = ProfileTextPrimary,
            )
        },
    ) {
        tabs.forEachIndexed { index, (tab, title) ->
            val selected = index == selectedTabIndex

            Tab(
                selected = selected,
                onClick = {
                    onTabSelected(tab)
                },
                modifier = Modifier.height(56.dp),
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.DarkGray,
                text = {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = if (selected) {
                                FontWeight.Bold
                            } else {
                                FontWeight.SemiBold
                            },
                            fontSize = 18.sp
                        ),
                    )
                },
            )
        }
    }
}

@Composable
fun ProfileActionRow(
    onAnalyticsClick: () -> Unit,
    onEditChannelClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileActionButton(
            iconRes = R.drawable.ic_channel_analytics,
            text = stringResource(R.string.channel_analytics),
            onClick = onAnalyticsClick,
            modifier = Modifier.weight(1f),
        )

        ProfileActionButton(
            iconRes = R.drawable.ic_channel_edit,
            text = stringResource(R.string.channel_edit),
            onClick = onEditChannelClick,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun ProfileActionButton(
    iconRes: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(ProfileActionBackground)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = text,
            modifier = Modifier.size(24.dp),
            tint = ProfileTextPrimary,
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = text,
            color = ProfileTextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}

@Composable
fun ProfileHeader(
    bannerUrl: String,
    avatarUrl: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileBanner(bannerUrl = bannerUrl)

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProfileAvatar(avatarUrl = avatarUrl)

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = stringResource(R.string.channel_name),
                    color = ProfileTextPrimary,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = stringResource(R.string.channel_handle),
                    color = ProfileTextPrimary,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = stringResource(R.string.channel_subscribers_videos),
                    color = ProfileTextSecondary,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
private fun ProfileBanner(
    bannerUrl: String,
) {
    if (bannerUrl.isBlank()) {
        Image(
            painter = painterResource(R.drawable.img_profile_banner),
            contentDescription = stringResource(R.string.profile_banner),
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop,
        )
    } else {
        AsyncImage(
            model = bannerUrl,
            error = painterResource(R.drawable.img_profile_banner),
            placeholder = painterResource(R.drawable.img_profile_banner),
            contentDescription = stringResource(R.string.profile_banner),
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun ProfileAvatar(
    avatarUrl: String,
) {
    if (avatarUrl.isBlank()) {
        Image(
            painter = painterResource(R.drawable.ic_avatar),
            contentDescription = stringResource(R.string.profile_avatar),
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
    } else {
        AsyncImage(
            model = avatarUrl,
            error = painterResource(R.drawable.ic_avatar),
            placeholder = painterResource(R.drawable.ic_avatar),
            contentDescription = stringResource(R.string.profile_avatar),
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun ProfileTopBar(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMoreClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(ProfileBackground)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = {
                onBackClick()
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_left),
                contentDescription = stringResource(R.string.channel_icon_back),
                modifier = Modifier.size(24.dp),
                tint = ProfileTextPrimary,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = onSearchClick,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(R.string.channel_icon_search),
                modifier = Modifier.size(24.dp),
                tint = ProfileTextPrimary,
            )
        }

        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = onMoreClick,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more_vertical),
                contentDescription = stringResource(R.string.channel_icon_more),
                modifier = Modifier.size(24.dp),
                tint = ProfileTextPrimary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    YoutobeComposeTheme {
        ProfileScreen(
            state = previewProfileState(),
            onBackClick = {},
        )
    }
}

private fun previewProfileState(): ProfileState {
    return ProfileState(
        bannerUrl = "https://picsum.photos/id/1043/480/270",
        avatarUrl = "https://i.pravatar.cc/200?img=32",
        selectedTab = ProfileTab.HOME,
        homeVideos = previewProfileVideos(),
    )
}

private fun previewProfileVideos(): List<ProfileVideoUiModel> {
    return listOf(
        ProfileVideoUiModel(
            id = "demo_git",
            thumbnailUrl = "https://picsum.photos/id/1015/480/270",
            titleRes = R.string.channel_video_demo_git,
            metadataRes = R.string.channel_video_demo_git_metadata,
            durationRes = R.string.channel_video_demo_git_duration,
        ),
        ProfileVideoUiModel(
            id = "android_splash",
            thumbnailUrl = "https://picsum.photos/id/1043/480/270",
            titleRes = R.string.channel_video_android_splash,
            metadataRes = R.string.channel_video_android_splash_metadata,
            durationRes = R.string.channel_video_android_splash_duration,
        ),
        ProfileVideoUiModel(
            id = "library",
            thumbnailUrl = "https://picsum.photos/seed/video3/480/270",
            titleRes = R.string.channel_video_library,
            metadataRes = R.string.channel_video_library_metadata,
            durationRes = R.string.channel_video_library_duration,
        ),
        ProfileVideoUiModel(
            id = "compose_profile",
            thumbnailUrl = "https://picsum.photos/id/1067/480/270",
            titleRes = R.string.channel_video_compose_profile,
            metadataRes = R.string.channel_video_compose_profile_metadata,
            durationRes = R.string.channel_video_compose_profile_duration,
        ),
        ProfileVideoUiModel(
            id = "android_navigation",
            thumbnailUrl = "https://picsum.photos/id/1036/480/270",
            titleRes = R.string.channel_video_android_navigation,
            metadataRes = R.string.channel_video_android_navigation_metadata,
            durationRes = R.string.channel_video_android_navigation_duration,
        ),
    )
}
