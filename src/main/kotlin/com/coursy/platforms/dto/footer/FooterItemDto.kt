package com.coursy.platforms.dto.footer

import com.coursy.platforms.model.footer.FooterItem

data class FooterItemDto(
    val href: String,
    val label: String,
    val order: Int,
) {
    fun toModel() = FooterItem(
        href = href,
        label = label,
        order = order,
    )
}

fun FooterItem.toResponse() = FooterItemDto(
    href = href,
    label = label,
    order = order
)
        
