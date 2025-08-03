package com.coursy.platforms.dto

import com.coursy.platforms.model.PageTemplate
import java.util.*

data class PageTemplateResponse(
    val id: UUID,
    val title: String,
    val sections: List<PageSectionResponse>
)

fun PageTemplate.toResponse() = PageTemplateResponse(
    this.id,
    this.title,
    this.sections.map { it.toResponse() }
)