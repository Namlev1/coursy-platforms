package com.coursy.platforms.dto

import com.coursy.platforms.model.customization.CourseListLayout
import com.coursy.platforms.model.customization.VideoPlayerType
import com.coursy.platforms.model.theme.Theme

data class ThemeResponse(
    val colors: ColorsResponse,
    var courseListLayout: CourseListLayout,
    var videoPlayerType: VideoPlayerType,
)

fun Theme.toResponse() = ThemeResponse(
    colors = this.colors.toResponse(),
    courseListLayout = this.courseListLayout,
    videoPlayerType = this.videoPlayerType
)