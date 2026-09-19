package io.github.iweidujiang.springaisourcenotes.advisor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Advisor 链阅读入口。原先的 {@code /chat} 保持不动。
 */
@RestController
public class AdvisorChainController {

    private final ChatClient chatClient;

    public AdvisorChainController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * 书写顺序是 late(100) 再 early(10)。日志应是 early 先 before、late 后 before，返回时相反。
     */
    @GetMapping("/chat/advisors")
    public String advisors(@RequestParam(defaultValue = "用一句话介绍 Advisor") String q) {
        return this.chatClient.prompt()
                .advisors(new TraceAdvisor("late", 100), new TraceAdvisor("early", 10))
                .user(q)
                .call()
                .content();
    }

    /**
     * BlockingAdvisor 不调用 nextCall。控制台不应出现 TraceAdvisor 的 before/after，也不会打到模型。
     * 响应体为 null（没有 ChatResponse）。
     */
    @GetMapping("/chat/advisors/block")
    public String block(@RequestParam(defaultValue = "这句话不应到达模型") String q) {
        return this.chatClient.prompt()
                .advisors(new BlockingAdvisor(), new TraceAdvisor("should-not-run", 50))
                .user(q)
                .call()
                .content();
    }
}
