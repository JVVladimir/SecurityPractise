package com.jvvladimir.securitypractise

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController {

    @PostMapping("csrf/post")
    fun testPost(): String {
        return "Hello it is post request"
    }
}