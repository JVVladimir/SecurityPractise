package com.jvvladimir.securitypractise

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(stubs = ["/csrf/post"])
class TestControllerTest {

    @Test
    fun `test csrf attack`() {
        stubFor(
            get(urlEqualTo("/csrf/post"))
                .willReturn(
                    aResponse().withStatus(403)
                )
        )

        assertThat(testClient.get("/some/thing").statusCode(), `is`(200))
    }
}