package com.coursy.be

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class HomeController {
    @GetMapping
    fun helloWorld(): String {
        return "Hello world!"
    }
}