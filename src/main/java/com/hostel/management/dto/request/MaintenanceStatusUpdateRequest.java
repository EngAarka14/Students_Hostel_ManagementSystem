package com.hostel.management.dto.request;

import com.hostel.management.entity.enums.MaintenanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MaintenanceStatusUpdateRequest {
    @NotNull
    private MaintenanceStatus status;
}
