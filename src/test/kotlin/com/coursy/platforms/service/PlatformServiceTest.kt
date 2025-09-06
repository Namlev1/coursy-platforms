//package com.coursy.platforms.service
//
//import com.coursy.platforms.dto.PlatformRequest
//import com.coursy.platforms.dto.toResponse
//import com.coursy.platforms.failure.PlatformFailure
//import com.coursy.platforms.model.Platform
//import com.coursy.platforms.repository.PlatformRepository
//import com.coursy.platforms.types.Description
//import com.coursy.platforms.types.Email
//import com.coursy.platforms.types.Name
//import io.kotest.assertions.arrow.core.shouldBeLeft
//import io.kotest.assertions.arrow.core.shouldBeRight
//import io.kotest.core.spec.style.DescribeSpec
//import io.kotest.matchers.shouldBe
//import io.kotest.matchers.types.shouldBeInstanceOf
//import io.mockk.*
//import org.springframework.data.repository.findByIdOrNull
//import java.util.*
//
//class PlatformServiceTest : DescribeSpec({
//
//    class TestFixtures {
//        // Common values
//        val platformId = UUID.randomUUID()
//        val nonExistentId = UUID.randomUUID()
//
//        // Platform data
//        val platformName = "Test Platform"
//        val platformDescription = "Test Description"
//        val userEmail = Email.create("test@example.com").getOrNull()!!
//        val validName = Name.create(platformName).getOrNull()!!
//        val validDescription = Description.create(platformDescription).getOrNull()!!
//
//        // Request objects
//        val validPlatformRequest by lazy {
//            PlatformRequest.Validated(
//                name = validName,
//                description = validDescription
//            )
//        }
//
//        // Platform object
//        fun createPlatform(
//            id: UUID = platformId,
//            name: String = platformName,
//            description: String = platformDescription,
//            userEmail: String = this.userEmail.value
//        ) = Platform(
//            id = id,
//            name = name,
//            description = description,
//            userEmail = userEmail
//        )
//    }
//
//    // Mocks
//    val platformRepository = mockk<PlatformRepository>()
//
//    // System under test
//    val platformService = PlatformService(platformRepository)
//
//    val fixtures = TestFixtures()
//
//    beforeTest {
//        clearAllMocks()
//    }
//
//    afterTest {
//        unmockkAll()
//    }
//
//    describe("PlatformService") {
//
//        describe("Get All Platforms") {
//            it("should return a list of all platforms") {
//                // given
//                val platforms = listOf(fixtures.createPlatform(), fixtures.createPlatform(id = UUID.randomUUID()))
//                val expectedResponses = platforms.map { it.toResponse() }
//
//                every { platformRepository.findAll() } returns platforms
//
//                // when
//                val result = platformService.getAllPlatforms()
//
//                // then
//                result shouldBe expectedResponses
//                verify(exactly = 1) { platformRepository.findAll() }
//            }
//
//            it("should return an empty list when no platforms exist") {
//                // given
//                every { platformRepository.findAll() } returns emptyList()
//
//                // when
//                val result = platformService.getAllPlatforms()
//
//                // then
//                result shouldBe emptyList()
//                verify(exactly = 1) { platformRepository.findAll() }
//            }
//        }
//
//        describe("Save Platform") {
//            it("should save a platform successfully") {
//                // given
//                val request = fixtures.validPlatformRequest
//                val userEmail = fixtures.userEmail
//                val savedPlatform = fixtures.createPlatform()
//
//                every {
//                    platformRepository.save(any())
//                } returns savedPlatform
//
//                // when
//                val result = platformService.savePlatform(request, userEmail)
//
//                // then
//                result.shouldBeRight()
//                verify(exactly = 1) {
//                    platformRepository.save(match {
//                        it.name == fixtures.validName.value &&
//                                it.description == fixtures.validDescription.value &&
//                                it.userEmail == fixtures.userEmail.value
//                    })
//                }
//            }
//        }
//
//        describe("Delete Platform") {
//            it("should delete platform successfully") {
//                // given
//                val platformId = fixtures.platformId
//                every { platformRepository.deleteById(platformId) } just runs
//
//                // when
//                platformService.deletePlatform(platformId)
//
//                // then
//                verify(exactly = 1) { platformRepository.deleteById(platformId) }
//            }
//        }
//
//        describe("Get Platform By ID") {
//            context("when retrieving an existing platform") {
//                it("should retrieve platform successfully") {
//                    // given
//                    val platformId = fixtures.platformId
//                    val platform = fixtures.createPlatform()
//                    val expectedResponse = platform.toResponse()
//
//                    every { platformRepository.findByIdOrNull(platformId) } returns platform
//
//                    // when
//                    val result = platformService.getById(platformId)
//
//                    // then
//                    val response = result.shouldBeRight()
//                    response shouldBe expectedResponse
//
//                    verify(exactly = 1) { platformRepository.findByIdOrNull(platformId) }
//                }
//            }
//
//            context("when retrieving a non-existent platform") {
//                it("should return NotFound failure") {
//                    // given
//                    val nonExistentId = fixtures.nonExistentId
//                    every { platformRepository.findByIdOrNull(nonExistentId) } returns null
//
//                    // when
//                    val result = platformService.getById(nonExistentId)
//
//                    // then
//                    val failure = result.shouldBeLeft()
//                    failure.shouldBeInstanceOf<PlatformFailure.NotFound>()
//
//                    verify(exactly = 1) { platformRepository.findByIdOrNull(nonExistentId) }
//                }
//            }
//        }
//
//        describe("Get Platforms By User Email") {
//            it("should return platforms for the user") {
//                // given
//                val userEmail = fixtures.userEmail
//                val platforms = listOf(fixtures.createPlatform(), fixtures.createPlatform(id = UUID.randomUUID()))
//                val expectedResponses = platforms.map { it.toResponse() }
//
//                every { platformRepository.getByUserEmail(userEmail.value) } returns platforms
//
//                // when
//                val result = platformService.getByUserEmail(userEmail)
//
//                // then
//                result shouldBe expectedResponses
//                verify(exactly = 1) { platformRepository.getByUserEmail(userEmail.value) }
//            }
//
//            it("should return an empty list when user has no platforms") {
//                // given
//                val userEmail = fixtures.userEmail
//                every { platformRepository.getByUserEmail(userEmail.value) } returns emptyList()
//
//                // when
//                val result = platformService.getByUserEmail(userEmail)
//
//                // then
//                result shouldBe emptyList()
//                verify(exactly = 1) { platformRepository.getByUserEmail(userEmail.value) }
//            }
//        }
//    }
//})