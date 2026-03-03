package com.crm.algo.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemListResponse {
    @JsonProperty("warehouse_id")
    private Integer warehouseId;
    @JsonProperty("item_id")
    private Integer itemId;
    @JsonProperty("quantity")
    private Integer quantity;
}
