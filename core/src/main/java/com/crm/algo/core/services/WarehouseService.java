package com.crm.algo.core.services;


import com.crm.algo.core.dto.WarehouseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "warehouse-Service", url = "http://172.27.127.143:81/api/warehouse/warehouse")
public interface WarehouseService {


    @GetMapping("/bulk")
    List<WarehouseResponse> warehouseList(@RequestBody List<Integer> idList);

}
