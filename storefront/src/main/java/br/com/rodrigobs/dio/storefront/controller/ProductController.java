package br.com.rodrigobs.dio.storefront.controller;

import br.com.rodrigobs.dio.storefront.controller.request.ProductSaveRequest;
import br.com.rodrigobs.dio.storefront.controller.response.ProductAvailableResponse;
import br.com.rodrigobs.dio.storefront.controller.response.ProductDetailResponse;
import br.com.rodrigobs.dio.storefront.controller.response.ProductSavedResponse;
import br.com.rodrigobs.dio.storefront.mapper.IProductMapper;
import br.com.rodrigobs.dio.storefront.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("products")
@AllArgsConstructor
public class ProductController {
    private final IProductService service;
    private final IProductMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductSavedResponse create(@RequestBody final ProductSaveRequest request){
        var entity = mapper.toEntity(request);
        entity = service.save(entity);
        return mapper.toResponse(entity);
    }

    @PostMapping("{id}/purchase")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void purchase(@PathVariable final UUID id){
        service.purchase(id);
    }

    @GetMapping
    List<ProductAvailableResponse> listAvailable(){
        var entities = service.findAllActive();
        return mapper.toResponse(entities);
    }

    @GetMapping("{id}")
    ProductDetailResponse findById(@PathVariable final UUID id){
        var dto = service.findInfo(id);
        return mapper.toResponse(dto);
    }
}
