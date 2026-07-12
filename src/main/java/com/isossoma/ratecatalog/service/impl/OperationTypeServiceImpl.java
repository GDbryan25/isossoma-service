package com.isossoma.ratecatalog.service.impl;

import com.isossoma.ratecatalog.dto.response.ServiceTypeResponse;
import com.isossoma.ratecatalog.mapper.ServiceTypeMapper;
import com.isossoma.ratecatalog.repository.ServiceTypeRepository;
import com.isossoma.ratecatalog.service.OperationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationTypeServiceImpl implements OperationTypeService {
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceTypeMapper mapper;

    @Override
    public List<ServiceTypeResponse> findAll() {
        return serviceTypeRepository.findAll()
                .stream()
                .map(mapper::toServiceTypeResponse)
                .toList();
    }
}