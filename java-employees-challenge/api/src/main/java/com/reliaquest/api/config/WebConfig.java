package com.reliaquest.api.config;

import com.reliaquest.api.constants.ApiConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {
    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return createWebClient(webClientBuilder);
    }

    private WebClient createWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(ApiConstants.BASE_API_ENDPOINT)
                .defaultCookie("cookie-name", "cookie-value")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
