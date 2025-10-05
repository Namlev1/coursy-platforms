package com.coursy.platforms.dto

import com.coursy.platforms.model.Platform
import java.util.*

data class PlatformResponse(
    val id: UUID,
    val name: String,
    val description: String,
    val config: ConfigDto?,
    val subdomain: String
)

fun Platform.toResponse(): PlatformResponse =
    PlatformResponse(
        id = this.id,
        name = this.name,
        description = this.description,
        config = this.config?.toResponse(),
        subdomain = this.subdomain
    )
