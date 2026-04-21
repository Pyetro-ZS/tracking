package com.example.tracking.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String qrId;
    private LocalDateTime timestamp;
    private String userAgent;
    private String ipHash;

    public UUID getId() {
        return id;
    }

    public String getQrId() {
        return qrId;
    }

    public void setQrId(String qrId) {
        this.qrId = qrId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIpHash() {
        return ipHash;
    }

    public void setIpHash(String ipHash) {
        this.ipHash = ipHash;
    }
}
