package com.coursy.platforms.dto

import arrow.core.Either
import arrow.core.raise.either
import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.ValidationFailure
import com.coursy.platforms.model.Platform
import com.coursy.platforms.types.Description
import com.coursy.platforms.types.Email
import com.coursy.platforms.types.Name

data class PlatformRequest(
    val name: String,
    val description: String,
) : SelfValidating<Failure, PlatformRequest.Validated> {
    data class Validated(
        val name: Name,
        val description: Description
    ) {
        fun toModel(userEmail: Email) = Platform(
            userEmail = userEmail.value,
            name = this.name.value,
            description = this.description.value
        )
    }

    override fun validate(): Either<ValidationFailure, Validated> {
        return either {
            val validName = Name.create(name).bind()
            val validDescription = Description.create(description).bind()

            Validated(
                name = validName,
                description = validDescription,
            )
        }
    }
}