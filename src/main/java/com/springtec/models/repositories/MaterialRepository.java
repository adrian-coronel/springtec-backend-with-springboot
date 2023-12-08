package com.springtec.models.repositories;

import com.springtec.models.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
   List<Material> findAllByInvoice(Integer invoiceId);
}
