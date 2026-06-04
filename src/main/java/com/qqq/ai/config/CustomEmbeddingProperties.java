package com.qqq.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "custom.embedding")
public class CustomEmbeddingProperties {

    private String apiKey;
    private String baseUrl;
    private String model;
    private Integer dimensions;

}