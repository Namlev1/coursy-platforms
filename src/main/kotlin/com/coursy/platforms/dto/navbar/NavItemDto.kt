package com.coursy.platforms.dto.navbar

import com.coursy.platforms.model.navbar.NavItem
import com.coursy.platforms.model.navbar.NavItemAccess

data class NavItemDto(
    val href: String,
    val label: String,
    val access: NavItemAccess
) {
    fun toModel() = NavItem(
        href = href,
        label = label,
        access = access
    )
}

fun NavItem.toResponse() = NavItemDto(
    href = href,
    label = label,
    access = access
)
        
