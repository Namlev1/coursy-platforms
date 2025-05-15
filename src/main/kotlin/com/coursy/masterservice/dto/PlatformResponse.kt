package com.coursy.masterservice.dto

import com.coursy.masterservice.model.Platform

data class PlatformResponse(
    val id: Long?,
    val name: String,
    val description: String
)

fun Platform.toResponse(): PlatformResponse =
    PlatformResponse(
        id = this.id,
        name = this.name,
        description = this.description
    )
