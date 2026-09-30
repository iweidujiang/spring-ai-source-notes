package io.github.iweidujiang.springaisourcenotes.observation;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Observation / Metrics 阅读探针。先打 {@code /chat} 或 {@code /chat/tools}，再看本接口。
 */
@RestController
@RequestMapping("/observation")
public class ObservationProbeController {

    private final MeterRegistry meterRegistry;

    public ObservationProbeController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    /**
     * 列出名称里带 gen_ai / spring.ai 的 meter，对应 ChatModel / ChatClient 等观测约定。
     */
    @GetMapping("/meters")
    public List<Map<String, Object>> meters() {
        return this.meterRegistry.getMeters()
                .stream()
                .map(Meter::getId)
                .filter(id -> {
                    String name = id.getName();
                    return name.contains("gen_ai") || name.contains("spring.ai");
                })
                .sorted(Comparator.comparing(Meter.Id::getName))
                .map(id -> {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("name", id.getName());
                    row.put("type", id.getType());
                    row.put("tags", id.getTags()
                            .stream()
                            .collect(Collectors.toMap(t -> t.getKey(), t -> t.getValue(), (a, b) -> a, LinkedHashMap::new)));
                    return row;
                })
                .toList();
    }
}
