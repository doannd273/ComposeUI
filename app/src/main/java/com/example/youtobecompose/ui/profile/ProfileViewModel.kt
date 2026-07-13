package com.example.youtobecompose.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtobecompose.R
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
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<ProfileEffect>()
    val effect: SharedFlow<ProfileEffect> = _effect.asSharedFlow()

    init {
        loadProfileState()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.SearchClick -> {
                submitSearchClick()
            }

            ProfileEvent.MoreClick -> {
                submitMoreClick()
            }

            ProfileEvent.AnalyticsClick -> {
                submitAnalyticsClick()
            }

            ProfileEvent.EditChannelClick -> {
                submitEditChannelClick()
            }

            ProfileEvent.OpenVideosClick -> {
                submitOpenVideosClick()
            }

            is ProfileEvent.VideoClick -> {
                submitVideoClick(video = event.video)
            }

            is ProfileEvent.VideoMoreClick -> {
                submitVideoMoreClick(video = event.video)
            }

            is ProfileEvent.TabSelected -> {
                _uiState.update { state ->
                    state.copy(selectedTab = event.tab)
                }
                submitTabSelected(tab = event.tab)
            }
        }
    }

    private fun loadProfileState() {
        _uiState.update { state ->
            ProfileState(
                bannerUrl = "https://picsum.photos/id/1043/480/270",
                avatarUrl = "https://i.pravatar.cc/200?img=32",
                selectedTab = state.selectedTab,
                homeVideos = loadHomeVideos(),
            )
        }
    }

    private fun loadHomeVideos(): List<ProfileVideoUiModel> {
        return listOf(
            ProfileVideoUiModel(
                id = "demo_git",
                thumbnailUrl = "https://picsum.photos/id/1015/480/270",
                titleRes = R.string.channel_video_demo_git,
                metadataRes = R.string.channel_video_demo_git_metadata,
                durationRes = R.string.channel_video_demo_git_duration,
            ),
            ProfileVideoUiModel(
                id = "android_splash",
                thumbnailUrl = "https://picsum.photos/id/1043/480/270",
                titleRes = R.string.channel_video_android_splash,
                metadataRes = R.string.channel_video_android_splash_metadata,
                durationRes = R.string.channel_video_android_splash_duration,
            ),
            ProfileVideoUiModel(
                id = "library",
                thumbnailUrl = "https://picsum.photos/seed/video3/480/270",
                titleRes = R.string.channel_video_library,
                metadataRes = R.string.channel_video_library_metadata,
                durationRes = R.string.channel_video_library_duration,
            ),
            ProfileVideoUiModel(
                id = "compose_profile",
                thumbnailUrl = "https://picsum.photos/id/1067/480/270",
                titleRes = R.string.channel_video_compose_profile,
                metadataRes = R.string.channel_video_compose_profile_metadata,
                durationRes = R.string.channel_video_compose_profile_duration,
            ),
            ProfileVideoUiModel(
                id = "android_navigation",
                thumbnailUrl = "https://picsum.photos/id/1036/480/270",
                titleRes = R.string.channel_video_android_navigation,
                metadataRes = R.string.channel_video_android_navigation_metadata,
                durationRes = R.string.channel_video_android_navigation_duration,
            ),
        )
    }

    private fun submitSearchClick() {
        viewModelScope.launch {
            _effect.emit(ProfileEffect.SearchClickSuccess)
        }
    }

    private fun submitMoreClick() {
        viewModelScope.launch {
            _effect.emit(ProfileEffect.MoreClickSuccess)
        }
    }

    private fun submitAnalyticsClick() {
        viewModelScope.launch {
            _effect.emit(ProfileEffect.AnalyticsClickSuccess)
        }
    }

    private fun submitEditChannelClick() {
        viewModelScope.launch {
            _effect.emit(ProfileEffect.EditChannelClickSuccess)
        }
    }

    private fun submitOpenVideosClick() {
        viewModelScope.launch {
            _effect.emit(ProfileEffect.OpenVideosClickSuccess)
        }
    }

    private fun submitVideoClick(video: ProfileVideoUiModel) {
        viewModelScope.launch {
            _effect.emit(ProfileEffect.VideoClickSuccess(videoTitleRes = video.titleRes))
        }
    }

    private fun submitVideoMoreClick(video: ProfileVideoUiModel) {
        viewModelScope.launch {
            _effect.emit(ProfileEffect.VideoMoreClickSuccess(videoTitleRes = video.titleRes))
        }
    }

    private fun submitTabSelected(tab: ProfileTab) {
        viewModelScope.launch {
            _effect.emit(ProfileEffect.TabSelectedSuccess(tab = tab))
        }
    }
}
