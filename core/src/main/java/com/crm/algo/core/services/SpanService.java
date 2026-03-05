package com.crm.algo.core.services;

import com.crm.algo.core.dto.RouteResponse;
import com.crm.algo.core.dto.SpanResponse;
import com.crm.algo.core.entity.Route;
import com.crm.algo.core.entity.Span;
import com.crm.algo.core.mappers.SpanMapper;
import com.crm.algo.core.repositories.SpanRepository;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class SpanService {
    private final RouteService routeService;
    private final SpanRepository spanRepository;

    public SpanService(SpanRepository spanRepository, RouteService routeService) {
        this.spanRepository = spanRepository;
        this.routeService = routeService;
    }

    public List<RouteResponse> getSpans(Integer requestId) {
        List<Route> route = routeService.getRouteByRequestId(requestId);
        List<RouteResponse> routeResponseList = new ArrayList<>();

        for (Route routeItem : route) {
            RouteResponse routeResponse = RouteResponse.builder()
                    .routeId(routeItem.getId())
                    .spans(spanRepository.findAllSpanByRouteId(routeItem.getId()))
                    .build();
            routeResponseList.add(routeResponse);
        }

        return routeResponseList;
    }

    public List<SpanResponse> getSpansByRouteId(Integer routeId) {
        List<Span> spans = spanRepository.findAllSpanByRouteId(routeId);
        return spans.stream()
                .map(SpanMapper::createSpanResponse)
                .toList();
    }

}
