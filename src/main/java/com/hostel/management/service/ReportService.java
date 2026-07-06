package com.hostel.management.service;

import com.hostel.management.dto.response.DashboardStatsResponse;
import com.hostel.management.dto.response.OccupancyReportResponse;
import com.hostel.management.entity.Room;
import com.hostel.management.entity.enums.AllocationStatus;
import com.hostel.management.entity.enums.MaintenanceStatus;
import com.hostel.management.entity.enums.PaymentStatus;
import com.hostel.management.entity.enums.RoomStatus;
import com.hostel.management.repository.PaymentRepository;
import com.hostel.management.repository.RoomRepository;
import com.hostel.management.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;
    private final AllocationRepository allocationRepository;
    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final PaymentRepository paymentRepository;

    public OccupancyReportResponse getOccupancyReport() {
        List<Room> rooms = roomRepository.findAll();

        long totalRooms = rooms.size();
        long available = rooms.stream().filter(r -> r.getStatus() == RoomStatus.AVAILABLE).count();
        long fullyOccupied = rooms.stream().filter(r -> r.getStatus() == RoomStatus.FULLY_OCCUPIED).count();
        long underMaintenance = rooms.stream().filter(r -> r.getStatus() == RoomStatus.UNDER_MAINTENANCE).count();
        long totalCapacity = rooms.stream().mapToLong(Room::getCapacity).sum();
        long totalOccupants = allocationRepository.findByStatus(AllocationStatus.ACTIVE).size();

        double occupancyRate = totalCapacity == 0 ? 0.0 : (totalOccupants * 100.0) / totalCapacity;

        return new OccupancyReportResponse(totalRooms, available, fullyOccupied, underMaintenance,
                totalCapacity, totalOccupants, Math.round(occupancyRate * 100.0) / 100.0);
    }

    public DashboardStatsResponse getDashboardStats() {
        long totalStudents = studentRepository.count();
        long totalRooms = roomRepository.count();
        long activeAllocations = allocationRepository.findByStatus(AllocationStatus.ACTIVE).size();
        long pendingMaintenance = maintenanceRequestRepository.findByStatus(MaintenanceStatus.PENDING).size();
        long overduePayments = paymentRepository.findByStatus(PaymentStatus.OVERDUE).size();

        BigDecimal totalRevenue = paymentRepository.findByStatus(PaymentStatus.PAID).stream()
                .map(p -> p.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DashboardStatsResponse(totalStudents, totalRooms, activeAllocations,
                pendingMaintenance, overduePayments, totalRevenue);
    }
}
