package com.coursy.platforms.dto

import arrow.core.Either
import arrow.core.Either.Companion.catch
import arrow.core.left
import arrow.core.raise.either
import com.coursy.platforms.dto.footer.FooterItemDto
import com.coursy.platforms.dto.footer.toResponse
import com.coursy.platforms.dto.navbar.NavbarConfigDto
import com.coursy.platforms.dto.navbar.toResponse
import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.ThemeFailure
import com.coursy.platforms.model.Platform
import com.coursy.platforms.model.PlatformConfig
import com.coursy.platforms.model.customization.CourseListLayout
import com.coursy.platforms.model.customization.VideoPlayerType
import com.coursy.platforms.model.theme.Colors
import com.coursy.platforms.model.theme.Theme
import java.awt.Color

data class ConfigDto(
    val courseListLayout: String,
    val videoPlayerType: String,
    val colors: Map<String, String>,
    val navbarConfig: NavbarConfigDto,
    val footerItems: List<FooterItemDto>,
) : SelfValidating<Failure, ConfigDto.Validated> {
    data class Validated(
        val courseListLayout: CourseListLayout,
        val videoPlayerType: VideoPlayerType,
        val colors: Colors,
        val navbarConfig: NavbarConfigDto,
        val footerItems: List<FooterItemDto>,
    ) {
        fun toModel(platform: Platform?): PlatformConfig {
            val theme = Theme(colors = colors)
            return PlatformConfig(
                theme = theme,
                courseListLayout = courseListLayout,
                videoPlayerType = videoPlayerType,
                platform = platform
            )
        }
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
                colors = validColors,
                navbarConfig = navbarConfig,
                footerItems = footerItems
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
            val textSecondary = parseColor(colorMap, "textSecondary").bind()

            Colors(
                primary = primary,
                secondary = secondary,
                tertiary = tertiary,
                background = background,
                textPrimary = textPrimary,
                textSecondary = textSecondary
            )
        }
    }

    private fun parseColor(colorMap: Map<String, String>, key: String): Either<Failure, Color> {
        val colorString = colorMap[key] ?: return ThemeFailure.MissingColor(key).left()

        return catch { Color.decode(colorString) }
            .mapLeft { ThemeFailure.InvalidColor(key, colorString) }
    }
}

fun PlatformConfig.toResponse(): ConfigDto {
    return ConfigDto(
        courseListLayout = this.courseListLayout.name,
        videoPlayerType = this.videoPlayerType.name,
        colors = mapOf(
            "primary" to String.format("#%06X", 0xFFFFFF and this.theme.colors.primary.rgb),
            "secondary" to String.format("#%06X", 0xFFFFFF and this.theme.colors.secondary.rgb),
            "tertiary" to String.format("#%06X", 0xFFFFFF and this.theme.colors.tertiary.rgb),
            "background" to String.format("#%06X", 0xFFFFFF and this.theme.colors.background.rgb),
            "textPrimary" to String.format("#%06X", 0xFFFFFF and this.theme.colors.textPrimary.rgb),
            "textSecondary" to String.format("#%06X", 0xFFFFFF and this.theme.colors.textSecondary.rgb),
        ),
        navbarConfig = this.navbarConfig.toResponse(),
        footerItems = this.footerItems.map { it.toResponse() }
    )
}