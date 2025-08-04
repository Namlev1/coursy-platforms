package com.coursy.platforms.dto

import com.coursy.platforms.model.Colors
import com.coursy.platforms.model.CourseListLayout
import com.coursy.platforms.model.Theme
import com.coursy.platforms.model.VideoPlayerType

data class ThemeResponse(
    val colors: Colors,
    var courseListLayout: CourseListLayout,
    var videoPlayerType: VideoPlayerType,
)

fun Theme.toResponse() = ThemeResponse(
    colors = this.colors,
    courseListLayout = this.courseListLayout,
    videoPlayerType = this.videoPlayerType
)