package com.isossoma.ratecatalog.service;

import com.isossoma.ratecatalog.dto.request.CreateServiceType;
import com.isossoma.ratecatalog.dto.request.UpdateServiceType;
import com.isossoma.ratecatalog.dto.response.GetAllServiceType;

import java.util.List;

public interface OperationTypeService {
    void saveServiceTypeWithCategories(CreateServiceType createServiceType);
    void editServiceTypeWithCategories(UpdateServiceType updateServiceType);
    void softDeleteServiceType(Long serviceTypeId);
    List<GetAllServiceType> getAllServiceTypes();
}