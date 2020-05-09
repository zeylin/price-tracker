package com.zeylin.pricetracker.controller;

import com.zeylin.pricetracker.dto.AddPriceRequest;
import com.zeylin.pricetracker.dto.PriceDto;
import com.zeylin.pricetracker.dto.PriceListDto;
import com.zeylin.pricetracker.dto.UpdatePriceRequest;
import com.zeylin.pricetracker.service.PriceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PriceDto add(@RequestBody AddPriceRequest request) {
        return priceService.add(request);
    }

    @PutMapping
    public void update(@RequestBody UpdatePriceRequest request) {
        priceService.update(request);
    }

    @GetMapping(value = "/{id}")
    public PriceDto load(@PathVariable Integer id) {
        return priceService.load(id);
    }

    @GetMapping
    public List<PriceListDto> list() {
        return priceService.list();
    }

    @PostMapping(value = "/filtered")
    public void search() {
        priceService.search();
    }

    @GetMapping(value = "/dynamics")
    public void listPriceDynamics() {
        priceService.listPriceDynamics();
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) {
        priceService.delete(id);
    }

    @GetMapping(value = "/deleted")
    public List<PriceListDto> listDeleted() {
        return priceService.listDeleted();
    }

    @DeleteMapping(value = "/deleted/{id}")
    public void deletePermanently(@PathVariable Integer id) {
        priceService.deletePermanently(id);
    }

    @PutMapping(value = "/restore/{id}")
    public void restore(@PathVariable Integer id) {
        priceService.restore(id);
    }

}
