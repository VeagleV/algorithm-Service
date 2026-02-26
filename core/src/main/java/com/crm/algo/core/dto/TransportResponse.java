package com.crm.algo.core.dto;

import com.crm.algo.core.enums.TransportType;
import lombok.Data;

@Data
public class TransportResponse {
    private Integer transportId;
    private Integer warehouseId;
    private TransportType transportType;
    private Double costPerKm;
    private Double speed;
    private Integer capacity;
}
