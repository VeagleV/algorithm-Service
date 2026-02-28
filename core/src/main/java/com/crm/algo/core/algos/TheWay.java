package com.crm.algo.core.algos;

import com.crm.algo.core.dto.*;
import com.crm.algo.core.enums.Condition;
import com.crm.algo.core.models.TransportModel;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Component
public class TheWay {

    @Value("${API_KEY}")
    static String API_KEY;

    private static Map<Integer, Integer> warehouseItemQuantity = new HashMap<>();
    private static List<TransportModel> transportModels = new ArrayList<>();
    private static Map<Integer, Double> warehouseDistance = new HashMap<>();

    public static double DistanceGetter(String lon1, String lat1, String lon2, String lat2) {
        String url = String.format(
                "https://api.openrouteservice.org/v2/directions/driving-car" +
                        "?&start=%s,%s&end=%s,%s",
                lon1, lat1,
                lon2, lat2
        );

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder(new URI(url))
                    .header("Authorization", API_KEY)
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObj = new JSONObject(response.body());
        double distance = jsonObj
                .getJSONArray("features")
                .getJSONObject(0)
                .getJSONObject("properties")
                .getJSONObject("summary")
                .getDouble("distance");

        return distance;
    }

    public static void mainCalculate(AlgoRequest algoRequest){
        List<ItemListResponse> itemList = http.request(itemId, warehouseId);
        List<Integer> warehousesId = itemList.stream()
                .map(ItemListResponse::getWarehouseId)
                .toList(); // получаем все warehouse`ы из itemList`а
        List<WarehouseResponse> warehouseList = http.request(warehousesId); // из списка выше получаем всю инфу об этих складах
        List<TransportResponse> transportList = http.request(warehousesId); // из списка n - 2 получаем весь транспорт с которым можно взаимодействовать
        // один раз заполняем все дистанции между src складом и остальныии
        WarehouseResponse warehouseSrc = warehouseList.stream()
                .filter(w -> w.getWarehouseId().equals(algoRequest.getWarehouseId()))
                .findFirst()
                .get();
        String lon1 = warehouseSrc.getLongitude().toString(), lat1 = warehouseSrc.getLatitude().toString();
        for(WarehouseResponse warehouseResponse : warehouseList) {
            if(!warehouseDistance.containsKey(warehouseResponse.getWarehouseId())){
                String lon2 = warehouseResponse.getLongitude().toString(), lat2 = warehouseResponse.getLatitude().toString();
                warehouseDistance.put(warehouseResponse.getWarehouseId(), DistanceGetter(lon1, lat1, lon2, lat2));
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

        for(ShipmentRequest shipment : algoRequest.getShipmentRequestList()){
            List<ItemListResponse> neededItems = new ArrayList<>(); // только товар shipment_item_id = itemlist_item_id
            int itemQuantity = 0; // подсчет количества товаров на всех складах
            for(ItemListResponse item : itemList){ //ищем сначала товар shipment
                if(Objects.equals(item.getWarehouseId(), algoRequest.getWarehouseId())){
                    continue; // тот же склад - не проверяем
                }
                if(!Objects.equals(shipment.getItemId(), item.getItemId())){
                    continue; // не один и тот же товар
                }
                itemQuantity += item.getQuantity();
                neededItems.add(item);
            }
            if (itemQuantity < shipment.getQuantity()){
                continue; // !!!!!!!!!!!!!!!!!!!! нет столька товара
            }
            calculate(algoRequest, neededItems);
        }
    }

    public static void calculate( AlgoRequest algoRequest, List<ItemListResponse> neededItems) {
        warehouseItemQuantity.clear();
        for(ItemListResponse itemListResponse : neededItems) {
            warehouseItemQuantity.put(itemListResponse.getWarehouseId(), itemListResponse.getQuantity());
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
                    cheapers.get(i).setItemQuantityToTransport(cheapers.get(i).getItemQuantityToTransport() - (currentQuantity - itemOverallQuantity));
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
                    fasters.get(i).setItemQuantityToTransport(fasters.get(i).getItemQuantityToTransport() - (currentQuantity - itemOverallQuantity));
                }
                answer.add(fasters.get(i));
                i++;
            }
        }
        return answer;
    }

}
