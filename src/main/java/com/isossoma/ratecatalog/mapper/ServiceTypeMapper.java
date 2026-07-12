package com.isossoma.ratecatalog.mapper;

import com.isossoma.ratecatalog.dto.response.ServiceTypeResponse;
import com.isossoma.ratecatalog.model.ServiceType;
import org.springframework.stereotype.Component;

@Component
public class ServiceTypeMapper {
    public ServiceTypeResponse toServiceTypeResponse(ServiceType serviceType) {
        return new  ServiceTypeResponse(
                serviceType.getId(),
                serviceType.getDescription()
        );
    }
}
