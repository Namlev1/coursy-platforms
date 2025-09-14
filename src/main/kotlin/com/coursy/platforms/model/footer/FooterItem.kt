package com.coursy.platforms.model.footer

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.Hibernate
import java.util.*

@Entity
class FooterItem(
    @Id
    var id: UUID = UUID.randomUUID(),
    var href: String,
    var label: String,
    @Column(name = "sort_order")
    var order: Int,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as FooterItem

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
