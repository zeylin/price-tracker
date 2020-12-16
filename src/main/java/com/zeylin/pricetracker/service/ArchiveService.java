package com.zeylin.pricetracker.service;

import com.zeylin.pricetracker.dto.PriceListDto;

import java.util.List;

public interface ArchiveService {

    List<PriceListDto> list();
    void deletePermanently(Integer id);
    void restore(Integer id);
    void deleteAll();
}
