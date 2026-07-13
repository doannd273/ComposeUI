package com.example.youtobecompose.ui.profile

import androidx.annotation.StringRes

data class ProfileState(
    val bannerUrl: String = "",
    val avatarUrl: String = "",
    val selectedTab: ProfileTab = ProfileTab.HOME,
    val homeVideos: List<ProfileVideoUiModel> = emptyList(),
)

data class ProfileVideoUiModel(
    val id: String,
    val thumbnailUrl: String,
    @StringRes val titleRes: Int,
    @StringRes val metadataRes: Int,
    @StringRes val durationRes: Int,
)

enum class ProfileTab {
    HOME,
    VIDEOS,
    PLAYLISTS,
    POSTS,
}

sealed interface ProfileEvent {
    data object SearchClick : ProfileEvent

    data object MoreClick : ProfileEvent

    data object AnalyticsClick : ProfileEvent

    data object EditChannelClick : ProfileEvent

    data object OpenVideosClick : ProfileEvent

    data class VideoClick(val video: ProfileVideoUiModel) : ProfileEvent

    data class VideoMoreClick(val video: ProfileVideoUiModel) : ProfileEvent

    data class TabSelected(val tab: ProfileTab) : ProfileEvent
}

sealed interface ProfileEffect {
    data object SearchClickSuccess : ProfileEffect

    data object MoreClickSuccess : ProfileEffect

    data object AnalyticsClickSuccess : ProfileEffect

    data object EditChannelClickSuccess : ProfileEffect

    data object OpenVideosClickSuccess : ProfileEffect

    data class VideoClickSuccess(@StringRes val videoTitleRes: Int) : ProfileEffect

    data class VideoMoreClickSuccess(@StringRes val videoTitleRes: Int) : ProfileEffect

    data class TabSelectedSuccess(val tab: ProfileTab) : ProfileEffect
}
