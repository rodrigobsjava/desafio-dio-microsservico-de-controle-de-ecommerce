package br.com.rodrigobs.dio.warehouse.controller;

import br.com.rodrigobs.dio.warehouse.controller.request.StockSaveRequest;
import br.com.rodrigobs.dio.warehouse.controller.response.StockSavedResponse;
import br.com.rodrigobs.dio.warehouse.mapper.IStockMapper;
import br.com.rodrigobs.dio.warehouse.service.IStockService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("stocks")
@AllArgsConstructor
public class StockController {
    private final IStockService service;
    private final IStockMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    StockSavedResponse save(@RequestBody final StockSaveRequest request){
        var entity = mapper.toEntity(request);
        entity = service.save(entity);
        return mapper.toResponse(entity);
    }

    @PutMapping("{id}/release")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void release(@PathVariable final UUID id){
        service.release(id);
    }

    @DeleteMapping("{id}/inactive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void inactive(@PathVariable final UUID id){
        service.inactive(id);
    }
}
