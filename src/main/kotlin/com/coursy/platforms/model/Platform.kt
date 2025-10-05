package com.coursy.platforms.model

import com.coursy.platforms.model.page.PageTemplate
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
    @JoinColumn(name = "config_id")
    var config: PlatformConfig?,
    var subdomain: String
) {
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Platform

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
} 