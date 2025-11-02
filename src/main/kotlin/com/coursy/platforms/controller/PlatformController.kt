package com.coursy.platforms.controller

import arrow.core.flatMap
import arrow.core.right
import com.coursy.platforms.dto.PlatformRequest
import com.coursy.platforms.dto.PlatformResponse
import com.coursy.platforms.dto.toResponse
import com.coursy.platforms.failure.Failure
import com.coursy.platforms.failure.PlatformFailure
import com.coursy.platforms.security.readToken
import com.coursy.platforms.service.ImagesManagementService
import com.coursy.platforms.service.PlatformService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/api/platforms")
class PlatformController(
    val service: PlatformService,
    val imageService: ImagesManagementService,
) {

    @GetMapping
    fun getAllPlatforms(jwt: PreAuthenticatedAuthenticationToken): List<PlatformResponse> {
        val (email, _) = jwt.readToken()
        return service.getByUserEmail(email)
    }

    @GetMapping("/{id}")
    fun getPlatformById(@PathVariable id: UUID): ResponseEntity<Any> {
        return service.getById(id)
            .fold(
                { handleFailure(it) },
                { ResponseEntity.status(HttpStatus.OK).body(it) }
            )
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createPlatform(
        @RequestPart("data") platformRequest: PlatformRequest,
        @RequestPart("logo") logo: MultipartFile,
        @RequestPart("hero") hero: MultipartFile,
        jwt: PreAuthenticatedAuthenticationToken
    ): ResponseEntity<Any> {
        val (email, _) = jwt.readToken()
        return platformRequest
            .validate()
            .flatMap { validatedRequest ->
                service.savePlatform(validatedRequest, email).right()
            }
            .flatMap { platform ->
                imageService.uploadLogo(logo, platform.id)
                    .map { platform }
            }
            .flatMap { platform ->
                imageService.uploadHero(hero, platform.id)
                    .map { platform }
            }
            .fold(
                { handleFailure(it) },
                { ResponseEntity.status(HttpStatus.CREATED).body(it.toResponse()) }
            )
    }

    @DeleteMapping("/{id}")
    fun deletePlatform(
        @PathVariable id: UUID,
        jwt: PreAuthenticatedAuthenticationToken
    ) {
        val (email, _) = jwt.readToken()

        service.deletePlatform(id, email)
            .fold(
                { handleFailure(it) },
                { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }
            )
    }

    private fun handleFailure(failure: Failure): ResponseEntity<Any> =
        when (failure) {
            is PlatformFailure.NotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            else -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
}