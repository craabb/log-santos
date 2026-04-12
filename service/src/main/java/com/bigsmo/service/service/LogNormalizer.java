package com.bigsmo.service.service;

import com.bigsmo.common.dto.IncomingLogDto;
import com.bigsmo.common.model.NormalizedLogEvent;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;

@Service
public class LogNormalizer {

    public NormalizedLogEvent normalize(IncomingLogDto dto, String clientIp) {
        NormalizedLogEvent event = NormalizedLogEvent.fromDto(dto, clientIp);
        event.setTimestamp(parseTimestamp(dto.getTimestamp()));
        event.setNormalizedAt(Instant.now());
        return event;
    }

    private Instant parseTimestamp(String raw) {
        if (raw == null || raw.isBlank()) return Instant.now();
        try { return Instant.parse(raw); }
        catch (DateTimeParseException e) {
            try { return Instant.ofEpochMilli(Long.parseLong(raw)); }
            catch (NumberFormatException ex) { return Instant.now(); }
        }
    }
}