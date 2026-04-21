package com.example.tracking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tracking.entity.AccessLog;
import com.example.tracking.repository.AccessLogRepository;
import com.example.tracking.util.HashUtil;

@Service
public class AccessLogService {

    private final AccessLogRepository repository;

    public AccessLogService(AccessLogRepository repository) {
        this.repository = repository;
    }

    public void save(String qrId, String userAgent, String ip, String referer) {
        if (qrId == null || qrId.trim().isEmpty() || qrId.length() > 100) {
            throw new IllegalArgumentException("qrId inválido");
        }
        AccessLog log = new AccessLog();
        log.setQrId(qrId);
        log.setTimestamp(LocalDateTime.now());
        log.setUserAgent(userAgent != null ? userAgent : "");
        log.setIpHash(HashUtil.sha256(ip));
        log.setReferer(referer);
        repository.save(log);
    }

    public long countAll() {
        return repository.count();
    }

    public long countByQrId(String qrId) {
        return repository.countByQrId(qrId);
    }

    public List<AccessLog> findLast(int limit) {
        return repository.findTopNByOrderByTimestampDesc(limit);
    }

    public List<AccessLog> findAll() {
        return repository.findAll();
    }

    public List<AccessLog> findByQrId(String qrId) {
        return repository.findByQrId(qrId);
    }
}
