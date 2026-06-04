package com.qqq.ai.config;

import com.qqq.ai.constants.SystemConstants;
import com.qqq.ai.tools.CourseTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qqq
 * @description
 * @createDate 2026/6/1 12:02
 */
@Configuration
public class CommonConfiguration {

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().build();
    }

    @Bean
    public ChatClient chatClient(DeepSeekChatModel deepSeekChatModel, ChatMemory chatMemory) {
        return ChatClient
                .builder(deepSeekChatModel)
                .defaultSystem("你叫高手，你是一个专业的助手")
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .build();
    }

    @Bean
    public ChatClient gameChatClient(DeepSeekChatModel deepSeekChatModel, ChatMemory chatMemory) {
        return ChatClient
                .builder(deepSeekChatModel)
                .defaultSystem(SystemConstants.GAME_SYSTEM_PROMPT)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .build();
    }

    @Bean
    public ChatClient serviceChatClient(DeepSeekChatModel deepSeekChatModel, ChatMemory chatMemory, CourseTools courseTools) {
        return ChatClient
                .builder(deepSeekChatModel)
                .defaultSystem(SystemConstants.SERVICE_SYSTEM_PROMPT)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .defaultTools(courseTools)
                .build();
    }

    @Bean
    public ChatClient pdfChatClient(DeepSeekChatModel deepSeekChatModel, ChatMemory chatMemory, VectorStore vectorStore) {
        return ChatClient
                .builder(deepSeekChatModel)
                .defaultSystem("请根据提供的上下文回答问题，不要自己猜测。")
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(SearchRequest.builder()
                                        .similarityThreshold(0.6)
                                        .topK(2)
                                        .build())
                                .build()
                )
                .build();
    }

    /**
     * 向量存储
     * @param embeddingModel 嵌入向量模型
     * @return 本地向量存储数据库
     */
       @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }
}
