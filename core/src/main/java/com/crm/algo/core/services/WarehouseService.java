package com.crm.algo.core.services;


import com.crm.algo.core.dto.WarehouseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "warehouse-Service", url = "http://localhost:8081")
public interface WarehouseService {

    @GetMapping("/warehouse/{warehouseId}")
    WarehouseResponse getWarehouseById(@PathVariable int warehouseId);

    @PostMapping("/warehouse/bulk")
    List<WarehouseResponse> warehouseList(@RequestBody List<Integer> idList);

}
