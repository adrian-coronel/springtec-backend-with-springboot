package com.springtec.models.repositories;

import com.springtec.models.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

   boolean existsByDirectRequestId(Integer directRequestId);
   Invoice findByDirectRequestId(Integer directRequestId);

}
