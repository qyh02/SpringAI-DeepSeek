package com.qqq.ai.controller;

import com.qqq.ai.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

/**
 * @author qqq
 * @description
 * @createDate 2026/6/1 12:10
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class GametController {

    private final ChatClient gameChatClient;

    @RequestMapping(value = "/game",produces = "text/html;charset=utf-8")
    public Flux<String> send(String prompt, String chatId) {
        return gameChatClient.prompt()
                .user(prompt)
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, chatId))
                .stream()
                .content();
    }
}
