package com.coursy.platforms.dto

import arrow.core.Either
import arrow.core.Either.Companion.catch
import arrow.core.raise.either
import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.PageTemplateFailure
import com.coursy.platforms.model.PageTemplate
import com.coursy.platforms.model.PageType
import com.coursy.platforms.types.PageTitle
import com.fasterxml.jackson.databind.JsonNode

data class PageTemplateRequest(
    val title: String,
    val type: String,
    val sections: List<PageSectionRequest>,
    val props: JsonNode?,
) : SelfValidating<Failure, PageTemplateRequest.Validated> {
    data class Validated(
        val title: PageTitle,
        val type: PageType,
        val sections: List<PageSectionRequest.Validated>,
        val props: JsonNode?,
    ) {
        fun toModel() = PageTemplate(
            title = title.value,
            sections = sections.map { it.toModel() }.toMutableList(),
            type = type,
            props = props
        )
    }

    override fun validate(): Either<Failure, Validated> {
        return either {
            val validTitle = PageTitle.create(title).bind()
            val validSections = sections.map { it.validate().bind() }
            val validType = catch { PageType.valueOf(type) }
                .mapLeft { PageTemplateFailure.InvalidType(type) }
                .bind()

            Validated(
                title = validTitle,
                sections = validSections,
                type = validType,
                props = props
            )
        }
    }
}