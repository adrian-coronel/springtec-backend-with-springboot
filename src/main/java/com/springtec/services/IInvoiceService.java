package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.InvoiceDto;
import com.springtec.models.payload.InvoiceRequest;

public interface IInvoiceService {

   InvoiceDto findByDirectRequest(Integer directRequestId) throws ElementNotExistInDBException;
   InvoiceDto save(InvoiceRequest invoiceRequest) throws ElementNotExistInDBException;

}
