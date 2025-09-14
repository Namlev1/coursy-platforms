package com.coursy.platforms.dto

import com.coursy.platforms.model.page.PageSection
import com.fasterxml.jackson.databind.JsonNode
import java.util.*

data class PageSectionResponse(
    val id: UUID,
    val type: String,
    val order: Int,
    val props: JsonNode
)

fun PageSection.toResponse() = PageSectionResponse(
    this.id,
    this.type,
    this.order,
    this.props
)