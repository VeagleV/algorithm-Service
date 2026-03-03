package com.crm.algo.core.controllers;


import com.crm.algo.core.algos.TheWay;
import com.crm.algo.core.dto.AlgoRequest;
import com.crm.algo.core.dto.SpanResponse;
import com.crm.algo.core.exceptions.NotEnoughItemsException;
import com.crm.algo.core.exceptions.NotEnoughTransportsException;
import com.crm.algo.core.services.AlgoService;
import com.crm.algo.core.services.SpanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/algo")
public class AlgoController {

    private final TheWay theWay;
    private final SpanService spanService;

    public AlgoController(TheWay theWay, SpanService spanService) {
        this.theWay = theWay;
        this.spanService = spanService;
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
    public ResponseEntity<List<SpanResponse>> getAlgoResult(@PathVariable Integer requestId) {
        List< SpanResponse> response =  spanService.getSpans(requestId);
        return ResponseEntity.ok(response);
    }


}
