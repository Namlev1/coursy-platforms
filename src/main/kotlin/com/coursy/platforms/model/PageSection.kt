package com.coursy.platforms.model

import com.fasterxml.jackson.databind.JsonNode
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.Hibernate
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.*

@Entity
class PageSection(
    @Id
    var id: UUID = UUID.randomUUID(),
    var type: String,  // Just a string identifier
    @Column(name = "`order`")
    var order: Int,
    @JdbcTypeCode(SqlTypes.JSON)
    var props: JsonNode
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as PageSection

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
} 
