package com.coursy.be.model.platform

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