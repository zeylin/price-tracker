package com.zeylin.pricetracker.controller;

import com.zeylin.pricetracker.service.PriceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add() {
        priceService.add();
    }

    @PutMapping
    public void update() {
        priceService.update();
    }

    @GetMapping(value = "/{id}")
    public void load(@PathVariable Integer id) {
        priceService.load();
    }

    @GetMapping
    public void list() {
        priceService.list();
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
        priceService.delete();
    }

    @GetMapping(value = "/deleted")
    public void listRecentlyDeleted() {
        priceService.listRecentlyDeleted();
    }

    @DeleteMapping(value = "/deleted/{id}")
    public void deletePermanently(@PathVariable Integer id) {
        priceService.deletePermanently();
    }

}
