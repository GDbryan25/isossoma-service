package com.isossoma.ratecatalog.controller;

import com.isossoma.ratecatalog.dto.filters.ServiceItemFilter;
import com.isossoma.ratecatalog.dto.request.CreateServiceItem;
import com.isossoma.ratecatalog.dto.request.UpdateServiceItem;
import com.isossoma.ratecatalog.dto.response.item.ItemResponse;
import com.isossoma.ratecatalog.dto.response.item.ItemWithSupplierResponse;
import com.isossoma.ratecatalog.service.ItemService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.responses.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/service-item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;

    @GetMapping
    public ResponseEntity<ApiResponse> search(@ModelAttribute ServiceItemFilter filter, Pageable pageable) {
        Page<ItemResponse> data = service.search(filter, pageable);

        return ResponseBuilder.ok("", data);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CreateServiceItem createServiceItem) {
        ItemResponse item = service.create(createServiceItem);

        return ResponseBuilder.created(
                "Item creado correctamente",
                item
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody UpdateServiceItem updateServiceItem) {
        ItemResponse item = service.update(id, updateServiceItem);

        return ResponseBuilder.ok(
                "Item actualizado correctamente",
                item
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        service.softDeleteServiceItem(id);

        return ResponseBuilder.ok(
                "Item eliminado correctamente",
                null
        );
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<ApiResponse> reactivate(@PathVariable Long id) {
        service.reactivateServiceItem(id);

        return ResponseBuilder.ok(
                "Item reactivado correctamente",
                null
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getItemWithSuppliers(@PathVariable("id") Long id) {
        ItemWithSupplierResponse data = service.getItemWithSuppliers(id);

        return ResponseBuilder.ok("", data);
    }
}