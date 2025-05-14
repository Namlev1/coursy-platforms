package com.coursy.masterservice.controller

import com.coursy.masterservice.security.UserDetailsImp
import com.coursy.masterservice.service.PlatformService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/platform")
class UserPlatformController(val service: PlatformService) {

    @GetMapping
    fun getAllPlatforms(@AuthenticationPrincipal user: UserDetailsImp) =
        service.getByUserEmail(user.email)

}