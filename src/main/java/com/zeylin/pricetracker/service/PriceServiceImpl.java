package com.zeylin.pricetracker.service;

import com.zeylin.pricetracker.dao.PriceDAO;
import com.zeylin.pricetracker.dto.AddPriceRequest;
import com.zeylin.pricetracker.dto.PriceDto;
import com.zeylin.pricetracker.dto.PriceListDto;
import com.zeylin.pricetracker.dto.UpdatePriceRequest;
import com.zeylin.pricetracker.exceptions.InvalidArgumentException;
import com.zeylin.pricetracker.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceServiceImpl.class);

    private final PriceDAO priceDAO;

    public PriceServiceImpl(PriceDAO priceDAO) {
        this.priceDAO = priceDAO;
    }

    @Override
    public PriceDto add(AddPriceRequest request) {

        if (request.getItemId() == null && request.getItemName() == null) {
            throw new InvalidArgumentException("Item ID cannot be null when item name is null");
        }

        if (request.getLocationId() == null && request.getLocationName() == null) {
            throw new InvalidArgumentException("Location ID cannot be null when location name is null");
        }

        if (request.getItemId() == null && request.getItemName() != null) {
            LOGGER.info("Adding new item: {}", request.getItemName());

            Integer itemId = priceDAO.saveItem(request.getItemName());
            request.setItemId(itemId);
        }

        if (request.getLocationId() == null && request.getLocationName() != null) {
            LOGGER.info("Adding new location: {}", request.getLocationName());

            Integer locationId = priceDAO.saveLocation(request.getLocationName(), request.getCityId());
            request.setLocationId(locationId);
        }

        Integer id = priceDAO.addPrice(request);
        return priceDAO.loadPrice(id);
    }

    @Override
    public void update(UpdatePriceRequest request) {

        if (request.getId() == null) {
            throw new InvalidArgumentException("ID cannot be null");
        }

        if (request.getItemId() == null && request.getItemName() == null) {
            throw new InvalidArgumentException("Item ID cannot be null when item name is null");
        }

        if (request.getLocationId() == null && request.getLocationName() == null) {
            throw new InvalidArgumentException("Location ID cannot be null when location name is null");
        }

        if (request.getItemId() == null && request.getItemName() != null) {
            LOGGER.info("Adding new item: {}", request.getItemName());

            Integer itemId = priceDAO.saveItem(request.getItemName());
            request.setItemId(itemId);
        }

        if (request.getLocationId() == null && request.getLocationName() != null) {
            LOGGER.info("Adding new location: {}", request.getLocationName());

            Integer locationId = priceDAO.saveLocation(request.getLocationName(), request.getCityId());
            request.setLocationId(locationId);
        }

        priceDAO.updatePrice(request);

    }

    @Override
    public PriceDto load(Integer id) {
        return priceDAO.loadPrice(id);
    }

    @Override
    public List<PriceListDto> list() {
        return priceDAO.list();
    }

    @Override
    public void search() {

    }

    @Override
    public void listPriceDynamics() {

    }

    @Override
    public void delete(Integer id) {
        Integer deleteCount = priceDAO.delete(id);

        if (deleteCount == 0) {
            throw new NotFoundException("Price could not be found with id: " + id);
        }
    }

    @Override
    public List<PriceListDto> listDeleted() {
        return priceDAO.listDeleted();
    }

    @Override
    public void deletePermanently() {

    }

    @Override
    public void restore(Integer id) {
        int restoreCount = priceDAO.restore(id);

        if (restoreCount == 0) {
            throw new NotFoundException("Price could not be found with id: " + id);
        }
    }

}
