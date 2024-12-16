package com.Zi.spring_IMS.repository;

import com.Zi.spring_IMS.model.entity.InboundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InboundRepository extends JpaRepository<InboundTransaction, Integer> {
}
