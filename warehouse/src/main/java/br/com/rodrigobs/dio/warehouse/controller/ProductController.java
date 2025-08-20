package br.com.rodrigobs.dio.warehouse.controller;

import br.com.rodrigobs.dio.warehouse.controller.request.ProductSaveRequest;
import br.com.rodrigobs.dio.warehouse.controller.response.ProductDetailResponse;
import br.com.rodrigobs.dio.warehouse.controller.response.ProductSavedResponse;
import br.com.rodrigobs.dio.warehouse.mapper.IProductMapper;
import br.com.rodrigobs.dio.warehouse.service.IProductQueryService;
import br.com.rodrigobs.dio.warehouse.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("products")
@AllArgsConstructor
public class ProductController {

    private final IProductService service;
    private final IProductQueryService queryService;
    private final IProductMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductSavedResponse create(@RequestBody final ProductSaveRequest request) {
        var entity = mapper.toEntity(request);
        entity = service.save(entity);
        return mapper.toSavedResponse(entity);
    }

    @PostMapping("{id}/purchase")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void purchase(@PathVariable final UUID id) {
        service.purchase(id);
    }

    @GetMapping("{id}")
    ProductDetailResponse findById(@PathVariable final UUID id) {
        var dto = queryService.findById(id);
        return mapper.toDetailResponse(dto);
    }

}
