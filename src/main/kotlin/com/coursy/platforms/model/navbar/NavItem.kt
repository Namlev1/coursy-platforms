package com.coursy.platforms.model.navbar

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.hibernate.Hibernate
import java.util.*

@Entity
class NavItem(
    @Id
    var id: UUID = UUID.randomUUID(),
    var href: String,
    var label: String,
    @Enumerated(EnumType.STRING)
    var access: NavItemAccess,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as NavItem

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
