package com.zeylin.pricetracker.service;

import com.zeylin.pricetracker.dto.AddPriceRequest;
import com.zeylin.pricetracker.dto.PriceDto;
import com.zeylin.pricetracker.dto.PriceListDto;

import java.util.List;

public interface PriceService {

    public PriceDto add(AddPriceRequest request);
    public void update();
    public PriceDto load(Integer id);
    public List<PriceListDto> list();
    public void search();
    public void listPriceDynamics();
    public void delete();
    public void listRecentlyDeleted();
    public void deletePermanently();

}
