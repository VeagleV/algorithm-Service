package com.crm.algo.core.algos;

import com.crm.algo.core.dto.*;
import com.crm.algo.core.enums.Condition;
import com.crm.algo.core.models.TransportModel;

import java.time.Duration;
import java.util.*;

public class TheWay {

    private static Map<Integer, Integer> warehouseItemQuantity = new HashMap();
    private static List<TransportModel> transportModels = new ArrayList();
    private static Map<Integer, Double> warehouseDistance = new HashMap();

    public static void calculate(AlgoRequest algoRequest) {
        List<ItemListResponse> itemList = http.request;
        for(ItemListResponse itemListResponse : itemList) {
            warehouseItemQuantity.put(itemListResponse.getWarehouseId(), itemListResponse.getQuantity());
        }
        List<Integer> warehousesId = new ArrayList(warehouseItemQuantity.keySet());
        List<WarehouseResponse> warehouseList = http.requst(warehousesId);
        List<TransportResponse> transportList = http.requst(warehousesId);
        for(WarehouseResponse warehouseResponse : warehouseList) {
            if(warehouseDistance.containsKey(warehouseResponse.getWarehouseId())){
                warehouseDistance.put(warehouseResponse.getWarehouseId(), API(warehouseResponse.getLongitude(), warehouseResponse.getLatitude()));
            }
        }

        for(TransportResponse transportResponse : transportList) {
            TransportModel transportModel = TransportModel.builder()
                    .warehouseId(transportResponse.getWarehouseId())
                    .transportId(transportResponse.getTransportId())
                    .overallCost(warehouseDistance.get(transportResponse.getWarehouseId()) * transportResponse.getCostPerKm())
                    .overallTime(Duration.ofMinutes((long) (warehouseDistance.get(transportResponse.getWarehouseId()) / transportResponse.getSpeed()) * 60))
                    .capacity(transportResponse.getCapacity())
                    .build();
            transportModels.add(transportModel);
        }
        
        if(algoRequest.getCondition() == Condition.CHEAPER){
            cheaper(algoRequest);
        } else if (algoRequest.getCondition() == Condition.FASTER) {
            faster(algoRequest);
        }

    }

    private static List<TransportModel> cheaper(AlgoRequest algoRequest) {
        List<TransportModel> answer = new ArrayList<>();
        for(ShipmentRequest shipmentRequest : algoRequest.getShipmentRequestList()){
            List<TransportModel> cheapers = new ArrayList<>();
            Integer itemOverallQuantity = shipmentRequest.getQuantity();
            for(TransportModel transportModel : transportModels){
                transportModel.setItemQuantityToTransport(
                        warehouseItemQuantity.get(transportModel.getWarehouseId()) > transportModel.getCapacity()
                                ? transportModel.getCapacity()
                                : warehouseItemQuantity.get(transportModel.getWarehouseId())
                );
                transportModel.setCostEfficiency(transportModel.getOverallCost() / transportModel.getItemQuantityToTransport());
            }
            PriorityQueue<TransportModel> minHeap = new PriorityQueue<>(Comparator.comparingDouble(TransportModel::getCostEfficiency));
            minHeap.addAll(transportModels);
            int currentQuantity = 0;
            while(currentQuantity < itemOverallQuantity){
                TransportModel transportModel = minHeap.poll();
                int warehouseId = transportModel.getWarehouseId();
                int itemQuantity = warehouseItemQuantity.get(warehouseId);
                if(itemQuantity == 0){
                    continue;
                }
                transportModel.setItemQuantityToTransport(
                        itemQuantity > transportModel.getCapacity()
                                ? transportModel.getCapacity()
                                : itemQuantity
                );
                warehouseItemQuantity.put(warehouseId, itemQuantity - transportModel.getItemQuantityToTransport());
                for(TransportModel transportModel2 : transportModels){
                    if(transportModel2.getWarehouseId() != warehouseId){
                        continue;
                    }
                    minHeap.remove(transportModel2);
                    transportModel2.setCostEfficiency(transportModel2.getOverallCost() / transportModel2.getItemQuantityToTransport());
                    minHeap.add(transportModel2);
                }
                currentQuantity += transportModel.getItemQuantityToTransport();
                cheapers.add(transportModel);
            }
            cheapers.sort(Comparator.comparing(TransportModel::getItemQuantityToTransport));
            Collections.reverse(cheapers);
            currentQuantity = 0;
            int i = 0;
            while (currentQuantity < itemOverallQuantity){
                currentQuantity += cheapers.get(i).getItemQuantityToTransport();
                if(currentQuantity > itemOverallQuantity){
                    cheapers.get(i).setItemQuantityToTransport(cheapers.get(i).getItemQuantityToTransport() - currentQuantity - itemOverallQuantity);
                }
                answer.add(cheapers.get(i));
                i++;
            }
        }
        return answer;
    }


    private static List<TransportModel> faster(AlgoRequest algoRequest) {
        List<TransportModel> answer = new ArrayList<>();
        for(ShipmentRequest shipmentRequest : algoRequest.getShipmentRequestList()){
            List<TransportModel> fasters = new ArrayList<>();
            int itemOverallQuantity = shipmentRequest.getQuantity();
            transportModels.sort(Comparator.comparing(TransportModel::getOverallTime));
            int currentQuantity = 0, i = 0;
            while(currentQuantity < itemOverallQuantity){
                Integer itemQuantity = warehouseItemQuantity.get(transportModels.get(i).getWarehouseId());
                if(itemQuantity == 0) {
                    i++; continue;
                }
                transportModels.get(i).setItemQuantityToTransport(
                        itemQuantity > transportModels.get(i).getCapacity()
                                ? transportModels.get(i).getCapacity()
                                : itemQuantity
                );
                warehouseItemQuantity.put(transportModels.get(i).getWarehouseId(),
                        itemQuantity - transportModels.get(i).getItemQuantityToTransport()
                );
                currentQuantity += transportModels.get(i).getItemQuantityToTransport();
                fasters.add(transportModels.get(i));
                i++;
            }
            fasters.sort(Comparator.comparing(TransportModel::getItemQuantityToTransport));
            Collections.reverse(fasters);
            currentQuantity = 0; i = 0;
            while (currentQuantity < itemOverallQuantity){
                currentQuantity += fasters.get(i).getItemQuantityToTransport();
                if(currentQuantity > itemOverallQuantity){
                    fasters.get(i).setItemQuantityToTransport(fasters.get(i).getItemQuantityToTransport() - currentQuantity - itemOverallQuantity);
                }
                answer.add(fasters.get(i));
                i++;
            }
        }
        return answer;
    }

}
