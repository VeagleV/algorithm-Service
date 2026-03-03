package com.crm.algo.core.mappers;

import com.crm.algo.core.dto.SpanResponse;
import com.crm.algo.core.entity.Span;

public class SpanMapper {
    public static SpanResponse createSpanResponse(Span span) {
        return SpanResponse.builder()
                .warehouseId(span.getWarehouseId())
                .transportId(span.getTransportId())
                .cost(span.getCost())
                .time(span.getTime())
                .itemId(span.getItemId())
                .itemQuantity(span.getItemQuantity())
                .build();

    }
}
