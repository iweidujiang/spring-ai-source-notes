package io.github.iweidujiang.springaisourcenotes.chatclient;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 从 {@code ChatClient.prompt().call()} 单步进入源码。
 * <p>
 * 阅读时在 IDEA 对 {@code call()} 下方法断点，沿 Advisor 链走到 {@code ChatModel}。
 */
@RestController
public class ChatClientCallController {

    private final ChatClient chatClient;

    public ChatClientCallController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(defaultValue = "用一句话介绍 Spring AI 的 ChatClient") String q) {
        return this.chatClient.prompt()
                .user(q)
                .call()
                .content();
    }
}
