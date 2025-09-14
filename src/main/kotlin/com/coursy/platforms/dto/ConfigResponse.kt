package com.coursy.platforms.dto

import com.coursy.platforms.dto.footer.FooterItemResponse
import com.coursy.platforms.dto.footer.toResponse
import com.coursy.platforms.dto.navbar.NavbarConfigResponse
import com.coursy.platforms.dto.navbar.toResponse
import com.coursy.platforms.model.PlatformConfig
import com.coursy.platforms.model.customization.CourseListLayout
import com.coursy.platforms.model.customization.VideoPlayerType

data class ConfigResponse(
    val colors: ColorsResponse,
    var courseListLayout: CourseListLayout,
    var videoPlayerType: VideoPlayerType,
    var footerItems: List<FooterItemResponse>,
    var navbarConfig: NavbarConfigResponse
)

fun PlatformConfig.toResponse() = ConfigResponse(
    colors = this.theme.colors.toResponse(),
    courseListLayout = this.courseListLayout,
    videoPlayerType = this.videoPlayerType,
    footerItems = this.footerItems.map { it.toResponse() },
    navbarConfig = this.navbarConfig.toResponse()
)