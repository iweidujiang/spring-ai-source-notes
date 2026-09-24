package io.github.iweidujiang.springaisourcenotes.tool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * 供 Tool Calling 阅读用的本地工具。方法被模型选中后，由 {@code ToolCallingManager} 反射调用。
 */
@Component
public class DemoTools {

    private static final Logger log = LoggerFactory.getLogger(DemoTools.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Tool(description = "返回当前本地日期时间，格式为 yyyy-MM-dd HH:mm:ss")
    public String currentDateTime() {
        String now = LocalDateTime.now().format(FORMATTER);
        log.info("[tool] currentDateTime -> {}", now);
        return now;
    }

    @Tool(description = "计算两个整数的和")
    public int add(
            @ToolParam(description = "第一个加数") int a,
            @ToolParam(description = "第二个加数") int b) {
        int sum = a + b;
        log.info("[tool] add a={} b={} -> {}", a, b, sum);
        return sum;
    }
}
