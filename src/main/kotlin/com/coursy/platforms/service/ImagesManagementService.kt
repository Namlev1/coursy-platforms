package com.coursy.platforms.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.coursy.platforms.failure.Failure
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

@Service
class ImagesManagementService(
    private val minIoService: MinIOService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun uploadLogo(
        file: MultipartFile,
        platformId: UUID,
    ): Either<Failure, Unit> = uploadImageFromMultipart(file, platformId, "logo")

    fun uploadHero(
        file: MultipartFile,
        platformId: UUID,
    ): Either<Failure, Unit> = uploadImageFromMultipart(file, platformId, "hero")

    fun getLogo(
        platformId: UUID,
    ): Either<Failure, InputStream> = downloadImage(platformId, "logo")

    fun getHero(
        platformId: UUID,
    ): Either<Failure, InputStream> = downloadImage(platformId, "hero")

    private fun uploadImageFromMultipart(
        file: MultipartFile, platformId: UUID, imageType: String
    ): Either<Failure, Unit> {
        val tempFile = Files.createTempFile(imageType, ".jpg")
        return try {
            file.transferTo(tempFile)
            uploadImage(tempFile, platformId, imageType)
        } finally {
            Files.deleteIfExists(tempFile)
        }
    }

    private fun uploadImage(
        inputFile: Path, platformId: UUID, imageType: String
    ): Either<Failure, Unit> {
        return Files.newInputStream(inputFile).use { inputStream ->
            minIoService.uploadFile(
                "$platformId/$imageType.jpg", inputStream, "image/jpeg", Files.size(inputFile)
            ).fold({ failure ->
                logger.error("Failed to upload $imageType: ${failure.message()}")
                failure.left()
            }, { Unit.right() })
        }
    }

    private fun downloadImage(
        platformId: UUID,
        imageType: String
    ): Either<Failure, InputStream> {
        return minIoService.getFileStream("$platformId/$imageType.jpg")
            .mapLeft { failure ->
                logger.error("Failed to download $imageType: ${failure.message()}")
                failure
            }
    }
}
