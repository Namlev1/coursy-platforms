package com.coursy.masterservice.controller

import com.coursy.masterservice.dto.LoginRequest
import com.coursy.masterservice.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): Mono<ResponseEntity<Any>> {
        return authService.loginUser(request)
            .map { either ->
                either.fold(
                    { errorMsg -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMsg) },
                    { jwtResponse -> ResponseEntity.ok(jwtResponse) }
                )
            }
    }
}