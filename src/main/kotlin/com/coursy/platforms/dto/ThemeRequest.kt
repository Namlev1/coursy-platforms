package com.coursy.platforms.dto

import arrow.core.Either
import arrow.core.Either.Companion.catch
import arrow.core.left
import arrow.core.raise.either
import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.ThemeFailure
import com.coursy.platforms.model.Colors
import com.coursy.platforms.model.CourseListLayout
import com.coursy.platforms.model.Theme
import com.coursy.platforms.model.VideoPlayerType
import java.awt.Color

data class ThemeRequest(
    val courseListLayout: String,
    val videoPlayerType: String,
    val colors: Map<String, String>
) : SelfValidating<Failure, ThemeRequest.Validated> {
    data class Validated(
        val courseListLayout: CourseListLayout,
        val videoPlayerType: VideoPlayerType,
        val colors: Colors,
    ) {
        fun toModel() = Theme(
            courseListLayout = courseListLayout,
            videoPlayerType = videoPlayerType,
            colors = colors
        )
    }

    override fun validate(): Either<Failure, Validated> {
        return either {
            val validCourseListLayout = catch { CourseListLayout.valueOf(courseListLayout) }
                .mapLeft { ThemeFailure.InvalidCourseListLayout(courseListLayout) }
                .bind()
            val validVideoPlayerType = catch { VideoPlayerType.valueOf(videoPlayerType) }
                .mapLeft { ThemeFailure.InvalidVideoPlayerType(videoPlayerType) }
                .bind()
            val validColors = validateColors(colors).bind()

            Validated(
                courseListLayout = validCourseListLayout,
                videoPlayerType = validVideoPlayerType,
                colors = validColors
            )
        }
    }

    private fun validateColors(colorMap: Map<String, String>): Either<Failure, Colors> {
        return either {
            val primary = parseColor(colorMap, "primary").bind()
            val secondary = parseColor(colorMap, "secondary").bind()
            val tertiary = parseColor(colorMap, "tertiary").bind()
            val background = parseColor(colorMap, "background").bind()
            val textPrimary = parseColor(colorMap, "textPrimary").bind()

            Colors(
                primary = primary,
                secondary = secondary,
                tertiary = tertiary,
                background = background,
                textPrimary = textPrimary
            )
        }
    }

    private fun parseColor(colorMap: Map<String, String>, key: String): Either<Failure, Color> {
        val colorString = colorMap[key] ?: return ThemeFailure.MissingColor(key).left()

        return catch { Color.decode(colorString) }
            .mapLeft { ThemeFailure.InvalidColor(key, colorString) }
    }
}
