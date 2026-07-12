package com.isossoma.ratecatalog.service;

import com.isossoma.ratecatalog.dto.response.ServiceTypeResponse;
import java.util.List;

public interface OperationTypeService {
    List<ServiceTypeResponse> findAll();
}