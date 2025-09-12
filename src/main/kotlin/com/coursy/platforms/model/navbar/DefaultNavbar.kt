package com.coursy.platforms.model.navbar

object DefaultNavbar {
    fun create(): NavbarConfig {
        return NavbarConfig(
            logoUrl = null,
            logoText = null,
            isLogoVisible = false,
            navItems = mutableListOf(
                NavItem(
                    href = "/home",
                    label = "Home",
                    access = NavItemAccess.public
                ),
                NavItem(
                    href = "/contact",
                    label = "Contact",
                    access = NavItemAccess.public
                )
            )
        )
    }
}