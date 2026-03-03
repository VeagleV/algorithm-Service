package com.crm.algo.core.dto;

import com.crm.algo.core.enums.TransportType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransportResponse {
    @JsonProperty("transport_id")
    private Integer transportId;
    @JsonProperty("warehouse_id")
    private Integer warehouseId;
    @JsonProperty("type")
    private TransportType transportType;
    @JsonProperty("cost")
    private Double costPerKm;
    @JsonProperty("speed")
    private Double speed;
    @JsonProperty("capacity")
    private Integer capacity;
}
