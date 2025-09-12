package com.coursy.platforms.model.footer

object DefaultFooter {
    fun create(): MutableList<FooterItem> {
        return mutableListOf(
            FooterItem(
                href = "/tos",
                label = "Terms of Service"
            ),
            FooterItem(
                href = "/privacy",
                label = "Privacy Policy"
            ),
            FooterItem(
                href = "/contact",
                label = "Contact us"
            ),
        )
    }
}
