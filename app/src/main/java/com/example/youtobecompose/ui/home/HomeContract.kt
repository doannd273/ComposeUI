package com.example.youtobecompose.ui.home

import com.example.youtobecompose.model.Filter
import com.example.youtobecompose.model.HomeFeed
import com.example.youtobecompose.model.ShortModel
import com.example.youtobecompose.model.VideoModel

data class HomeState(
    val filters: List<Filter> = listOf(),
    val feedItems: List<HomeFeed> = listOf()
)

sealed class HomeEvent {
    data object ExploreClick : HomeEvent()

    data class FilterClick(val filter: Filter) : HomeEvent()

    data class VideoClick(val video: VideoModel) : HomeEvent()

    data class VideoMoreClick(val video: VideoModel) : HomeEvent()

    data class ShortClick(val short: ShortModel) : HomeEvent()

    data class ShortMoreClick(val short: ShortModel) : HomeEvent()
}

sealed class HomeEffect {
    data object ExploreSuccess : HomeEffect()

    data class FilterSuccess(val filterName: String) : HomeEffect()

    data class VideoClickSuccess(val videoTitle: String) : HomeEffect()

    data class VideoMoreClickSuccess(val videoTitle: String) : HomeEffect()

    data class ShortClickSuccess(val shortTitle: String) : HomeEffect()

    data class ShortMoreClickSuccess(val shortTitle: String) : HomeEffect()
}
