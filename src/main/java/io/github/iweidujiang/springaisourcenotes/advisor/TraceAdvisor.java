package io.github.iweidujiang.springaisourcenotes.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;

/**
 * 环绕日志 Advisor：调用 {@code nextCall} 前后各打一行，用来观察执行顺序。
 * <p>
 * 注册时故意把 order 大的写在前面，日志若仍是 order 小的先 before、后 after，
 * 说明链按 {@code getOrder()} 重排，而不是按代码书写顺序。
 */
public final class TraceAdvisor implements CallAdvisor {

    private static final Logger log = LoggerFactory.getLogger(TraceAdvisor.class);

    private final String advisorName;

    private final int order;

    public TraceAdvisor(String advisorName, int order) {
        this.advisorName = advisorName;
        this.order = order;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        log.info("[before] name={} order={}", this.advisorName, this.order);
        ChatClientResponse response = chain.nextCall(request);
        log.info("[after]  name={} order={}", this.advisorName, this.order);
        return response;
    }

    @Override
    public String getName() {
        return this.advisorName;
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
