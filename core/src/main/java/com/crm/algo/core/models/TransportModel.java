package com.crm.algo.core.models;

import com.crm.algo.core.enums.TransportType;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Builder
@Data
public class TransportModel {
    private Integer warehouseId;
    private Integer transportId;
    private Double overallCost;
    private Duration overallTime;
    private TransportType transportType;
    private Integer capacity;
    private Integer itemQuantityToTransport;
    private Double costEfficiency;
}
