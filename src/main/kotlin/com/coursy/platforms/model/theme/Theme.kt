package com.coursy.platforms.model.theme

import com.coursy.platforms.model.PlatformConfig
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import org.hibernate.Hibernate
import java.util.*

@Entity
class Theme(
    @Id
    var id: UUID = UUID.randomUUID(),
    @Embedded
    var colors: Colors,
    @OneToOne(mappedBy = "theme")
    var config: PlatformConfig? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Theme

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}