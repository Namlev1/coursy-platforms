package com.coursy.be.service

import com.coursy.be.model.platform.Platform
import com.coursy.be.model.platform.PlatformFailure
import com.coursy.be.repository.PlatformRepository
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.repository.findByIdOrNull

class PlatformServiceTest : DescribeSpec({
    val repository: PlatformRepository = mockk(relaxed = true)
    val service = PlatformService(repository)

    describe("PlatformService") {

        describe("Get all platforms") {
            it("should call repository") {
                // when
                service.getAllPlatforms()

                // then
                verify(exactly = 1) { repository.findAll() }
            }

            it("should retrieve all platforms if present") {
                // given
                val p1 = Platform(1L, "Platform 1")
                val p2 = Platform(2L, "Platform 2")
                every { repository.findAll() } returns listOf(p1, p2)

                // when
                val result = service.getAllPlatforms()

                // then
                result shouldHaveSize 2
                result shouldContainExactly listOf(p1, p2)
            }

            it("should return empty list if no platforms are present") {
                // given
                every { repository.findAll() } returns emptyList()

                // when
                val result = service.getAllPlatforms()

                // then
                result.shouldBeEmpty()
            }
        }

        describe("Get platform by id") {
            it("should return Left with NotFound if invalid id") {
                // given
                val id = 1L
                every { repository.findByIdOrNull(id) } returns null

                // when
                val result = service.getById(id)

                // then
                result.shouldBeLeft(PlatformFailure.NotFound(id))
            }

            it("should return Right with Platform if valid id") {
                // given
                val id = 1L
                val platform = Platform(id, "Platform 1")
                every { repository.findByIdOrNull(id) } returns platform

                // when
                val result = service.getById(id)

                // then
                result.isRight() shouldBe true
                result.getOrNull() shouldBe platform
            }
        }

        describe("Save platform") {
            it("should call repository") {
                // given
                val platform = Platform(1L, "Test platform")
                every { repository.save(platform) } returns platform

                // when
                service.savePlatform(platform)

                // then
                verify(exactly = 1) { repository.save(platform) }
            }

            it("should save platform and return it") {
                // given
                val platform = Platform(1L, "Test platform")
                every { repository.save(platform) } returns platform

                // when
                val result = service.savePlatform(platform)

                // then
                result shouldBe platform
            }
        }

        describe("Delete platform") {
            it("should call repository") {
                // given
                val id = 1L
                every { repository.deleteById(id) } returns Unit

                // when
                service.deletePlatform(id)

                // then
                verify(exactly = 1) { repository.deleteById(id) }
            }
        }
    }
})