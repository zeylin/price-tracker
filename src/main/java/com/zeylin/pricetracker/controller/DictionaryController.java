package com.zeylin.pricetracker.controller;

import com.zeylin.pricetracker.dao.ItemDAO;
import com.zeylin.pricetracker.dao.LocationDAO;
import com.zeylin.pricetracker.dto.ItemDto;
import com.zeylin.pricetracker.dto.ItemRequest;
import com.zeylin.pricetracker.dto.LocationDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    private final ItemDAO itemDAO;
    private final LocationDAO locationDAO;

    public DictionaryController(ItemDAO itemDAO, LocationDAO locationDAO) {
        this.itemDAO = itemDAO;
        this.locationDAO = locationDAO;
    }

    @GetMapping(value = "/items")
    public List<ItemDto> items(@RequestBody ItemRequest request) {
        return itemDAO.list(request);
    }

    @GetMapping(value = "/locations")
    public List<LocationDto> locations() {
        return locationDAO.list();
    }
}
