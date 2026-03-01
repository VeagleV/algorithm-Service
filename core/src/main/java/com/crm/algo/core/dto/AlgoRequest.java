package com.crm.algo.core.dto;

import com.crm.algo.core.enums.Condition;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlgoRequest {

    @JsonProperty("warehouse_id")
    private Integer warehouseId;

    @JsonProperty("condition")
    private Condition condition;

    @JsonProperty("shipments")
    private List<ShipmentRequest> shipmentRequestList;


}
