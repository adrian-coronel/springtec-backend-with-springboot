package com.springtec.models.repositories;

import com.springtec.models.payload.DirectRequestView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DirectRequestViewRepository extends JpaRepository<DirectRequestView, Integer> {

   List<DirectRequestView> findAllByTIdAndSdrIdAndDrStateInvoice(Integer tId, Integer sdrId, char drStateInvoice);

}
