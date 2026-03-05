package com.crm.algo.core.services;


import com.crm.algo.core.dto.ItemListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "item-Management-Service")
public interface ItemListService {

    @PostMapping("/items/itemsList/items")
    List<ItemListResponse> getItemListsById(@RequestBody List<Integer> idList);

}
