package com.example.tracking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/logs")
    public ResponseEntity<List<AccessLog>> getAllLogs(@RequestParam(required = false) String qrId) {
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

        service.save(
                qrId,
                request.getHeader("User-Agent"),
                ip
        );

        return ResponseEntity.status(302)
                .header("Location", redirectUrl)
                .build();
    }

    private String extractClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");

        if (xfHeader != null && !xfHeader.isEmpty()) {
            return xfHeader.split(",")[0];
        }

        return request.getRemoteAddr();
    }
}
