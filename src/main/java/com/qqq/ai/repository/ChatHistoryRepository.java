package com.qqq.ai.repository;

import java.util.List;

/**
 * @author qqq
 * @description
 * @createDate 2026/6/1 17:19
 */
public interface ChatHistoryRepository {

    /**
     * 保存会话记录
     * @param type 业务类型 如：chat,service,pdf
     * @param chatId 会话id
     */
    void save(String type, String chatId);

    /**
     * 获取所有会话id
     * @param type 业务类型 如：chat,service,pdf
     * @return 会话id列表
     */
    List<String> getChatIds(String type);
}
