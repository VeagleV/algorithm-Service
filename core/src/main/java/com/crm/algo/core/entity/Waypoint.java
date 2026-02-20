package com.crm.algo.core.entity;

import com.crm.algo.core.enums.Type;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Waypoint")
@Data
public class Waypoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waypoint_id")
    private int id;

    private Double latitude;

    private Double longitude;

    @Enumerated(EnumType.STRING)
    private Type type;
}
