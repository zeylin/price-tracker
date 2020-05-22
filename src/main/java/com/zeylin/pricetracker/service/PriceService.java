package com.zeylin.pricetracker.service;

import com.zeylin.pricetracker.dto.AddPriceRequest;
import com.zeylin.pricetracker.dto.PriceDto;
import com.zeylin.pricetracker.dto.PriceFilterRequest;
import com.zeylin.pricetracker.dto.PriceListDto;
import com.zeylin.pricetracker.dto.UpdatePriceRequest;

import java.util.List;

public interface PriceService {

    PriceDto add(AddPriceRequest request);
    void update(UpdatePriceRequest request);
    PriceDto load(Integer id);
    List<PriceListDto> list();
    List<PriceListDto> search(PriceFilterRequest request);
    void listPriceDynamics();
    void delete(Integer id);
    List<PriceListDto> listDeleted();
    void deletePermanently(Integer id);
    void restore(Integer id);

}
