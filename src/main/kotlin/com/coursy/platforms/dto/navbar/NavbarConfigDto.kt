package com.coursy.platforms.dto.navbar

import com.coursy.platforms.model.navbar.NavbarConfig

data class NavbarConfigDto(
    val logoUrl: String?,
    val logoText: String?,
    val isLogoVisible: Boolean,
    val navItems: List<NavItemDto>
)

fun NavbarConfig.toResponse() = NavbarConfigDto(
    logoUrl = logoUrl,
    logoText = logoText,
    isLogoVisible = isLogoVisible,
    navItems = navItems.map { it.toResponse() }
)
        
