package com.qqq.ai.embedding;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class CustomEmbeddingApiResponse {

    private List<Item> data;


    @Data
    public static class Item {
        private Integer index;
        private float[] embedding;

    }
}