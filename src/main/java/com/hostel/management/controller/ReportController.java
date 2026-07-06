package com.hostel.management.controller;

import com.hostel.management.dto.response.DashboardStatsResponse;
import com.hostel.management.dto.response.OccupancyReportResponse;
import com.hostel.management.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/occupancy")
    public ResponseEntity<OccupancyReportResponse> occupancy() {
        return ResponseEntity.ok(reportService.getOccupancyReport());
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardStatsResponse> dashboard() {
        return ResponseEntity.ok(reportService.getDashboardStats());
    }
}
