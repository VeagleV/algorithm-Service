package com.crm.algo.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "spantransport")
public class SpanTransport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spantransport_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "span_id")
    private Span span;

    @Column(name = "transport_id")
    private Integer transportId;

    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "quantity")
    private Integer quantity;
}
