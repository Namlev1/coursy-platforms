package com.coursy.platforms.model.navbar

object DefaultNavbar {
    fun create(): NavbarConfig {
        return NavbarConfig(
            logoUrl = null,
            logoText = null,
            isLogoVisible = false,
            navItems = mutableListOf(
                NavItem(
                    href = "/",
                    label = "Home",
                    access = NavItemAccess.public
                ),
                NavItem(
                    href = "/courses",
                    label = "Courses",
                    access = NavItemAccess.public
                ),
                NavItem(
                    href = "/dashboard",
                    label = "Dashboard",
                    access = NavItemAccess.admin
                ),
                NavItem(
                    href = "/my-learning",
                    label = "My learning",
                    access = NavItemAccess.user
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