package com.crm.algo.core.models;

import com.crm.algo.core.enums.TransportType;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransportModel that = (TransportModel) o;
        return Objects.equals(warehouseId, that.warehouseId) &&
                Objects.equals(transportId, that.transportId) &&
                Objects.equals(overallCost, that.overallCost) &&
                Objects.equals(overallTime, that.overallTime) &&
                transportType == that.transportType &&
                Objects.equals(capacity, that.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouseId, transportId, overallCost, overallTime, transportType, capacity);
    }
}
