package io.github.iweidujiang.springaisourcenotes.observation;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 把 Spring AI 各层 Observation 的起止打到控制台，方便对照 ChatClient / Advisor / ChatModel / Tool。
 */
@Component
public class LayerLoggingObservationHandler implements ObservationHandler<Observation.Context> {

    private static final Logger log = LoggerFactory.getLogger(LayerLoggingObservationHandler.class);

    @Override
    public boolean supportsContext(Observation.Context context) {
        String type = context.getClass().getName();
        return type.startsWith("org.springframework.ai.");
    }

    @Override
    public void onStart(Observation.Context context) {
        log.info("[obs:start] kind={} name={}", shortKind(context), context.getName());
    }

    @Override
    public void onStop(Observation.Context context) {
        log.info("[obs:stop]  kind={} name={} error={}", shortKind(context), context.getName(),
                context.getError() != null ? context.getError().toString() : "none");
    }

    private static String shortKind(Observation.Context context) {
        String simple = context.getClass().getSimpleName();
        if (simple.endsWith("ObservationContext")) {
            return simple.substring(0, simple.length() - "ObservationContext".length());
        }
        return simple;
    }
}
