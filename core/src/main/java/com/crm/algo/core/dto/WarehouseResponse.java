package com.crm.algo.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WarehouseResponse {
    @JsonProperty("warehouse_id")
    private Integer warehouseId;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("latitude")
    private Double latitude;
}
