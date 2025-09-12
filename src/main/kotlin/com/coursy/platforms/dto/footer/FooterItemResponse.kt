package com.coursy.platforms.dto.footer

import com.coursy.platforms.model.footer.FooterItem

data class FooterItemResponse(
    val href: String,
    val label: String,
    val order: Int,
)

fun FooterItem.toResponse() = FooterItemResponse(
    href = href,
    label = label,
    order = order
)
        
