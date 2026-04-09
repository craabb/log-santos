package org.example.logsantos.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.logsantos.model.NormalizedLogEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogIngestService {
    private final ArrayBlockingQueue<NormalizedLogEvent> logQueue;

    public IngestResult ingest(List<NormalizedLogEvent> events) {
        int accepted = 0;
        int dropped = 0;

        for (NormalizedLogEvent event : events) {
            if (logQueue.offer(event)) {
                accepted++;
            } else {
                dropped++;
                log.debug("Queue is full. Dropping log event for service: {}", event.getServiceId());
            }
        }
        return new IngestResult(accepted, dropped);
    }

    public record IngestResult(int accepted, int dropped) {}
}