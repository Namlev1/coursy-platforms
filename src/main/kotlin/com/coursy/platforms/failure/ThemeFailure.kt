package com.coursy.platforms.failure

import java.util.*

sealed class ThemeFailure : Failure {
    data class InvalidVideoPlayerType(val type: String) : ThemeFailure()
    data class InvalidCourseListLayout(val layout: String) : ThemeFailure()
    data class MissingColor(val color: String) : ThemeFailure()
    data class InvalidColor(val key: String, val colorString: String) : ThemeFailure()
    data class NotFound(val id: UUID) : ThemeFailure()

    override fun message(): String = when (this) {
        is InvalidVideoPlayerType -> "Invalid video player type: $type"
        is InvalidCourseListLayout -> "Invalid course list layout: $layout"
        is MissingColor -> "Missing color: $color"
        is InvalidColor -> "Invalid color: [$key, $colorString]"
        is NotFound -> "Theme id=$id not found"
    }
}
