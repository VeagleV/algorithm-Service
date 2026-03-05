package com.crm.algo.core.services;

import com.crm.algo.core.entity.Route;
import com.crm.algo.core.repositories.RouteRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> getRouteByRequestId(Integer requestId) {
        return routeRepository.findByRequestId(requestId);
    }
}
