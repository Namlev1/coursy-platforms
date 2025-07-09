package com.coursy.platforms.controller

import com.coursy.platforms.repository.PlatformRepository
import com.coursy.platforms.service.PlatformService
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.optional.shouldNotBePresent
import jakarta.transaction.Transactional
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AdminPlatformControllerTest(
    private val mockMvc: MockMvc,
    private val platformService: PlatformService,
    private val platformRepo: PlatformRepository
) : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)
    val fixtures = ControllerTestFixtures()

    val adminUrl = fixtures.adminUrl
    val testEmail = fixtures.testEmail
    val testName = fixtures.testName
    val testPlatform = fixtures.testPlatformRequest

    init {
        describe("Authorization") {
            it("should not allow unauthorized access") {
                mockMvc.get(adminUrl)
                    .andExpect { status { isForbidden() } }
            }

            it("should not allow authorized user") {
                fixtures.authorizeNextRequest(isAdmin = false)
                mockMvc.get(adminUrl)
                    .andExpect { status { isForbidden() } }
            }

            it("should allow authorized admin") {
                fixtures.authorizeNextRequest(isAdmin = true)
                mockMvc.get(adminUrl)
                    .andExpect { status { isOk() } }

            }
        }

        describe("Get all platforms") {
            it("should return non-empty list") {
                platformService.savePlatform(testPlatform, testEmail)
                fixtures.authorizeNextRequest()

                mockMvc.get(adminUrl)
                    .andExpect {
                        status { isOk() }
                        jsonPath("$.[0].name") { value(testName.value) }
                    }
            }

            it("should return empty list") {
                fixtures.authorizeNextRequest()

                mockMvc.get(adminUrl)
                    .andExpect {
                        status { isOk() }
                        jsonPath("$") { isEmpty() }
                    }
            }
        }

        describe("Get platform by id") {
            it("should return platform response") {
                platformService.savePlatform(testPlatform, testEmail)
                val id = platformRepo.getByUserEmail(testEmail.value)[0].id
                fixtures.authorizeNextRequest()

                mockMvc.get("$adminUrl/$id")
                    .andExpect {
                        status { isOk() }
                        jsonPath("$.name") { value(testName.value) }
                        jsonPath("$.id") { value(id.toString()) }
                    }
            }

            it("should return not found") {
                fixtures.authorizeNextRequest()

                mockMvc.get("$adminUrl/${UUID.randomUUID()}")
                    .andExpect {
                        status { isNotFound() }
                    }
            }
        }

        describe("Delete platform") {
            it("should delete platform by ID") {
                platformService.savePlatform(testPlatform, testEmail)
                val id = platformRepo.getByUserEmail(testEmail.value)[0].id
                fixtures.authorizeNextRequest()

                mockMvc.delete("$adminUrl/$id")
                    .andExpect {
                        status { isNoContent() }
                    }

                platformRepo.findById(id!!).shouldNotBePresent()
            }
        }
    }

}