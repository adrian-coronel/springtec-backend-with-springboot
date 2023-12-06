package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.DirectRequestDto;
import com.springtec.models.dto.InvoiceDto;
import com.springtec.models.dto.MaterialDto;
import com.springtec.models.entity.DirectRequest;
import com.springtec.models.entity.Invoice;
import com.springtec.models.entity.Material;
import com.springtec.models.enums.State;
import com.springtec.models.payload.InvoiceRequest;
import com.springtec.models.payload.MaterialRequest;
import com.springtec.models.repositories.DirectRequestRepository;
import com.springtec.models.repositories.InvoiceRepository;
import com.springtec.models.repositories.MaterialRepository;
import com.springtec.services.IInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceImplService implements IInvoiceService {

   private final DirectRequestRepository directRequestRepository;
   private final InvoiceRepository invoiceRepository;
   private final MaterialRepository materialRepository;

   @Override
   public InvoiceDto findByDirectRequest(Integer directRequestId) throws ElementNotExistInDBException {
      //invoiceRepository.existsByDirectRequestId(directRequestId);
      Invoice invoice = invoiceRepository.findByDirectRequestId(directRequestId);
      if (invoice == null){
         throw new ElementNotExistInDBException("El directRequest con id "+directRequestId+" no existe.");
      }
      return null;
   }

   @Override
   public InvoiceDto save(InvoiceRequest invoiceRequest) throws ElementNotExistInDBException {
      // Verificamos que el directRequest exista
      DirectRequest directRequest = directRequestRepository.findById(invoiceRequest.getDirectRequestId())
          .orElseThrow(() -> new ElementNotExistInDBException("El directRequest con id "+invoiceRequest.getDirectRequestId()+" no existe."));

      Invoice invoice = Invoice.builder()
          .task(invoiceRequest.getTask())
          .directRequest(directRequest)
          .description(invoiceRequest.getDescription())
          .price(invoiceRequest.getPrice())
          .date(invoiceRequest.getDate())
          .hour(invoiceRequest.getHour())
          .state(State.ACTIVE)
          .build();
      invoice = invoiceRepository.save(invoice);

      List<MaterialDto> materiales = new ArrayList<>();
      // Si se envia una lista de materiale y no esta vacia
      if (invoiceRequest.getMateriales() != null && !invoiceRequest.getMateriales().isEmpty()){
         Invoice finalInvoice = invoice;
         List<Material> materialList = invoiceRequest.getMateriales().stream().map(material -> Material.builder()
             .name(material.getName())
             .invoice(finalInvoice.getId())
             .price(material.getPrice())
             .stock(material.getStock())
             .state(State.ACTIVE)
             .build()).toList();
         materiales = materialRepository.saveAll(materialList)
             .stream().map(MaterialDto::new).toList();
      }

      return InvoiceDto.builder()
          .id(invoice.getId())
          .directRequest(new DirectRequestDto(invoice.getDirectRequest()))
          .task(invoice.getTask())
          .description(invoice.getDescription())
          .price(invoice.getPrice())
          .date(invoice.getDate())
          .hour(invoice.getHour())
          .materiales(materiales)
          .build();
   }

}
