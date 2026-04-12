package com.bigsmo.controller.service;

import com.bigsmo.common.model.NormalizedLogEvent;

import java.util.List;

public interface LogIngestService {
    public IngestResult ingest(List<String> events);

    public record IngestResult(int accepted, int dropped) {}
}