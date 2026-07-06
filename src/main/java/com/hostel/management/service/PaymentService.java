package com.hostel.management.service;

import com.hostel.management.entity.Payment;
import com.hostel.management.entity.Student;
import com.hostel.management.entity.enums.PaymentStatus;
import com.hostel.management.exception.ResourceNotFoundException;
import com.hostel.management.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentService studentService;

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public Payment getById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    public List<Payment> getByStudent(Long studentId) {
        return paymentRepository.findByStudentId(studentId);
    }

    public List<Payment> getByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }

    @Transactional
    public Payment create(Payment payment, Long studentId) {
        Student student = studentService.getById(studentId);
        payment.setStudent(student);
        if (payment.getStatus() == PaymentStatus.PAID && payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDate.now());
        }
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment markAsPaid(Long id, String method) {
        Payment payment = getById(id);
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaymentDate(LocalDate.now());
        if (method != null) {
            payment.setMethod(com.hostel.management.entity.enums.PaymentMethod.valueOf(method));
        }
        return paymentRepository.save(payment);
    }

    @Transactional
    public void delete(Long id) {
        Payment existing = getById(id);
        paymentRepository.delete(existing);
    }
}
