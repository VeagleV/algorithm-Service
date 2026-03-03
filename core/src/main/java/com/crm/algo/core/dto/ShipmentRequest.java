package com.crm.algo.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShipmentRequest {

    @JsonProperty("item_id")
    private Integer itemId;

    @JsonProperty("quantity")
    private Integer quantity;
}
