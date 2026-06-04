package com.qqq.ai.embedding;

import com.qqq.ai.config.CustomEmbeddingProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingOptions;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CustomEmbeddingModel implements EmbeddingModel {

    private final RestClient restClient;
    private final CustomEmbeddingProperties properties;

    @Override
    public EmbeddingResponse call(EmbeddingRequest request) {
        Assert.notNull(request, "EmbeddingRequest must not be null");
        Assert.notEmpty(request.getInstructions(), "Embedding input must not be empty");

        CustomEmbeddingRequest body = new CustomEmbeddingRequest();
        body.setModel(properties.getModel());
        body.setInput(request.getInstructions());
        body.setDimensions(properties.getDimensions());

        CustomEmbeddingApiResponse response = restClient.post()
                .uri("/v1/embeddings")
                .body(body)
                .retrieve()
                .body(CustomEmbeddingApiResponse.class);

        if (response == null || response.getData() == null || response.getData().isEmpty()) {
            throw new IllegalStateException("Embedding response is empty");
        }

        List<Embedding> embeddings = new ArrayList<>();
        for (CustomEmbeddingApiResponse.Item item : response.getData()) {
            embeddings.add(new Embedding(item.getEmbedding(), item.getIndex()));
        }

        return new EmbeddingResponse(embeddings);
    }

    @Override
    public float[] embed(Document document) {
        return this.embed(document.getText());
    }


    public EmbeddingResponse call(List<String> texts) {
        return this.call(new EmbeddingRequest(texts, EmbeddingOptionsBuilder.build()));
    }

    private static class EmbeddingOptionsBuilder {
        static EmbeddingOptions build() {
            return EmbeddingOptions.builder().build();
        }
    }
}