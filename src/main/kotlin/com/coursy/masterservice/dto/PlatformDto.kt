package com.coursy.masterservice.dto

import com.coursy.masterservice.model.Platform
import com.coursy.masterservice.types.Email

data class PlatformDto(
    val id: Long?,
    val name: String,
)

fun Platform.toDto(): PlatformDto =
    PlatformDto(
        id = this.id,
        name = this.name
    )

fun PlatformDto.toModel(email: Email): Platform =
    Platform(
        id = this.id,
        name = this.name,
        userEmail = email.value
    )
