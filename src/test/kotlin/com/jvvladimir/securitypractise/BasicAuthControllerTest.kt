package com.jvvladimir.securitypractise

import com.github.tomakehurst.wiremock.client.WireMock.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*


@WebMvcTest(controllers = [TestController::class])
@AutoConfigureMockMvc
@TestPropertySource(properties = ["spring.security.user.password=pass", "spring.security.user.name=user", "security.enable-csrf=false"])
class BasicAuthControllerTest {

    @Autowired
    private lateinit var client: MockMvc

    /**
     * POST запрос без аутентификации
     *
     * */
    @Test
    fun `basic auth filter returns 401`() {
        val builder = MockMvcRequestBuilders.post("/csrf/post")

        client.perform(builder).andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    /**
     * POST запрос без аутентификации
     *
     * */
    @Test
    fun `basic auth filter returns 200`() {
        val builder = MockMvcRequestBuilders
            .post("/csrf/post")
            .header("Authorization", "Basic ${Base64.getEncoder().encodeToString("vova2:pass".toByteArray())}")

        client.perform(builder).andExpect(MockMvcResultMatchers.status().isOk)
    }
}