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


@WebMvcTest(controllers = [TestController::class])
@AutoConfigureMockMvc
@TestPropertySource(properties = ["spring.security.user.password=pass", "spring.security.user.name=user", "security.enable-csrf=true"])
class CSRFControllerTest {

    @Autowired
    private lateinit var client: MockMvc

    /**
     * POST запрос требует scrf токен для успешного исполнения
     *
     * */
    @Test
    fun `csrf filter returns 403`() {
        val builder = MockMvcRequestBuilders.post("/csrf/post")

        client.perform(builder).andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    /**
     * POST запрос требует scrf токен для успешного исполнения
     *
     * */
    @Test
    fun `csrf filter returns 200`() {
        val builder = MockMvcRequestBuilders
            .post("/csrf/post")
            .header("X-CSRF-TOKEN", "")

        client.perform(builder).andExpect(MockMvcResultMatchers.status().isOk)
    }

    /**
     * GET запрос не требует scrf токен для успешного исполнения, т.к. GET не модифицирует данные
     *
     * */
    @Test
    fun `csrf filter returns 401`() {
        val builder = MockMvcRequestBuilders.get("/csrf/get")

        client.perform(builder).andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }
}