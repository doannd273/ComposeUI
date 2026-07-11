package com.example.youtobecompose.ui.subscription

import com.example.youtobecompose.model.Filter
import com.example.youtobecompose.model.VideoModel

data class SubscriptionState(
    val channels: List<SubscriptionChannelModel> = listOf(),
    val filters: List<Filter> = listOf(),
    val videos: List<VideoModel> = listOf(),
)

data class SubscriptionChannelModel(
    val id: String,
    val avatarUrl: String,
    val name: String,
    val hasNewContent: Boolean,
)

sealed class SubscriptionEvent {
    data class ChannelClick(val channel: SubscriptionChannelModel) : SubscriptionEvent()

    data object AllChannelsClick : SubscriptionEvent()

    data class FilterClick(val filter: Filter) : SubscriptionEvent()

    data class VideoClick(val video: VideoModel) : SubscriptionEvent()

    data class VideoMoreClick(val video: VideoModel) : SubscriptionEvent()
}

sealed class SubscriptionEffect {
    data class ChannelClickSuccess(val channelName: String) : SubscriptionEffect()

    data object AllChannelsClickSuccess : SubscriptionEffect()

    data class FilterClickSuccess(val filterName: String) : SubscriptionEffect()

    data class VideoClickSuccess(val videoTitle: String) : SubscriptionEffect()

    data class VideoMoreClickSuccess(val videoTitle: String) : SubscriptionEffect()
}
