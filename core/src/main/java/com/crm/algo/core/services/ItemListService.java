package com.crm.algo.core.services;


import com.crm.algo.core.dto.ItemListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "item-Management-Service", url = "http://172.27.127.143:81/api/items/items")
public interface ItemListService {

    @GetMapping("/itemsList/items")
    List<ItemListResponse> getItemListsById(@RequestBody List<Integer> idList);

}
