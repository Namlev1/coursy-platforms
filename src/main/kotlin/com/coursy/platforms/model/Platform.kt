package com.coursy.platforms.model

import com.coursy.platforms.model.footer.DefaultFooter
import com.coursy.platforms.model.footer.FooterItem
import com.coursy.platforms.model.navbar.DefaultNavbar
import com.coursy.platforms.model.navbar.NavbarConfig
import com.coursy.platforms.model.page.PageTemplate
import com.coursy.platforms.model.theme.Theme
import jakarta.annotation.PostConstruct
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.util.*

@Entity
class Platform(
    @Id
    var id: UUID = UUID.randomUUID(),
    var userEmail: String,
    var name: String,
    var description: String,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "platform_id")
    var templates: MutableList<PageTemplate> = mutableListOf(),
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    var theme: Theme,
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var navbarConfig: NavbarConfig = DefaultNavbar.create(),
    @OneToMany(mappedBy = "platform", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var footerItems: MutableList<FooterItem> = mutableListOf()
    
) {
    @PostConstruct
    fun initializeDefaults() {
        if (footerItems.isEmpty()) {
            footerItems.addAll(DefaultFooter.create(this))
        }
    } 
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Platform

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
} 