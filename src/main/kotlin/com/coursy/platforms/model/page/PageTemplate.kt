package com.coursy.platforms.model.page

import com.fasterxml.jackson.databind.JsonNode
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.*

@Entity
class PageTemplate(
    @Id
    var id: UUID = UUID.randomUUID(),
    var title: String,
    @Enumerated(EnumType.STRING)
    var type: PageType,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "page_template_id")
    var sections: MutableList<PageSection> = mutableListOf(),
    @JdbcTypeCode(SqlTypes.JSON)
    var props: JsonNode?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as PageTemplate

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
} 
