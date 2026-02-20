package com.crm.algo.core.repositories;

import com.crm.algo.core.entity.SpanTransport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpanTransportRepository extends JpaRepository<SpanTransport, Integer> {
}
