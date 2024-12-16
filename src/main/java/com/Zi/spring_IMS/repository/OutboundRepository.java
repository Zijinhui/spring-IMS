package com.Zi.spring_IMS.repository;

import com.Zi.spring_IMS.model.entity.OutboundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboundRepository extends JpaRepository<OutboundTransaction, Integer> {
}
