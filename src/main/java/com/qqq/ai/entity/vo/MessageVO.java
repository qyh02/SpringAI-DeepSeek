package com.qqq.ai.entity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.Message;

/**
 * @author qqq
 * @description
 * @createDate 2026/6/1 19:56
 */
@NoArgsConstructor
@Data
public class MessageVO {
    private String role;
    private String content;

    public MessageVO(Message message) {
        switch (message.getMessageType()) {
            case USER -> role = "user";
            case ASSISTANT -> role = "assistant";
            case SYSTEM -> role = "system";
            case TOOL -> role = "function";
            default -> role = "unknown";
        }
        this.content = message.getText();
    }
}
