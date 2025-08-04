package com.coursy.platforms.model

import jakarta.persistence.*
import org.hibernate.Hibernate
import java.util.*

@Entity
class Theme(
    @Id
    var id: UUID = UUID.randomUUID(),
    @Enumerated(EnumType.STRING)
    var courseListLayout: CourseListLayout,
    @Enumerated(EnumType.STRING)
    var videoPlayerType: VideoPlayerType,
    @Embedded
    var colors: Colors,
    @OneToOne(mappedBy = "theme")
    var platform: Platform? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Theme

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}