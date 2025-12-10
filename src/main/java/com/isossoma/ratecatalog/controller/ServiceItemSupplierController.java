package com.isossoma.ratecatalog.controller;

import com.isossoma.ratecatalog.dto.request.CreateSupplier;
import com.isossoma.ratecatalog.dto.request.UpdateSupplier;
import com.isossoma.ratecatalog.dto.response.GetSupplier;
import com.isossoma.ratecatalog.service.SupplierService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.message.SuccessMessages;
import com.isossoma.shared.responses.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class ServiceItemSupplierController {

    private final SupplierService service;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CreateSupplier dto) {
        service.create(dto);
        return ResponseBuilder.created(
                "Proveedor creado correctamente",
                null
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse> update(@RequestBody UpdateSupplier dto) {
        service.update(dto);
        return ResponseBuilder.ok(
                "Registro actualizado correctamente",
                null
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseBuilder.ok(
                "Registro eliminado correctamente",
                null
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable Long id) {
        GetSupplier supplier = service.get(id);
        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, supplier);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<GetSupplier> suppliers = service.getAll(page, size);
        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, suppliers);
    }
}