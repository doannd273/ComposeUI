package com.example.youtobecompose.ui.shorts

import com.example.youtobecompose.model.ShortModel

data class ShortsState(
    val shorts: List<ShortModel> = listOf(),
)

sealed class ShortsEvent {
    data object ScreenStarted : ShortsEvent()

    data class BackClick(val short: ShortModel) : ShortsEvent()

    data class LikeClick(val short: ShortModel) : ShortsEvent()

    data class DislikeClick(val short: ShortModel) : ShortsEvent()

    data class CommentClick(val short: ShortModel) : ShortsEvent()

    data class ShareClick(val short: ShortModel) : ShortsEvent()

    data class MoreClick(val short: ShortModel) : ShortsEvent()

    data class SoundClick(val short: ShortModel) : ShortsEvent()

    data class SubscribeClick(val short: ShortModel) : ShortsEvent()
}

sealed class ShortsEffect {
    data class BackClickSuccess(val shortTitle: String) : ShortsEffect()

    data class LikeClickSuccess(val shortTitle: String) : ShortsEffect()

    data class DislikeClickSuccess(val shortTitle: String) : ShortsEffect()

    data class CommentClickSuccess(val shortTitle: String) : ShortsEffect()

    data class ShareClickSuccess(val shortTitle: String) : ShortsEffect()

    data class MoreClickSuccess(val shortTitle: String) : ShortsEffect()

    data class SoundClickSuccess(val shortTitle: String) : ShortsEffect()

    data class SubscribeClickSuccess(val shortTitle: String) : ShortsEffect()
}
