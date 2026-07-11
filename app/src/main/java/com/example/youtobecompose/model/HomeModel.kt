package com.example.youtobecompose.model

data class Filter(
    val id: Int,
    val name: String,
    val isSelected: Boolean
)

data class VideoModel(
    val id: String,
    val thumbnailUrl: String,
    val avatarUrl: String,
    val title: String,
    val metadata: String,
)

data class RecentVideoModel(
    val id: String,
    val thumbnailUrl: String,
    val title: String,
    val channelName: String,
    val duration: String,
    val progress: Float,
)

data class ShortModel(
    val id: String,
    val thumbnailUrl: String,
    val title: String,
    val channelAvatarUrl: String,
    val channelName: String,
    val likeCount: String,
    val dislikeLabel: String,
    val commentCount: String,
    val shareLabel: String,
    val soundThumbnailUrl: String,
    val isSubscribed: Boolean,
)

sealed interface HomeFeed {
    val key: String

    data class Video(
        val video: VideoModel
    ) : HomeFeed {
        override val key: String
            get() = "video - ${video.id}"
    }

    data class Shorts(
        val sectionId: String,
        val shorts: List<ShortModel>
    ) : HomeFeed {
        override val key: String
            get() = "short-$sectionId"
    }
}
