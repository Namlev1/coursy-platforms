package com.coursy.platforms.model.footer

object DefaultFooter {
    fun create(): MutableList<FooterItem> {
        return mutableListOf(
            FooterItem(
                href = "/tos",
                label = "Terms of Service",
                order = 1,
            ),
            FooterItem(
                href = "/privacy",
                label = "Privacy Policy",
                order = 2,
            ),
            FooterItem(
                href = "/contact",
                label = "Contact us",
                order = 3,
            ),
        )
    }
}
