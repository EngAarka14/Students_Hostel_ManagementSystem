package com.hostel.management.controller;

import com.hostel.management.entity.Payment;
import com.hostel.management.entity.enums.PaymentStatus;
import com.hostel.management.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @GetMapping
    public ResponseEntity<List<Payment>> getAll(@RequestParam(required = false) Long studentId,
                                                 @RequestParam(required = false) PaymentStatus status) {
        if (studentId != null) {
            return ResponseEntity.ok(paymentService.getByStudent(studentId));
        }
        if (status != null) {
            return ResponseEntity.ok(paymentService.getByStatus(status));
        }
        return ResponseEntity.ok(paymentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Payment> create(@Valid @RequestBody Payment payment, @RequestParam Long studentId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.create(payment, studentId));
    }

    @PutMapping("/{id}/mark-paid")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Payment> markAsPaid(@PathVariable Long id, @RequestParam(required = false) String method) {
        return ResponseEntity.ok(paymentService.markAsPaid(id, method));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
