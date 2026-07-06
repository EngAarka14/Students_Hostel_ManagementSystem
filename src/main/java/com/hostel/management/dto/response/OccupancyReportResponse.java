package com.hostel.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OccupancyReportResponse {
    private long totalRooms;
    private long availableRooms;
    private long fullyOccupiedRooms;
    private long underMaintenanceRooms;
    private long totalCapacity;
    private long totalOccupants;
    private double occupancyRatePercent;
}
