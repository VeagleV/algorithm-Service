package com.crm.algo.core.repositories;

import com.crm.algo.core.entity.Span;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpanRepository extends JpaRepository<Span, Integer> {
    List<Span> findAllSpanByRouteId(Integer routeId);
}
