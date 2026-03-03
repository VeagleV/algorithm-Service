package com.crm.algo.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "Route")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private int id;

    @Column(name = "request_id")
    private Integer requestId;

    public Route(Integer requestId){
        this.requestId = requestId;
    }

}
