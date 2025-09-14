package com.coursy.platforms.dto.navbar

import com.coursy.platforms.model.navbar.NavbarConfig

data class NavbarConfigResponse(
    val logoUrl: String?,
    val logoText: String?,
    val isLogoVisible: Boolean,
    val navItems: List<NavItemResponse>
)

fun NavbarConfig.toResponse() = NavbarConfigResponse(
    logoUrl = logoUrl,
    logoText = logoText,
    isLogoVisible = isLogoVisible,
    navItems = navItems.map { it.toResponse() }
)
        
