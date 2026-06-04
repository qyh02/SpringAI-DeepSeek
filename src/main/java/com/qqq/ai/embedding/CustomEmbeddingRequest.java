package com.qqq.ai.embedding;

import lombok.Data;

import java.util.List;

@Data
public class CustomEmbeddingRequest {

    private String model;
    private List<String> input;
    private Integer dimensions;

}