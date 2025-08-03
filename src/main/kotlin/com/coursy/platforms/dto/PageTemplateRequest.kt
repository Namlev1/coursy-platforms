package com.coursy.platforms.dto

import arrow.core.Either
import arrow.core.raise.either
import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.ValidationFailure
import com.coursy.platforms.model.PageTemplate
import com.coursy.platforms.types.PageTitle

data class PageTemplateRequest(
    val title: String,
    val sections: List<PageSectionRequest>,
) : SelfValidating<Failure, PageTemplateRequest.Validated> {
    data class Validated(
        val title: PageTitle,
        val sections: List<PageSectionRequest.Validated>,
    ) {
        fun toModel() = PageTemplate(
            title = title.value,
            sections = sections.map { it.toModel() }.toMutableList()
        )
    }

    override fun validate(): Either<ValidationFailure, Validated> {
        return either {
            val validTitle = PageTitle.create(title).bind()
            val validSections = sections.map { it.validate().bind() }

            Validated(
                title = validTitle,
                sections = validSections
            )
        }
    }
}