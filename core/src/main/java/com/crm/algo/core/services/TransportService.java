package com.crm.algo.core.services;

import com.crm.algo.core.dto.TransportResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "transport-Management-Service", url = "http://localhost:8084/transports")
public interface TransportService {

    @PostMapping("/transportsByWarehouses")
    List<TransportResponse> getAllTransports(@RequestBody List<Integer> warehousesId);

}
