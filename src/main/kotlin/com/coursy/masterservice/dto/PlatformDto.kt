package com.coursy.masterservice.dto

import com.coursy.masterservice.model.Platform

data class PlatformDto(
    val id: Long?,
    val name: String,
)

fun Platform.toDto(): PlatformDto =
    PlatformDto(
        id = this.id,
        name = this.name
    )

fun PlatformDto.toModel(): Platform =
    Platform(
        id = this.id,
        name = this.name,
    )
