package com.crm.algo.core.dto;

import com.crm.algo.core.enums.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlgoRequest {

    private Integer warehouseId;

    private Condition condition;

    private List<ShipmentRequest> shipmentRequestList;


}
