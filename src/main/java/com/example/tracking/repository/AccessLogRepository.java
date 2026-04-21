package com.example.tracking.repository;

import com.example.tracking.entity.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import java.util.List;

public interface AccessLogRepository extends JpaRepository<AccessLog, UUID> {

    List<AccessLog> findByQrId(String qrId);
}
