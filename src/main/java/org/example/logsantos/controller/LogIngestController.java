package org.example.logsantos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.logsantos.dto.BatchResponse;
import org.example.logsantos.dto.LogBatchRequest;
import org.example.logsantos.model.NormalizedLogEvent;
import org.example.logsantos.service.LogIngestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/logs")
public class LogIngestController {
    private final LogIngestService logIngestService;

    @PostMapping("/batch")
    public ResponseEntity<BatchResponse> ingestBatch( @RequestBody LogBatchRequest request) {
        log.debug("Received batch request with {} logs",
                request.getLogs() != null ? request.getLogs().size() : 0);

        List<NormalizedLogEvent> events = request.getLogs().stream()
                .map(dto -> NormalizedLogEvent.fromDto(dto, "127.0.0.1"))
                .collect(Collectors.toList());

        var result = logIngestService.ingest(events);

        BatchResponse response = BatchResponse.builder()
                .status("accepted")
                .acceptedCount(result.accepted())
                .rejectedCount(0)
                .droppedCount(result.dropped())
                .build();

        log.debug("Ingested {} logs, dropped {} due to queue overflow", 
                result.accepted(), result.dropped());

        return ResponseEntity.accepted().body(response);
    }
}