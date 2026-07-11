package com.example.youtobecompose.ui.library

import com.example.youtobecompose.model.RecentVideoModel

data class LibraryState(
    val recentVideos: List<RecentVideoModel> = listOf()
)

sealed class LibraryEvent {
    data class RecentVideoDetailClick(
        val recentVideo: RecentVideoModel,
    ) : LibraryEvent()

    data object HistoryItemClick : LibraryEvent()

    data object YourVideoItemClick : LibraryEvent()

    data object DownloadItemClick : LibraryEvent()

    data object YourMovieItemClick : LibraryEvent()

    data object WatchLaterItemClick : LibraryEvent()

    data object CreatePlaylistClick : LibraryEvent()
}

sealed class LibraryEffect {
    data class RecentVideoDetailSuccess(
        val videoTitle: String,
    ) : LibraryEffect()

    data object HistoryItemSuccess : LibraryEffect()

    data object YourVideoItemSuccess : LibraryEffect()

    data object DownloadItemSuccess : LibraryEffect()

    data object YourMovieItemSuccess : LibraryEffect()

    data object WatchLaterItemSuccess : LibraryEffect()

    data object CreatePlaylistSuccess : LibraryEffect()
}
