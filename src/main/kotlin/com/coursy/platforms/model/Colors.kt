package com.coursy.platforms.model

import jakarta.persistence.*
import java.awt.Color

@Embeddable
class Colors(
    @Column(name = "primary_color")
    @Convert(converter = ColorConverter::class)
    var primary: Color,

    @Column(name = "secondary_color")
    @Convert(converter = ColorConverter::class)
    var secondary: Color,

    @Column(name = "tertiary_color")
    @Convert(converter = ColorConverter::class)
    var tertiary: Color,

    @Column(name = "background_color")
    @Convert(converter = ColorConverter::class)
    var background: Color,

    @Column(name = "text_primary_color")
    @Convert(converter = ColorConverter::class)
    var textPrimary: Color,

    @Column(name = "text_secondary_color")
    @Convert(converter = ColorConverter::class)
    var textSecondary: Color
)

@Converter
class ColorConverter : AttributeConverter<Color, String> {
    override fun convertToDatabaseColumn(attribute: Color?): String? {
        return attribute?.toHex()
    }

    override fun convertToEntityAttribute(dbData: String?): Color? {
        return dbData?.let {
            Color.decode(it)
        }
    }
}

fun Color.toHex(): String {
    return "#%02x%02x%02x".format(red, green, blue)
}