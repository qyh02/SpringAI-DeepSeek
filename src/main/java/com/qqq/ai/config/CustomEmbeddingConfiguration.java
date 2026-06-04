package com.qqq.ai.config;

import com.qqq.ai.embedding.CustomEmbeddingModel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(CustomEmbeddingProperties.class)
public class CustomEmbeddingConfiguration {

    @Bean
    public RestClient customEmbeddingRestClient(CustomEmbeddingProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeaders(headers -> headers.setBearerAuth(properties.getApiKey()))
                .build();
    }

    @Bean
    public CustomEmbeddingModel customEmbeddingModel(
            RestClient customEmbeddingRestClient,
            CustomEmbeddingProperties properties
    ) {
        return new CustomEmbeddingModel(customEmbeddingRestClient, properties);
    }
}