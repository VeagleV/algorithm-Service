package com.crm.algo.core.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Entity
@Table(name = "Span")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Span {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "span_id")
    private int id;

    @Column(name = "warehouse_id")
    private Integer warehouseId;

    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "transport_id")
    private Integer transportId;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "time")
    private Duration time;

    @Column(name = "item_quantity")
    private Integer itemQuantity;

    @Column(name = "route_id")
    private Integer routeId;

}
