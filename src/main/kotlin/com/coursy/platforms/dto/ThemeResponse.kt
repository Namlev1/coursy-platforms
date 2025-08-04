package com.coursy.platforms.dto

import com.coursy.platforms.model.CourseListLayout
import com.coursy.platforms.model.Theme
import com.coursy.platforms.model.VideoPlayerType

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