package com.jvvladimir.securitypractise

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController {

    @PostMapping("csrf/post")
    fun testPost(): String {
        return "Hello it is post request"
    }

    @GetMapping("csrf/get")
    fun testGet(): String {
        return "Hello it is post request"
    }

    @GetMapping("css/get")
    fun testGetCss(): String {
        return "Hello it is post request"
    }
}