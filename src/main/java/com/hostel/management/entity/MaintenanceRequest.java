package com.hostel.management.entity;

import com.hostel.management.entity.enums.MaintenancePriority;
import com.hostel.management.entity.enums.MaintenanceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    @NotNull
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @NotBlank
    @Column(nullable = false, length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MaintenancePriority priority = MaintenancePriority.MEDIUM;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MaintenanceStatus status = MaintenanceStatus.PENDING;

    @Column(name = "date_reported", updatable = false)
    private LocalDateTime dateReported;

    @Column(name = "date_resolved")
    private LocalDateTime dateResolved;

    @PrePersist
    protected void onCreate() {
        dateReported = LocalDateTime.now();
    }
}
