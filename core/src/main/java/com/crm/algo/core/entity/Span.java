package com.crm.algo.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.crm.algo.core.entity.Waypoint;

import java.time.Duration;

@Entity
@Table(name = "Span")
@Data
public class Span {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "span_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @Column(name = "transport_id")
    private Integer transportId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "waypoint_src")
    private Waypoint waypointSrc;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "waypoint_dest")
    private Waypoint waypointDest;

    private Double distance;

    private Double cost;

    @Column(name = "time", columnDefinition = "INTERVAL")
    private Duration time;
}
