package com.example.youtobecompose.ui.shorts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtobecompose.model.ShortModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShortsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ShortsState())
    val uiState: StateFlow<ShortsState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<ShortsEffect>()
    val effect: SharedFlow<ShortsEffect> = _effect.asSharedFlow()

    init {
        onEvent(ShortsEvent.ScreenStarted)
    }

    fun onEvent(event: ShortsEvent) {
        when (event) {
            ShortsEvent.ScreenStarted -> {
                loadScreen()
            }

            is ShortsEvent.BackClick -> {
                submitBackClick(short = event.short)
            }

            is ShortsEvent.LikeClick -> {
                submitLikeClick(short = event.short)
            }

            is ShortsEvent.DislikeClick -> {
                submitDislikeClick(short = event.short)
            }

            is ShortsEvent.CommentClick -> {
                submitCommentClick(short = event.short)
            }

            is ShortsEvent.ShareClick -> {
                submitShareClick(short = event.short)
            }

            is ShortsEvent.MoreClick -> {
                submitMoreClick(short = event.short)
            }

            is ShortsEvent.SoundClick -> {
                submitSoundClick(short = event.short)
            }

            is ShortsEvent.SubscribeClick -> {
                submitSubscribeClick(short = event.short)
            }
        }
    }

    private fun loadScreen() {
        val shorts = listOf(
            ShortModel(
                id = "short-poly-amazing-race",
                thumbnailUrl = "https://picsum.photos/id/1002/720/1280",
                title = "\"Poly Amazing Race\" san choi day thu vi moi danh cho sinh...",
                channelAvatarUrl = "https://i.pravatar.cc/200?img=11",
                channelName = "@ChannelName",
                likeCount = "2.3k",
                dislikeLabel = "Dislike",
                commentCount = "44",
                shareLabel = "Share",
                soundThumbnailUrl = "https://picsum.photos/id/1035/200/200",
                isSubscribed = false,
            ),
            ShortModel(
                id = "short-desert-walk",
                thumbnailUrl = "https://picsum.photos/id/1003/720/1280",
                title = "Walking through the desert with a sky full of quiet",
                channelAvatarUrl = "https://i.pravatar.cc/200?img=12",
                channelName = "@TravelMood",
                likeCount = "8.6k",
                dislikeLabel = "Dislike",
                commentCount = "128",
                shareLabel = "Share",
                soundThumbnailUrl = "https://picsum.photos/id/1040/200/200",
                isSubscribed = false,
            ),
            ShortModel(
                id = "short-city-night",
                thumbnailUrl = "https://picsum.photos/id/1011/720/1280",
                title = "City lights, fast edits, and one perfect night ride",
                channelAvatarUrl = "https://i.pravatar.cc/200?img=23",
                channelName = "@CityCuts",
                likeCount = "42k",
                dislikeLabel = "Dislike",
                commentCount = "390",
                shareLabel = "Share",
                soundThumbnailUrl = "https://picsum.photos/id/1050/200/200",
                isSubscribed = false,
            ),
            ShortModel(
                id = "short-food-market",
                thumbnailUrl = "https://picsum.photos/id/1060/720/1280",
                title = "Street food market moments that look too good",
                channelAvatarUrl = "https://i.pravatar.cc/200?img=34",
                channelName = "@StreetBites",
                likeCount = "19k",
                dislikeLabel = "Dislike",
                commentCount = "207",
                shareLabel = "Share",
                soundThumbnailUrl = "https://picsum.photos/id/1067/200/200",
                isSubscribed = true,
            ),
            ShortModel(
                id = "short-mountain-air",
                thumbnailUrl = "https://picsum.photos/id/1018/720/1280",
                title = "Morning mountain air and a road with no deadline",
                channelAvatarUrl = "https://i.pravatar.cc/200?img=45",
                channelName = "@OutdoorDaily",
                likeCount = "3.1k",
                dislikeLabel = "Dislike",
                commentCount = "61",
                shareLabel = "Share",
                soundThumbnailUrl = "https://picsum.photos/id/1076/200/200",
                isSubscribed = false,
            ),
            ShortModel(
                id = "short-desk-setup",
                thumbnailUrl = "https://picsum.photos/id/1074/720/1280",
                title = "Tiny desk setup upgrade that changes everything",
                channelAvatarUrl = "https://i.pravatar.cc/200?img=56",
                channelName = "@SetupLab",
                likeCount = "927",
                dislikeLabel = "Dislike",
                commentCount = "35",
                shareLabel = "Share",
                soundThumbnailUrl = "https://picsum.photos/id/1084/200/200",
                isSubscribed = false,
            )
        )

        _uiState.update { it.copy(shorts = shorts) }
    }

    private fun submitBackClick(short: ShortModel) {
        viewModelScope.launch {
            _effect.emit(ShortsEffect.BackClickSuccess(shortTitle = short.title))
        }
    }

    private fun submitLikeClick(short: ShortModel) {
        viewModelScope.launch {
            _effect.emit(ShortsEffect.LikeClickSuccess(shortTitle = short.title))
        }
    }

    private fun submitDislikeClick(short: ShortModel) {
        viewModelScope.launch {
            _effect.emit(ShortsEffect.DislikeClickSuccess(shortTitle = short.title))
        }
    }

    private fun submitCommentClick(short: ShortModel) {
        viewModelScope.launch {
            _effect.emit(ShortsEffect.CommentClickSuccess(shortTitle = short.title))
        }
    }

    private fun submitShareClick(short: ShortModel) {
        viewModelScope.launch {
            _effect.emit(ShortsEffect.ShareClickSuccess(shortTitle = short.title))
        }
    }

    private fun submitMoreClick(short: ShortModel) {
        viewModelScope.launch {
            _effect.emit(ShortsEffect.MoreClickSuccess(shortTitle = short.title))
        }
    }

    private fun submitSoundClick(short: ShortModel) {
        viewModelScope.launch {
            _effect.emit(ShortsEffect.SoundClickSuccess(shortTitle = short.title))
        }
    }

    private fun submitSubscribeClick(short: ShortModel) {
        viewModelScope.launch {
            _effect.emit(ShortsEffect.SubscribeClickSuccess(shortTitle = short.title))
        }
    }
}
