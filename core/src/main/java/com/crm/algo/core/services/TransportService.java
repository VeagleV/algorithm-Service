package com.crm.algo.core.services;

import com.crm.algo.core.dto.TransportResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "transport-Management-Service", url = "http://172.27.127.143:81/api/transport/transports")
public interface TransportService {

    @GetMapping("//transportsByWarehouses")
    List<TransportResponse> getAllTransports(@RequestBody List<Integer> warehousesId);

}
