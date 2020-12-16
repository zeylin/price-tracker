package com.zeylin.pricetracker.controller;

import com.zeylin.pricetracker.dto.PriceListDto;
import com.zeylin.pricetracker.service.ArchiveService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("archive")
public class ArchiveController {

    private final ArchiveService archiveService;

    public ArchiveController(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    @GetMapping()
    public List<PriceListDto> list() {
        return archiveService.list();
    }

    @DeleteMapping(value = "/{id}")
    public void deletePermanently(@PathVariable Integer id) {
        archiveService.deletePermanently(id);
    }

    @PutMapping(value = "/restore/{id}")
    public void restore(@PathVariable Integer id) {
        archiveService.restore(id);
    }

    @DeleteMapping(value = "/all")
    public void deleteAll() {
        archiveService.deleteAll();
    }
}
