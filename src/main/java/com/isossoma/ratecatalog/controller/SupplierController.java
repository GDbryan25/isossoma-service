package com.isossoma.ratecatalog.controller;

import com.isossoma.ratecatalog.dto.filters.SupplierFilter;
import com.isossoma.ratecatalog.dto.request.CreateSupplier;
import com.isossoma.ratecatalog.dto.request.UpdateSupplier;
import com.isossoma.ratecatalog.dto.response.SupplierResponse;
import com.isossoma.ratecatalog.service.SupplierService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.message.SuccessMessages;
import com.isossoma.shared.responses.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService service;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CreateSupplier createSupplier) {
        SupplierResponse supplier = service.create(createSupplier);

        return ResponseBuilder.created(
                "Proveedor creado correctamente",
                supplier
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody UpdateSupplier updateSupplier) {
        SupplierResponse supplier = service.update(id, updateSupplier);

        return ResponseBuilder.ok(
                "Registro actualizado correctamente",
                supplier
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

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<ApiResponse> reactivate(@PathVariable Long id) {
        service.reactivate(id);

        return ResponseBuilder.ok(
                "Registro reactivado correctamente",
                null
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findBybId(@PathVariable Long id) {
        SupplierResponse supplier = service.findById(id);

        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, supplier);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAllPaginated(@ModelAttribute SupplierFilter filter) {
        Page<SupplierResponse> suppliers = service.findAll(filter);

        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, suppliers);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> findAll() {
        List<SupplierResponse> suppliers = service.findAllNoPaginated();

        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, suppliers);
    }
}