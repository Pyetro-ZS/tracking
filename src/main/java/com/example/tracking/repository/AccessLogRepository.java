package com.example.tracking.repository;

import com.example.tracking.entity.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import java.util.List;

public interface AccessLogRepository extends JpaRepository<AccessLog, UUID> {

    List<AccessLog> findByQrId(String qrId);

    long countByQrId(String qrId);

    // Usando Spring Data JPA projections para buscar os últimos N
    List<AccessLog> findTop5ByOrderByTimestampDesc();

    // Para uso genérico (caso queira mudar o limite)
    default List<AccessLog> findTopNByOrderByTimestampDesc(int n) {
        List<AccessLog> all = findTop5ByOrderByTimestampDesc();
        return all.size() > n ? all.subList(0, n) : all;
    }
}
