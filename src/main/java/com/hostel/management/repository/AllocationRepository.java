package com.hostel.management.repository;

import com.hostel.management.entity.Allocation;
import com.hostel.management.entity.enums.AllocationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllocationRepository extends JpaRepository<Allocation, Long> {

    List<Allocation> findByStudentId(Long studentId);
    List<Allocation> findByRoomId(Long roomId);
    List<Allocation> findByStatus(AllocationStatus status);
}