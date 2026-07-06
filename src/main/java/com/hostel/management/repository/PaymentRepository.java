package com.hostel.management.repository;

import com.hostel.management.entity.Payment;
import com.hostel.management.entity.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentId(Long studentId);
    List<Payment> findByStatus(PaymentStatus status);
}
