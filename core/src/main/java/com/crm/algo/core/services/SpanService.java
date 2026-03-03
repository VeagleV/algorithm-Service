package com.crm.algo.core.services;

import com.crm.algo.core.dto.SpanResponse;
import com.crm.algo.core.entity.Route;
import com.crm.algo.core.entity.Span;
import com.crm.algo.core.mappers.SpanMapper;
import com.crm.algo.core.repositories.SpanRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpanService {
    private final RouteService routeService;
    private final SpanRepository spanRepository;

    public SpanService(SpanRepository spanRepository, RouteService routeService) {
        this.spanRepository = spanRepository;
        this.routeService = routeService;
    }

    public List<SpanResponse> getSpans(Integer requestId) {
        Route route = routeService.getRouteByRequestId(requestId);
        List<Span> spans = spanRepository.findAllSpanByRouteId(route.getId());
        return spans.stream().map(SpanMapper::createSpanResponse).toList();
    }

}
