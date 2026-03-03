package com.crm.algo.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpanResponse {
    @JsonProperty("warehouse_id")
    private Integer warehouseId;

    @JsonProperty("item_id")
    private Integer itemId;

    @JsonProperty("transport_id")
    private Integer transportId;

    @JsonProperty("cost")
    private Double cost;

    @JsonProperty("time")
    private Duration time;

    @JsonProperty("item_quantity")
    private Integer itemQuantity;
}
