package com.coursy.platforms.model.footer

import com.coursy.platforms.model.Platform

object DefaultFooter {
    fun create(platform: Platform): MutableList<FooterItem> {
        return mutableListOf(
            FooterItem(
                href = "/tos",
                label = "Terms of Service",
                order = 1,
                platform = platform,
            ),
            FooterItem(
                href = "/privacy",
                label = "Privacy Policy",
                order = 2,
                platform = platform,
            ),
            FooterItem(
                href = "/contact",
                label = "Contact us",
                order = 3,
                platform = platform,
            ),
        )
    }
}
