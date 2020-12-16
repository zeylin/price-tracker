package com.zeylin.pricetracker.service;

import com.zeylin.pricetracker.dao.ItemDAO;
import com.zeylin.pricetracker.dao.LocationDAO;
import com.zeylin.pricetracker.dao.PriceDAO;
import com.zeylin.pricetracker.dto.AddPriceRequest;
import com.zeylin.pricetracker.dto.PriceDto;
import com.zeylin.pricetracker.dto.PriceFilterRequest;
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
    private final ItemDAO itemDAO;
    private final LocationDAO locationDAO;

    public PriceServiceImpl(PriceDAO priceDAO, ItemDAO itemDAO, LocationDAO locationDAO) {
        this.priceDAO = priceDAO;
        this.itemDAO = itemDAO;
        this.locationDAO = locationDAO;
    }

    @Override
    public PriceDto add(AddPriceRequest request) {
        validate(request.getItemId(), request.getItemName(), request.getLocationId(), request.getLocationName());

        if (request.getItemId() == null && request.getItemName() != null) {
            LOGGER.info("Adding new item: {}", request.getItemName());

            Integer itemId = itemDAO.saveItem(request.getItemName());
            request.setItemId(itemId);
        }

        if (request.getLocationId() == null && request.getLocationName() != null) {
            LOGGER.info("Adding new location: {}", request.getLocationName());

            Integer locationId = locationDAO.saveLocation(request.getLocationName(), request.getCityId());
            request.setLocationId(locationId);
        }

        Integer id = priceDAO.addPrice(request);
        return priceDAO.loadPrice(id);
    }

    @Override
    public void update(UpdatePriceRequest request) {
        validate(request.getItemId(), request.getItemName(), request.getLocationId(), request.getLocationName());

        if (request.getItemId() == null && request.getItemName() != null) {
            LOGGER.info("Adding new item: {}", request.getItemName());

            Integer itemId = itemDAO.saveItem(request.getItemName());
            request.setItemId(itemId);
        }

        if (request.getLocationId() == null && request.getLocationName() != null) {
            LOGGER.info("Adding new location: {}", request.getLocationName());

            Integer locationId = locationDAO.saveLocation(request.getLocationName(), request.getCityId());
            request.setLocationId(locationId);
        }

        int updateCount = priceDAO.updatePrice(request);
        if (updateCount == 0) {
            throw new NotFoundException("Price not found with id: " + request.getId());
        }
    }

    /**
     * Validate a price.
     * @param itemId id of the item
     * @param itemName name of the item
     * @param locationId id of the location
     * @param locationName name of the location
     */
    private void validate(Integer itemId, String itemName, Integer locationId, String locationName) {
        if (itemId == null && itemName == null) {
            throw new InvalidArgumentException("Item ID cannot be null when item name is null");
        }

        if (locationId == null && locationName == null) {
            throw new InvalidArgumentException("Location ID cannot be null when location name is null");
        }
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
    public List<PriceListDto> search(PriceFilterRequest request) {
        return priceDAO.search(request);
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
}
