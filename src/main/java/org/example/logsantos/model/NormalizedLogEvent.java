package org.example.logsantos.model;

import org.example.logsantos.dto.IncomingLogDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NormalizedLogEvent {
    private String eventId;
    private String serviceId;
    private Instant timestamp;
    private String level;
    private String message;
    private String traceId;
    private String spanId;
    private Map<String, Object> attrs;
    private Instant ingestedAt;
    private String sourceIp;
    private Instant normalizedAt;

    public static NormalizedLogEvent fromDto(IncomingLogDto dto, String sourceIp) {
        return NormalizedLogEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .serviceId(dto.getServiceId())
                .timestamp(null)
                .level(dto.getLevel())
                .message(dto.getMessage())
                .traceId(dto.getTraceId())
                .spanId(dto.getSpanId())
                .attrs(dto.getAttrs())
                .ingestedAt(Instant.now())
                .sourceIp(sourceIp)
                .normalizedAt(null)
                .build();
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "event_id", eventId,
                "service_id", serviceId,
                "@timestamp", timestamp,
                "level", level,
                "message", message,
                "trace_id", traceId,
                "span_id", spanId,
                "attrs", attrs != null ? attrs : Map.of(),
                "ingested_at", ingestedAt
        );
    }
}