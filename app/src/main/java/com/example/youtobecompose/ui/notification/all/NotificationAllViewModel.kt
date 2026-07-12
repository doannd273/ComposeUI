package com.example.youtobecompose.ui.notification.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtobecompose.model.NotificationGroupModel
import com.example.youtobecompose.model.NotificationModel
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
class NotificationAllViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationAllState())
    val uiState: StateFlow<NotificationAllState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<NotificationAllEffect>()
    val effect: SharedFlow<NotificationAllEffect> = _effect.asSharedFlow()

    init {
        loadData()
    }

    fun onEvent(event: NotificationAllEvent) {
        when (event) {
            NotificationAllEvent.DismissBannerClick -> {
                submitDismissBannerClick()
            }

            NotificationAllEvent.EnableNotificationsClick -> {
                submitEnableNotificationsClick()
            }

            is NotificationAllEvent.GroupClick -> {
                toggleGroupExpand(groupId = event.groupId)
            }

            is NotificationAllEvent.NotificationClick -> {
                submitNotificationClick(notification = event.notification)
            }

            is NotificationAllEvent.NotificationMoreClick -> {
                submitNotificationMoreClick(notification = event.notification)
            }
        }
    }

    private fun loadData() {
        val groups = listOf(
            NotificationGroupModel(
                groupId = "android-dev",
                channelName = "Android Developers",
                avatarUrl = "https://i.pravatar.cc/200?img=12",
                isExpand = true,
                notifications = listOf(
                    NotificationModel(
                        id = "android-dev-1",
                        title = "New Compose performance tips are available",
                        timeText = "2 hours ago",
                        thumbnailUrl = "https://picsum.photos/id/1015/320/180",
                    ),
                    NotificationModel(
                        id = "android-dev-2",
                        title = "Build better adaptive layouts with Material 3",
                        timeText = "1 day ago",
                        thumbnailUrl = "https://picsum.photos/id/1025/320/180",
                    ),
                )
            ),
            NotificationGroupModel(
                groupId = "kotlin",
                channelName = "Kotlin",
                avatarUrl = "https://i.pravatar.cc/200?img=33",
                isExpand = false,
                notifications = listOf(
                    NotificationModel(
                        id = "kotlin-1",
                        title = "Kotlin language update and tooling highlights",
                        timeText = "3 days ago",
                        thumbnailUrl = "https://picsum.photos/id/1035/320/180",
                    ),
                    NotificationModel(
                        id = "kotlin-2",
                        title = "Coroutines patterns for cleaner Android apps",
                        timeText = "1 week ago",
                        thumbnailUrl = "https://picsum.photos/id/1043/320/180",
                    ),
                )
            ),
            NotificationGroupModel(
                groupId = "compose-academy",
                channelName = "Compose Academy",
                avatarUrl = "https://i.pravatar.cc/200?img=49",
                isExpand = true,
                notifications = listOf(
                    NotificationModel(
                        id = "compose-academy-1",
                        title = "State hoisting mistakes that make screens hard to test",
                        timeText = "2 weeks ago",
                        thumbnailUrl = "https://picsum.photos/id/1076/320/180",
                    ),
                )
            ),
        )

        _uiState.update { state ->
            state.copy(groups = groups)
        }
    }

    private fun toggleGroupExpand(groupId: String) {
        _uiState.update { state ->
            state.copy(
                groups = state.groups.map { group ->
                    if (group.groupId == groupId) {
                        group.copy(isExpand = !group.isExpand)
                    } else {
                        group
                    }
                }
            )
        }
    }

    private fun submitEnableNotificationsClick() {
        viewModelScope.launch {
            _effect.emit(NotificationAllEffect.EnableNotificationsClickSuccess)
        }
    }

    private fun submitDismissBannerClick() {
        viewModelScope.launch {
            _effect.emit(NotificationAllEffect.DismissBannerClickSuccess)
        }
    }

    private fun submitNotificationClick(notification: NotificationModel) {
        viewModelScope.launch {
            _effect.emit(
                NotificationAllEffect.NotificationClickSuccess(
                    notificationTitle = notification.title
                )
            )
        }
    }

    private fun submitNotificationMoreClick(notification: NotificationModel) {
        viewModelScope.launch {
            _effect.emit(
                NotificationAllEffect.NotificationMoreClickSuccess(
                    notificationTitle = notification.title
                )
            )
        }
    }
}
