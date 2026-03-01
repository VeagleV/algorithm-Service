package com.crm.algo.core.services;


import com.crm.algo.core.dto.ItemListResponse;
import com.crm.algo.core.dto.TransportResponse;
import com.crm.algo.core.dto.WarehouseResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlgoService {

    private final ItemListService itemListService;
    private final WarehouseService warehouseService;
    private final TransportService transportService;

    AlgoService(ItemListService itemListService, WarehouseService warehouseService, TransportService transportService) {
        this.itemListService = itemListService;
        this.warehouseService = warehouseService;
        this.transportService = transportService;
    }


    public List<ItemListResponse> getItemLists(List<Integer> itemIds) {
        return itemListService.getItemListsById(itemIds);
    }

    public List<WarehouseResponse> getWarehouses(List<Integer> warehouseIds) {
        return warehouseService.warehouseList(warehouseIds);
    }

    public List<TransportResponse> getTransports(List<Integer> transportIds) {
        return transportService.getAllTransports(transportIds);
    }
}
