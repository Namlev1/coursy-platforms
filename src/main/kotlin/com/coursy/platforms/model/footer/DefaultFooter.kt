package com.coursy.platforms.model.footer

import com.coursy.platforms.model.PlatformConfig

object DefaultFooter {
    fun create(platformConfig: PlatformConfig): MutableList<FooterItem> {
        return mutableListOf(
            FooterItem(
                href = "/tos",
                label = "Terms of Service",
                order = 1,
                config = platformConfig,
            ),
            FooterItem(
                href = "/privacy",
                label = "Privacy Policy",
                order = 2,
                config = platformConfig,
            ),
            FooterItem(
                href = "/contact",
                label = "Contact us",
                order = 3,
                config = platformConfig,
            ),
        )
    }
}
