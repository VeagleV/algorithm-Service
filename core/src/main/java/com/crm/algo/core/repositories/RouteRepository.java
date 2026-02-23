package com.crm.algo.core.repositories;

import com.crm.algo.core.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
    List<Route> findByRequestId(Integer requestId);

}
