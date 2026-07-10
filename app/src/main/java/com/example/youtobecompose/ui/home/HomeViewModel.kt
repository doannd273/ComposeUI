package com.example.youtobecompose.ui.home

import android.R.attr.name
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.youtobecompose.ui.home.model.Filter
import com.example.youtobecompose.ui.home.model.HomeFeed
import com.example.youtobecompose.ui.home.model.ShortModel
import com.example.youtobecompose.ui.home.model.VideoModel
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
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect: SharedFlow<HomeEffect> = _effect.asSharedFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.ExploreClick -> {
                submitExplore()
            }

            is HomeEvent.FilterClick -> {
                doFilter(filterSelected = event.filter)
            }

            is HomeEvent.VideoClick -> {
                submitVideoClick(video = event.video)
            }

            is HomeEvent.VideoMoreClick -> {
                submitVideoMoreClick(video = event.video)
            }

            is HomeEvent.ShortClick -> {
                submitShortClick(short = event.short)
            }

            is HomeEvent.ShortMoreClick -> {
                submitShortMoreClick(short = event.short)
            }
        }
    }

    init {
        loadFilters()
        loadFeed()
    }

    private fun loadFeed() {
        val feedItems: List<HomeFeed> = listOf(
            HomeFeed.Video(
                video = VideoModel(
                    id = "video-1",
                    thumbnailUrl = "https://picsum.photos/id/1015/480/270",
                    avatarUrl = "https://i.pravatar.cc/200?img=12",
                    title = "The Beauty of Existence - Heart Touching Nasheed",
                    metadata = "19M views • Jul 1, 2016",
                )
            ),
            HomeFeed.Shorts(
                sectionId = "shorts-1",
                shorts = listOf(
                    ShortModel(
                        id = "short-1",
                        thumbnailUrl = "https://picsum.photos/id/1025/240/426",
                        title = "Cute dog playing outside",
                        metadata = "1.2M views",
                    ),
                    ShortModel(
                        id = "short-2",
                        thumbnailUrl = "https://picsum.photos/id/1011/240/426",
                        title = "Beautiful nature in the morning",
                        metadata = "856K views",
                    ),
                    ShortModel(
                        id = "short-3",
                        thumbnailUrl = "https://picsum.photos/id/1035/240/426",
                        title = "A peaceful day near the ocean",
                        metadata = "2.4M views",
                    ),
                )
            ),
            HomeFeed.Video(
                video = VideoModel(
                    id = "video-2",
                    thumbnailUrl = "https://picsum.photos/id/1043/480/270",
                    avatarUrl = "https://i.pravatar.cc/200?img=32",
                    title = "Compose UI Patterns for Modern Android Apps",
                    metadata = "428K views • 2 weeks ago",
                )
            ),
        )

        _uiState.update { it.copy(feedItems = feedItems) }
    }

    private fun loadFilters() {
        val filters: List<Filter> = listOf(
            Filter(id = 1, name = "All", isSelected = true),
            Filter(id = 2, name = "Mixed", isSelected = false),
            Filter(id = 3, name = "Music", isSelected = false),
            Filter(id = 4, name = "Graphic", isSelected = false),
            Filter(id = 5, name = "Film", isSelected = false),
            Filter(id = 6, name = "Live", isSelected = false),
        )
        _uiState.update { it.copy(filters = filters) }
    }

    private fun doFilter(filterSelected: Filter) {
        _uiState.update {
            it.copy(filters = it.filters.map { filter ->
                if (filter.id == filterSelected.id) {
                    filter.copy(isSelected = !filterSelected.isSelected)
                } else {
                    filter.copy(isSelected = false)
                }
            })
        }

        viewModelScope.launch {
            _effect.emit(HomeEffect.FilterSuccess(
                filterName = filterSelected.name
            ))
        }
    }

    private fun submitExplore() {
        viewModelScope.launch {
            _effect.emit(HomeEffect.ExploreSuccess)
        }
    }

    private fun submitVideoClick(video: VideoModel) {
        viewModelScope.launch {
            _effect.emit(HomeEffect.VideoClickSuccess(videoTitle = video.title))
        }
    }

    private fun submitVideoMoreClick(video: VideoModel) {
        viewModelScope.launch {
            _effect.emit(HomeEffect.VideoMoreClickSuccess(videoTitle = video.title))
        }
    }

    private fun submitShortClick(short: ShortModel) {
        viewModelScope.launch {
            _effect.emit(HomeEffect.ShortClickSuccess(shortTitle = short.title))
        }
    }

    private fun submitShortMoreClick(short: ShortModel) {
        viewModelScope.launch {
            _effect.emit(HomeEffect.ShortMoreClickSuccess(shortTitle = short.title))
        }
    }
}
