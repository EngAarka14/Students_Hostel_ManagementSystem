package com.hostel.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {
    private long totalStudents;
    private long totalRooms;
    private long activeAllocations;
    private long pendingMaintenanceRequests;
    private long overduePayments;
    private BigDecimal totalRevenueCollected;
}
