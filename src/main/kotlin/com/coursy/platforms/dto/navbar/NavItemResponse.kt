package com.coursy.platforms.dto.navbar

import com.coursy.platforms.model.navbar.NavItem
import com.coursy.platforms.model.navbar.NavItemAccess

data class NavItemResponse(
    val href: String,
    val label: String,
    val access: NavItemAccess
)

fun NavItem.toResponse() = NavItemResponse(
    href = href,
    label = label,
    access = access
)
        
