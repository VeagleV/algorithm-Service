package com.crm.algo.core.controllers;


import com.crm.algo.core.algos.TheWay;
import com.crm.algo.core.dto.AlgoRequest;
import com.crm.algo.core.dto.RouteResponse;
import com.crm.algo.core.dto.SpanResponse;
import com.crm.algo.core.entity.Route;
import com.crm.algo.core.entity.Span;
import com.crm.algo.core.exceptions.NotEnoughItemsException;
import com.crm.algo.core.exceptions.NotEnoughTransportsException;
import com.crm.algo.core.services.AlgoService;
import com.crm.algo.core.services.SpanService;
import jakarta.ws.rs.GET;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/algo")
public class AlgoController {

    private final TheWay theWay;
    private final SpanService spanService;
    private final ResourceLoader resourceLoader;

    public AlgoController(TheWay theWay, SpanService spanService, ResourceLoader resourceLoader) {
        this.theWay = theWay;
        this.spanService = spanService;
        this.resourceLoader = resourceLoader;
    }

    @PostMapping("/startAlgorithm")
    public ResponseEntity<?> registerAlgoRequest(@RequestBody AlgoRequest algoRequest) {
        try {
            theWay.mainCalculate(algoRequest);
        } catch (NotEnoughItemsException | NotEnoughTransportsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/algoResult/{requestId}")
    public ResponseEntity<List<RouteResponse>> getAlgoResult(@PathVariable Integer requestId) {
        List<RouteResponse> spanResponseList =  spanService.getSpans(requestId);
        return new ResponseEntity<>(spanResponseList, HttpStatus.OK);
    }


    @GetMapping("/spans")
    public ResponseEntity<List<SpanResponse>> getSpansByRouteId(@RequestParam Integer routeId) {
        List<SpanResponse> spanResponseList = spanService.getSpansByRouteId(routeId);
        return new ResponseEntity<>(spanResponseList, HttpStatus.OK);
    }


}
