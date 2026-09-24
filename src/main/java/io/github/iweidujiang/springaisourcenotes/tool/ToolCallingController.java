package io.github.iweidujiang.springaisourcenotes.tool;

import io.github.iweidujiang.springaisourcenotes.advisor.TraceAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Tool Calling 阅读入口。
 * <p>
 * 链上额外挂一个 order=50 的 TraceAdvisor，落在 ToolCallingAdvisor（默认约 HIGHEST+300）
 * 与 ChatModelCallAdvisor（LOWEST）之间，便于在日志里看到：工具循环每一轮都会再次进入模型。
 */
@RestController
public class ToolCallingController {

    private final ChatClient chatClient;

    private final DemoTools demoTools;

    public ToolCallingController(ChatClient.Builder chatClientBuilder, DemoTools demoTools) {
        this.chatClient = chatClientBuilder.build();
        this.demoTools = demoTools;
    }

    /**
     * 期望模型调用 currentDateTime，再据此组织自然语言回答。
     * 控制台应出现 [tool] currentDateTime，以及 TraceAdvisor 多轮 before/after（至少两轮进模型）。
     */
    @GetMapping("/chat/tools")
    public String tools(
            @RequestParam(defaultValue = "现在几点了？请调用工具查询当前时间后再回答") String q) {
        return this.chatClient.prompt()
                .advisors(new TraceAdvisor("between-tool-and-model", 50))
                .tools(this.demoTools)
                .user(q)
                .call()
                .content();
    }

    /**
     * 期望模型调用 add，再回答计算结果。
     */
    @GetMapping("/chat/tools/add")
    public String add(
            @RequestParam(defaultValue = "请用工具计算 17 加 25 等于多少，只根据工具结果回答") String q) {
        return this.chatClient.prompt()
                .advisors(new TraceAdvisor("between-tool-and-model", 50))
                .tools(this.demoTools)
                .user(q)
                .call()
                .content();
    }
}
