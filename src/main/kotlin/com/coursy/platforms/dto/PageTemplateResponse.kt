package com.coursy.platforms.dto

import com.coursy.platforms.model.PageTemplate
import com.coursy.platforms.model.PageType
import com.fasterxml.jackson.databind.JsonNode
import java.util.*

data class PageTemplateResponse(
    val id: UUID,
    val title: String,
    val type: PageType,
    val sections: List<PageSectionResponse>,
    val props: JsonNode?
)

fun PageTemplate.toResponse() = PageTemplateResponse(
    this.id,
    this.title,
    this.type,
    this.sections.map { it.toResponse() },
    this.props
)