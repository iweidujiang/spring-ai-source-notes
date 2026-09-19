package io.github.iweidujiang.springaisourcenotes.advisor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.core.Ordered;

/**
 * 故意不调用 {@code nextCall} 的 Advisor。
 * <p>
 * order 取最高优先级，保证它先于 {@link TraceAdvisor} 和 {@code ChatModelCallAdvisor} 执行。
 * 若不调用 {@code nextCall}，后面的 Advisor 和模型都不会跑。
 */
public final class BlockingAdvisor implements CallAdvisor {

    private static final Logger log = LoggerFactory.getLogger(BlockingAdvisor.class);

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        log.warn("[block] nextCall not invoked, remaining advisors and ChatModel are skipped");
        return ChatClientResponse.builder()
                .context(Map.copyOf(request.context()))
                .build();
    }

    @Override
    public String getName() {
        return "blocking";
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
