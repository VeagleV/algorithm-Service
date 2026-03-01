package com.crm.algo.core.controllers;


import com.crm.algo.core.algos.TheWay;
import com.crm.algo.core.dto.AlgoRequest;
import com.crm.algo.core.enums.TransportType;
import com.crm.algo.core.models.TransportModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/algo")
public class AlgoController {

    private final TheWay theWay;

    public AlgoController(TheWay theWay) {
        this.theWay = theWay;
    }

    @PostMapping("/startAlgorithm")
    public ResponseEntity<?> registerAlgoRequest(@RequestBody AlgoRequest algoRequest) {
        theWay.mainCalculate(algoRequest);
        return ResponseEntity.ok().build();
    }
}
