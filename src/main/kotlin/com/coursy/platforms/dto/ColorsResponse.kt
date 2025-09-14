package com.coursy.platforms.dto

import com.coursy.platforms.model.theme.Colors
import com.coursy.platforms.model.theme.toHex

data class ColorsResponse(
    var primary: String,
    var secondary: String,
    var tertiary: String,
    var background: String,
    var textPrimary: String,
    var textSecondary: String
)

fun Colors.toResponse() = ColorsResponse(
    this.primary.toHex(),
    this.secondary.toHex(),
    this.tertiary.toHex(),
    this.background.toHex(),
    this.textPrimary.toHex(),
    this.textSecondary.toHex(),
)