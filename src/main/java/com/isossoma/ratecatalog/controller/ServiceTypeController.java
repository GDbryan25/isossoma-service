package com.isossoma.ratecatalog.controller;

import com.isossoma.ratecatalog.dto.request.CreateServiceType;
import com.isossoma.ratecatalog.dto.request.UpdateServiceType;
import com.isossoma.ratecatalog.service.OperationTypeService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.responses.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/service-types")
@RequiredArgsConstructor
public class ServiceTypeController {
    private final OperationTypeService operationTypeService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CreateServiceType createServiceType) {
        operationTypeService.saveServiceTypeWithCategories(createServiceType);

        return ResponseBuilder.created(
                "Tipo de servicio creado correctamente",
                null
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse> edit(@Valid @RequestBody UpdateServiceType updateServiceType) {
        operationTypeService.editServiceTypeWithCategories(updateServiceType);

        return ResponseBuilder.created(
                "Registro actualizado correctamente",
                null
        );
    }

    @DeleteMapping("/{service-type-id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("service-type-id") Long serviceTypeId) {
        operationTypeService.softDeleteServiceType(serviceTypeId);

        return ResponseBuilder.ok(
                "Registro eliminado correctamente",
                null
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllServiceTypes() {
        return ResponseBuilder.ok(
                "Consulta realizada correctamente",
                operationTypeService.getAllServiceTypes()
        );
    }
}