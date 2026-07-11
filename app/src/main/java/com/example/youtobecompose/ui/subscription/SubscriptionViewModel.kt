package com.example.youtobecompose.ui.subscription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtobecompose.model.Filter
import com.example.youtobecompose.model.VideoModel
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
class SubscriptionViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(SubscriptionState())
    val uiState: StateFlow<SubscriptionState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<SubscriptionEffect>()
    val effect: SharedFlow<SubscriptionEffect> = _effect.asSharedFlow()

    init {
        loadData()
    }

    fun onEvent(event: SubscriptionEvent) {
        when (event) {
            SubscriptionEvent.AllChannelsClick -> {
                submitAllChannelsClick()
            }

            is SubscriptionEvent.ChannelClick -> {
                submitChannelClick(channel = event.channel)
            }

            is SubscriptionEvent.FilterClick -> {
                doFilter(filterSelected = event.filter)
            }

            is SubscriptionEvent.VideoClick -> {
                submitVideoClick(video = event.video)
            }

            is SubscriptionEvent.VideoMoreClick -> {
                submitVideoMoreClick(video = event.video)
            }
        }
    }

    private fun loadData() {
        val channels = listOf(
            SubscriptionChannelModel(
                id = "channel-1",
                avatarUrl = "https://i.pravatar.cc/200?img=13",
                name = "Like Nastya",
                hasNewContent = true,
            ),
            SubscriptionChannelModel(
                id = "channel-2",
                avatarUrl = "https://i.pravatar.cc/200?img=14",
                name = "Bassera",
                hasNewContent = true,
            ),
            SubscriptionChannelModel(
                id = "channel-3",
                avatarUrl = "https://i.pravatar.cc/200?img=33",
                name = "Alor Path",
                hasNewContent = true,
            ),
            SubscriptionChannelModel(
                id = "channel-4",
                avatarUrl = "https://i.pravatar.cc/200?img=49",
                name = "Rain Drop..",
                hasNewContent = true,
            ),
        )

        val filters = listOf(
            Filter(id = 1, name = "All", isSelected = true),
            Filter(id = 2, name = "Today", isSelected = false),
            Filter(id = 3, name = "Yesterday", isSelected = false),
            Filter(id = 4, name = "This month", isSelected = false),
            Filter(id = 5, name = "continue", isSelected = false),
        )

        val videos = listOf(
            VideoModel(
                id = "subscription-video-1",
                thumbnailUrl = "https://picsum.photos/id/1076/720/405",
                avatarUrl = "https://i.pravatar.cc/200?img=13",
                title = "Heart Touching Nasheed #Shorts",
                metadata = "19,210,251 viewsJul • 1, 2016",
            ),
            VideoModel(
                id = "subscription-video-2",
                thumbnailUrl = "https://picsum.photos/id/1036/720/405",
                avatarUrl = "https://i.pravatar.cc/200?img=14",
                title = "Beautiful Architecture Short Clip",
                metadata = "428K views • 2 weeks ago",
            ),
            VideoModel(
                id = "subscription-video-3",
                thumbnailUrl = "https://picsum.photos/id/1048/720/405",
                avatarUrl = "https://i.pravatar.cc/200?img=33",
                title = "Quiet Morning Light Through The Glass",
                metadata = "92K views • 3 days ago",
            ),
        )

        _uiState.update {
            it.copy(
                channels = channels,
                filters = filters,
                videos = videos,
            )
        }
    }

    private fun doFilter(filterSelected: Filter) {
        _uiState.update {
            it.copy(
                filters = it.filters.map { filter ->
                    filter.copy(isSelected = filter.id == filterSelected.id)
                }
            )
        }

        viewModelScope.launch {
            _effect.emit(SubscriptionEffect.FilterClickSuccess(filterName = filterSelected.name))
        }
    }

    private fun submitChannelClick(channel: SubscriptionChannelModel) {
        viewModelScope.launch {
            _effect.emit(SubscriptionEffect.ChannelClickSuccess(channelName = channel.name))
        }
    }

    private fun submitAllChannelsClick() {
        viewModelScope.launch {
            _effect.emit(SubscriptionEffect.AllChannelsClickSuccess)
        }
    }

    private fun submitVideoClick(video: VideoModel) {
        viewModelScope.launch {
            _effect.emit(SubscriptionEffect.VideoClickSuccess(videoTitle = video.title))
        }
    }

    private fun submitVideoMoreClick(video: VideoModel) {
        viewModelScope.launch {
            _effect.emit(SubscriptionEffect.VideoMoreClickSuccess(videoTitle = video.title))
        }
    }
}
