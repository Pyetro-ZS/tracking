package com.example.tracking.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tracking.entity.AccessLog;
import com.example.tracking.service.AccessLogService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class TrackingController {

    private final AccessLogService service;

    @Value("${app.redirect.url}")
    private String redirectUrl;

    public TrackingController(AccessLogService service) {
        this.service = service;
    }

    private static final String AUTH_TOKEN = "admin123";

    private boolean isAuthorized(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }
        String token = authHeader.substring(7);
        return AUTH_TOKEN.equals(token);
    }

    @GetMapping("/logs")
    public ResponseEntity<?> getAllLogs(
            @RequestParam(required = false) String qrId,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (!isAuthorized(authHeader)) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }
        if (qrId != null) {
            return ResponseEntity.ok(service.findByQrId(qrId));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/track")
    public ResponseEntity<Void> track(
            @RequestParam String qrId,
            HttpServletRequest request
    ) {
        String ip = extractClientIp(request);
        String referer = request.getHeader("Referer");
        service.save(
                qrId,
                request.getHeader("User-Agent"),
                ip,
                referer
        );
        return ResponseEntity.status(302)
                .header("Location", redirectUrl)
                .build();
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (!isAuthorized(authHeader)) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", service.countAll());

        // Quantidade por qrId
        Map<String, Long> byQrId = new HashMap<>();
        for (AccessLog log : service.findAll()) {
            byQrId.put(log.getQrId(), byQrId.getOrDefault(log.getQrId(), 0L) + 1);
        }
        stats.put("byQrId", byQrId);

        // Últimos 5 acessos
        stats.put("last5", service.findLast(5));

        return ResponseEntity.ok(stats);
    }

    private String extractClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");

        if (xfHeader != null && !xfHeader.isEmpty()) {
            return xfHeader.split(",")[0];
        }

        return request.getRemoteAddr();
    }
}
