package com.coursy.platforms.dto

import arrow.core.Either
import arrow.core.raise.either
import com.coursy.platforms.failure.Failure
import com.coursy.platforms.model.Platform
import com.coursy.platforms.types.Description
import com.coursy.platforms.types.Email
import com.coursy.platforms.types.Name

data class PlatformRequest(
    val name: String,
    val description: String,
    val theme: ThemeRequest
) : SelfValidating<Failure, PlatformRequest.Validated> {
    data class Validated(
        val name: Name,
        val description: Description,
        val theme: ThemeRequest.Validated
    ) {
        fun toModel(userEmail: Email) = Platform(
            userEmail = userEmail.value,
            name = this.name.value,
            description = this.description.value,
            theme = theme.toModel()
        )
    }

    override fun validate(): Either<Failure, Validated> {
        return either {
            val validName = Name.create(name).bind()
            val validDescription = Description.create(description).bind()
            val validTheme = theme.validate().bind()

            Validated(
                name = validName,
                description = validDescription,
                theme = validTheme
            )
        }
    }
}