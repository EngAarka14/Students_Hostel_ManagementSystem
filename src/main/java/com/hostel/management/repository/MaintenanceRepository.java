package com.hostel.management.repository;

import com.hostel.management.entity.MaintenanceRequest;
import com.hostel.management.entity.enums.MaintenanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<MaintenanceRequest, Long> {

    List<MaintenanceRequest> findByRoomId(Long roomId);

    List<MaintenanceRequest> findByStudentId(Long studentId);

    List<MaintenanceRequest> findByRequestData(MaintenanceStatus status);
}