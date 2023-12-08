package com.springtec.services.impl;

import com.springtec.exceptions.DuplicateElementException;
import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.exceptions.InvalidArgumentException;
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
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InvoiceImplService implements IInvoiceService {

   private final DirectRequestRepository directRequestRepository;
   private final InvoiceRepository invoiceRepository;
   private final MaterialRepository materialRepository;

   @Override
   public List<InvoiceDto> findByFilters(Map<String, String> filters) throws ElementNotExistInDBException {
      List<InvoiceDto> invoiceDtoList = new ArrayList<>();
      if(filters.containsKey("directRequestId")){ // Si es que se quiere filtrar por directREQUEST
         Integer directRequestId = Integer.parseInt(filters.get("directRequestId"));
         //Buscamos el invoice por directRequest
         Invoice invoice = invoiceRepository.findByDirectRequestId(directRequestId);
         if (invoice == null){ // Si no existe lanzamos una exception
            throw new ElementNotExistInDBException("El directRequest con id "+directRequestId+" no existe.");
         }
         //Buscamos los materiales, en caso no tenga será un array vacío
         List<MaterialDto> materiales = materialRepository.findAllByInvoice(invoice.getId()).stream()
             .map(MaterialDto::new).toList();
         //Agregamos a la lista el INVOICE
         invoiceDtoList.add(InvoiceDto.builder()
             .id(invoice.getId())
             .task(invoice.getTask())
             .description(invoice.getDescription())
             .price(invoice.getPrice())
             .date(invoice.getDate())
             .hour(invoice.getHour())
             .materiales(materiales)
             .build());
      } else {
         // Buscamos todas las facturas
         invoiceDtoList = invoiceRepository.findAll().stream()
             .map(invoice -> { // Mapeamos a DTO
                  // Buscamos los materiales para cada invoice, si no tiene será lista vacia
                  List<MaterialDto> materiales =  materialRepository.findAllByInvoice(invoice.getId())
                        .stream().map(MaterialDto::new).toList();
                  return InvoiceDto.builder() //Retornamos el DTO
                     .id(invoice.getId())
                     .task(invoice.getTask())
                     .description(invoice.getDescription())
                     .price(invoice.getPrice())
                     .date(invoice.getDate())
                     .hour(invoice.getHour())
                     .materiales(materiales)
                     .build();
         }).toList();
      }
      // Devolvemos la lista InvoiceDTO
      return invoiceDtoList;
   }

   @Override
   public InvoiceDto save(InvoiceRequest invoiceRequest) throws Exception {
      if (invoiceRepository.existsByDirectRequestId(invoiceRequest.getDirectRequestId()))
         throw new DuplicateElementException("Ya existe una factura en directRequest con id "+invoiceRequest.getDirectRequestId());

      // Verificamos que el directRequest exista
      DirectRequest directRequest = directRequestRepository.findById(invoiceRequest.getDirectRequestId())
          .orElseThrow(() -> new ElementNotExistInDBException("El directRequest con id "+invoiceRequest.getDirectRequestId()+" no existe."));

      // Creamos el invoice
      Invoice invoice = Invoice.builder()
          .task(invoiceRequest.getTask())
          .directRequest(directRequest)
          .description(invoiceRequest.getDescription())
          .price(invoiceRequest.getPrice())
          .date(invoiceRequest.getDate())
          .hour(invoiceRequest.getHour())
          .state(State.ACTIVE)
          .build();
      // Guardamos el invoice y reescribimos la variable
      invoice = invoiceRepository.save(invoice);

      // Actualizamos el estado de invoice en direct request
      directRequest.setStateInvoice(State.ACTIVE);
      directRequestRepository.save(directRequest);

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
          .task(invoice.getTask())
          .description(invoice.getDescription())
          .price(invoice.getPrice())
          .date(invoice.getDate())
          .hour(invoice.getHour())
          .materiales(materiales)
          .build();
   }

}
