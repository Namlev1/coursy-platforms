package com.coursy.platforms.model.footer

import com.coursy.platforms.model.Platform
import jakarta.persistence.*
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
    @ManyToOne
    @JoinColumn(name = "platform_id")
    var platform: Platform
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as FooterItem

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
