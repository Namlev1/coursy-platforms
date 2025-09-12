package com.coursy.platforms.model.navbar

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import org.hibernate.Hibernate
import java.util.*

@Entity
class NavbarConfig(
    @Id
    var id: UUID = UUID.randomUUID(),
    var logoUrl: String?,
    var logoText: String?,
    var isLogoVisible: Boolean,
    @OneToMany
    @JoinColumn(name = "navbar_id")
    var navItems: MutableList<NavItem>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as NavbarConfig

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}