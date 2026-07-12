package com.isossoma.ratecatalog.controller;

import com.isossoma.ratecatalog.dto.filters.ItemSupplierPageableFilter;
import com.isossoma.ratecatalog.dto.request.CreateItemSupplier;
import com.isossoma.ratecatalog.dto.request.UpdateItemSupplier;
import com.isossoma.ratecatalog.dto.response.itemsupplier.ItemSupplierDetailResponse;
import com.isossoma.ratecatalog.dto.response.itemsupplier.ItemSupplierResponse;
import com.isossoma.ratecatalog.service.ItemSupplierService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.responses.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/item-supplier")
public class ItemSupplierController {
    private final ItemSupplierService service;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CreateItemSupplier createItemSupplier) {
        ItemSupplierResponse itemSupplier = service.create(createItemSupplier);

        return ResponseBuilder.created(
                "Item creado correctamente",
                itemSupplier
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable("id") Long id, @RequestBody UpdateItemSupplier updateItemSupplier) {
        ItemSupplierResponse itemSupplier = service.update(id, updateItemSupplier);

        return ResponseBuilder.ok(
                "Item actualizado correctamente",
                itemSupplier
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseBuilder.ok(
                "Item eliminado correctamente",
                null
        );
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<ApiResponse> reactivate(@PathVariable Long id) {
        service.reactivate(id);
        return ResponseBuilder.ok(
                "Item reactivado correctamente",
                null
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> search(@ModelAttribute ItemSupplierPageableFilter filter) {
        Page<ItemSupplierResponse> data = service.listAll(filter);

        return ResponseBuilder.ok("", data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable("id") Long id) {
        ItemSupplierDetailResponse data = service.getById(id);
        return ResponseBuilder.ok("", data);
    }
}