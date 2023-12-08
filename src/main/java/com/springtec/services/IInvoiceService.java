package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.InvoiceDto;
import com.springtec.models.payload.InvoiceRequest;

import java.util.List;
import java.util.Map;

public interface IInvoiceService {

   List<InvoiceDto> findByFilters(Map<String, String> filters) throws ElementNotExistInDBException;
   InvoiceDto save(InvoiceRequest invoiceRequest) throws Exception;

}
