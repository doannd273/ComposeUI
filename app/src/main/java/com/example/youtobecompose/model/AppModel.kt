package com.example.youtobecompose.model

enum class NotificationTab {
    All,
    Mentions,
}

data class NotificationGroupModel(
    val groupId: String,
    val channelName: String,
    val avatarUrl: String,
    val isExpand: Boolean,
    val notifications: List<NotificationModel>
)

data class NotificationModel(
    val id: String,
    val title: String,
    val timeText: String,
    val thumbnailUrl: String,
)

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
