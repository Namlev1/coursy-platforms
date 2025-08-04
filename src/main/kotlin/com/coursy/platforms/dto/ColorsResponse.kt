package com.coursy.platforms.dto

import com.coursy.platforms.model.Colors
import com.coursy.platforms.model.toHex

data class ColorsResponse(
    var primary: String,
    var secondary: String,
    var tertiary: String,
    var background: String,
    var textPrimary: String
)

fun Colors.toResponse() = ColorsResponse(
    this.primary.toHex(),
    this.secondary.toHex(),
    this.tertiary.toHex(),
    this.background.toHex(),
    this.textPrimary.toHex(),
)