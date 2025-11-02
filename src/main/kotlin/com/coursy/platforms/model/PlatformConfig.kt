package com.coursy.platforms.model

import com.coursy.platforms.model.customization.CourseListLayout
import com.coursy.platforms.model.customization.VideoPlayerType
import com.coursy.platforms.model.footer.DefaultFooter
import com.coursy.platforms.model.footer.FooterItem
import com.coursy.platforms.model.navbar.DefaultNavbar
import com.coursy.platforms.model.navbar.NavbarConfig
import com.coursy.platforms.model.theme.Theme
import com.coursy.platforms.types.CtaText
import com.coursy.platforms.types.HeroSubtitle
import com.coursy.platforms.types.HeroTitle
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.util.*

@Entity
class PlatformConfig(
    @Id
    var id: UUID = UUID.randomUUID(),
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    var theme: Theme,
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var navbarConfig: NavbarConfig = DefaultNavbar.create(),
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "platform_config_id")
    var footerItems: MutableList<FooterItem> = DefaultFooter.create(),
    @Enumerated(EnumType.STRING)
    var courseListLayout: CourseListLayout,
    @Enumerated(EnumType.STRING)
    var videoPlayerType: VideoPlayerType,

    var heroTitle: HeroTitle,
    var heroSubtitle: HeroSubtitle,
    var ctaText: CtaText,

    @OneToOne(mappedBy = "config")
    @JsonIgnore
    var platform: Platform?
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as PlatformConfig

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
} 
