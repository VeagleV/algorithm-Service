package com.crm.algo.core.repositories;

import com.crm.algo.core.entity.Waypoint;
import com.crm.algo.core.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaypointRepository extends JpaRepository<Waypoint, Integer> {
    List<Waypoint> findAllByType(Type type);
}
