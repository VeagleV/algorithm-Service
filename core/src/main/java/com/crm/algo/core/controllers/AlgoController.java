package com.crm.algo.core.controllers;


import com.crm.algo.core.algos.TheWay;
import com.crm.algo.core.dto.AlgoRequest;
import com.crm.algo.core.exceptions.NotEnoughItemsException;
import com.crm.algo.core.exceptions.NotEnoughTransportsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/algo")
public class AlgoController {

    private final TheWay theWay;

    public AlgoController(TheWay theWay) {
        this.theWay = theWay;
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


}
