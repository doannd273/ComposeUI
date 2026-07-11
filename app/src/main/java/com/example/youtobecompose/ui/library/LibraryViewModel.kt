package com.example.youtobecompose.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtobecompose.model.RecentVideoModel
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
class LibraryViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LibraryState())
    val uiState: StateFlow<LibraryState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<LibraryEffect>()
    val effect: SharedFlow<LibraryEffect> = _effect.asSharedFlow()

    init {
        loadData()
    }

    fun onEvent(event: LibraryEvent) {
        when (event) {
            is LibraryEvent.RecentVideoDetailClick -> {
                submitRecentVideoDetail(recentVideo = event.recentVideo)
            }

            LibraryEvent.HistoryItemClick -> {
                submitHistoryItemClick()
            }

            LibraryEvent.YourVideoItemClick -> {
                submitYourVideoItemClick()
            }

            LibraryEvent.DownloadItemClick -> {
                submitDownloadItemClick()
            }

            LibraryEvent.YourMovieItemClick -> {
                submitYourMovieItemClick()
            }

            LibraryEvent.WatchLaterItemClick -> {
                submitWatchLaterItemClick()
            }

            LibraryEvent.CreatePlaylistClick -> {
                submitCreatePlaylistClick()
            }
        }
    }

    private fun loadData() {
        val recentVideos = listOf(
            RecentVideoModel(
                id = "video_1",
                title = "Heart Touching Nasheed #Shorts",
                channelName = "An Naffe",
                thumbnailUrl = "https://picsum.photos/seed/video1/480/270",
                duration = "0:50",
                progress = 0.95f,
            ),
            RecentVideoModel(
                id = "video_2",
                title = "Beautiful Nature Relaxing Video",
                channelName = "Nature Channel",
                thumbnailUrl = "https://picsum.photos/seed/video2/480/270",
                duration = "1:20",
                progress = 0.65f,
            ),
            RecentVideoModel(
                id = "video_3",
                title = "Exploring the Dark Forest",
                channelName = "Adventure",
                thumbnailUrl = "https://picsum.photos/seed/video3/480/270",
                duration = "2:15",
                progress = 0.3f,
            ),
        )

        _uiState.update { it.copy(recentVideos = recentVideos) }
    }

    private fun submitRecentVideoDetail(recentVideo: RecentVideoModel) {
        viewModelScope.launch {
            _effect.emit(LibraryEffect.RecentVideoDetailSuccess(videoTitle = recentVideo.title))
        }
    }

    private fun submitHistoryItemClick() {
        viewModelScope.launch {
            _effect.emit(LibraryEffect.HistoryItemSuccess)
        }
    }

    private fun submitYourVideoItemClick() {
        viewModelScope.launch {
            _effect.emit(LibraryEffect.YourVideoItemSuccess)
        }
    }

    private fun submitDownloadItemClick() {
        viewModelScope.launch {
            _effect.emit(LibraryEffect.DownloadItemSuccess)
        }
    }

    private fun submitYourMovieItemClick() {
        viewModelScope.launch {
            _effect.emit(LibraryEffect.YourMovieItemSuccess)
        }
    }

    private fun submitWatchLaterItemClick() {
        viewModelScope.launch {
            _effect.emit(LibraryEffect.WatchLaterItemSuccess)
        }
    }

    private fun submitCreatePlaylistClick() {
        viewModelScope.launch {
            _effect.emit(LibraryEffect.CreatePlaylistSuccess)
        }
    }
}
