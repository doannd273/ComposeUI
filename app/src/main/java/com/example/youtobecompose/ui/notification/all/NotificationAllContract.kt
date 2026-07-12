package com.example.youtobecompose.ui.notification.all

import com.example.youtobecompose.model.NotificationGroupModel
import com.example.youtobecompose.model.NotificationModel

data class NotificationAllState(
    val groups: List<NotificationGroupModel> = listOf()
)

sealed class NotificationAllEvent {
    data object EnableNotificationsClick : NotificationAllEvent()

    data object DismissBannerClick : NotificationAllEvent()

    data class GroupClick(val groupId: String) : NotificationAllEvent()

    data class NotificationClick(val notification: NotificationModel) : NotificationAllEvent()

    data class NotificationMoreClick(val notification: NotificationModel) : NotificationAllEvent()
}

sealed class NotificationAllEffect {
    data object EnableNotificationsClickSuccess : NotificationAllEffect()

    data object DismissBannerClickSuccess : NotificationAllEffect()

    data class NotificationClickSuccess(val notificationTitle: String) : NotificationAllEffect()

    data class NotificationMoreClickSuccess(val notificationTitle: String) : NotificationAllEffect()
}
