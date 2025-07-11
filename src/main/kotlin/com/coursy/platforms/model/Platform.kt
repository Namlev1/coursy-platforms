package com.coursy.platforms.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.Hibernate
import java.util.*

@Entity
class Platform(
    @Id
    var id: UUID = UUID.randomUUID(),
    var userEmail: String,
    var name: String,
    var description: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Platform

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
} 