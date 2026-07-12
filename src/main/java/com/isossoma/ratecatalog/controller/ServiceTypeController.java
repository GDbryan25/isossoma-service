package com.isossoma.ratecatalog.controller;

import com.isossoma.ratecatalog.dto.response.ServiceTypeResponse;
import com.isossoma.ratecatalog.service.OperationTypeService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.responses.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/service-type")
@RequiredArgsConstructor
public class ServiceTypeController {
    private final OperationTypeService operationTypeService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllServiceTypes() {
        List<ServiceTypeResponse> serviceTypeResponse = operationTypeService.findAll();

        return ResponseBuilder.ok(
                "Consulta realizada correctamente",
                serviceTypeResponse
        );
    }
}