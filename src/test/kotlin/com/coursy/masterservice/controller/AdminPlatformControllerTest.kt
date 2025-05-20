package com.coursy.masterservice.controller

import com.coursy.masterservice.dto.PlatformRequest
import com.coursy.masterservice.repository.PlatformRepository
import com.coursy.masterservice.security.RoleName
import com.coursy.masterservice.security.UserDetailsImp
import com.coursy.masterservice.service.PlatformService
import com.coursy.masterservice.types.Description
import com.coursy.masterservice.types.Email
import com.coursy.masterservice.types.Name
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.optional.shouldNotBePresent
import jakarta.transaction.Transactional
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get

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

    val adminUrl = "/v1/admin/platform"
    val testEmail = Email.create("test_email@email.com").getOrNull()!!
    val testName = Name.create("test name").getOrNull()!!
    val testDescription = Description.create("test description").getOrNull()!!
    val testPlatform = PlatformRequest.Validated(testName, testDescription)

    init {
        describe("Authorization") {
            it("should not allow unauthorized access") {
                mockMvc.get(adminUrl)
                    .andExpect { status { isForbidden() } }
            }

            it("should not allow authorized user") {
                authorizeNextRequest(isAdmin = false)
                mockMvc.get(adminUrl)
                    .andExpect { status { isForbidden() } }
            }

            it("should allow authorized admin") {
                authorizeNextRequest(isAdmin = true)
                mockMvc.get(adminUrl)
                    .andExpect { status { isOk() } }

            }
        }

        describe("Get all platforms") {
            it("should return non-empty list") {
                platformService.savePlatform(testPlatform, testEmail)
                authorizeNextRequest()

                mockMvc.get(adminUrl)
                    .andExpect {
                        status { isOk() }
                        jsonPath("$.[0].name") { value(testName.value) }
                    }
            }

            it("should return empty list") {
                authorizeNextRequest()

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
                authorizeNextRequest()

                mockMvc.get("$adminUrl/$id")
                    .andExpect {
                        status { isOk() }
                        jsonPath("$.name") { value(testName.value) }
                        jsonPath("$.id") { value(id) }
                    }
            }

            it("should return not found") {
                authorizeNextRequest()

                mockMvc.get("$adminUrl/1")
                    .andExpect {
                        status { isNotFound() }
                    }
            }
        }

        describe("Delete platform") {
            it("should delete platform by ID") {
                platformService.savePlatform(testPlatform, testEmail)
                val id = platformRepo.getByUserEmail(testEmail.value)[0].id
                authorizeNextRequest()

                mockMvc.delete("$adminUrl/$id")
                    .andExpect {
                        status { isNoContent() }
                    }

                platformRepo.findById(id!!).shouldNotBePresent()
            }
        }
    }

    fun authorizeNextRequest(isAdmin: Boolean = true) {
        val email = Email.create("aoe@aoeu.com").getOrNull()!!
        val roleName = if (isAdmin) RoleName.ROLE_ADMIN else RoleName.ROLE_USER
        val authorities = mutableListOf(SimpleGrantedAuthority(roleName.toString()))
        val userDetails = UserDetailsImp(email, authorities)

        val authentication = UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.authorities
        )

        SecurityContextHolder.getContext().authentication = authentication
    }
}