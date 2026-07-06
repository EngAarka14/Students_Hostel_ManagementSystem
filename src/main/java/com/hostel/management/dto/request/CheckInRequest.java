package com.hostel.management.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CheckInRequest {
    @NotNull
    private Long studentId;

    @NotNull
    private Long roomId;

    private LocalDate checkInDate;

    private String notes;
}
