package com.zeylin.pricetracker.dao;

import com.zeylin.pricetracker.db.Sequences;
import com.zeylin.pricetracker.db.tables.Item;
import com.zeylin.pricetracker.db.tables.Location;
import com.zeylin.pricetracker.db.tables.Price;
import com.zeylin.pricetracker.dto.AddPriceRequest;
import com.zeylin.pricetracker.dto.PriceDto;
import com.zeylin.pricetracker.dto.PriceListDto;
import com.zeylin.pricetracker.dto.UpdatePriceRequest;
import com.zeylin.pricetracker.exceptions.NotFoundException;
import com.zeylin.pricetracker.utils.PriceConverter;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PriceDAO {

    private final DSLContext dslContext;

    public PriceDAO(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    /**
     * Add a price entry.
     * @param request request containing price info
     * @return ID of the saved price entry
     */
    public Integer addPrice(AddPriceRequest request) {
        Price p = Price.PRICE;

        Integer id = dslContext.nextval(Sequences.PRICE_SEQ).intValue();
        LocalDateTime now = LocalDateTime.now();

        dslContext.insertInto(p)
                .set(p.ID, id)
                .set(p.ITEM_ID, request.getItemId())
                .set(p.AMOUNT, request.getAmount())
                .set(p.DATE, request.getDate())
                .set(p.LOCATION_ID, request.getLocationId())
                .set(p.CREATE_DATE, now)
                .set(p.UPDATE_DATE, now)
                .execute();

        return id;
    }

    /**
     * Save an item.
     * @param itemName item name
     * @return ID of the saved item
     */
    public Integer saveItem(String itemName) {
        Item i = Item.ITEM;

        Integer itemId = dslContext.select(DSL.max(i.ITEM_ID).plus(1))
                .from(i)
                .fetchOne()
                .value1();

        dslContext.insertInto(i)
                .set(i.ITEM_ID, itemId)
                .set(i.NAME, itemName)
                .execute();
        return itemId;
    }

    /**
     * Save a location.
     * @param locationName location name
     * @param cityId ID of the location's city
     * @return ID of the saved location
     */
    public Integer saveLocation(String locationName, Integer cityId) {
        Location l = Location.LOCATION;

        Integer locationId = dslContext.select(DSL.max(l.LOCATION_ID).plus(1))
                .from(l)
                .fetchOne()
                .value1();

        dslContext.insertInto(l)
                .set(l.LOCATION_ID, locationId)
                .set(l.NAME, locationName)
                .set(l.CITY_ID, cityId)
                .execute();
        return locationId;
    }

    /**
     * Load a price entry by ID.
     * @param id ID of the price entry
     * @return price entry corresponding to the ID
     */
    public PriceDto loadPrice(Integer id) {
        Price p = Price.PRICE;
        Item i = Item.ITEM;
        Location l = Location.LOCATION;

        Record8<Integer, Integer, String, Integer, LocalDate, Integer, String, Boolean> record = dslContext
                .select(p.ID, p.ITEM_ID, i.NAME, p.AMOUNT, p.DATE, p.LOCATION_ID, l.NAME, p.IS_DELETED)
                .from(p)
                .leftJoin(i).on(p.ITEM_ID.eq(i.ITEM_ID))
                .leftJoin(l).on(p.LOCATION_ID.eq(l.LOCATION_ID))
                .where(p.ID.eq(id))
                .fetchOne();

        if (record == null) {
            throw new NotFoundException("Price could not be found with id: " + id);
        }

        return record.map(r -> PriceConverter.convertToPriceDto(p, i, l, r));
    }

    /**
     * List all non-deleted prices.
     * @return a list of prices
     */
    public List<PriceListDto> list() {
        Price p = Price.PRICE;
        Item i = Item.ITEM;
        Location l = Location.LOCATION;

        Result<Record6<Integer, Integer, String, Integer, LocalDate, String>> records = dslContext.select(p.ID, p.ITEM_ID, i.NAME, p.AMOUNT, p.DATE, l.NAME)
                .from(p)
                .leftJoin(i).on(p.ITEM_ID.eq(i.ITEM_ID))
                .leftJoin(l).on(p.LOCATION_ID.eq(l.LOCATION_ID))
                .where(p.IS_DELETED.isFalse())
                .orderBy(p.ID.desc())
                .fetch();

        return records.map(r -> PriceConverter.convertToPriceListDto(p, i, l, r));
    }

    /**
     * Update price.
     * @param request request containing price info
     */
    public void updatePrice(UpdatePriceRequest request) {
        Price p = Price.PRICE;

        LocalDateTime now = LocalDateTime.now();

        dslContext.update(p)
                .set(p.ITEM_ID, request.getItemId())
                .set(p.AMOUNT, request.getAmount())
                .set(p.DATE, request.getDate())
                .set(p.LOCATION_ID, request.getLocationId())
                .set(p.UPDATE_DATE, now)
                .where(p.ID.eq(request.getId())).and(p.IS_DELETED.isFalse())
                .execute();
    }

    /**
     * Archive a price entry by setting its deleted flag to true.
     * @param id ID of the price to be archived
     * @return the number of updated records
     */
    public Integer archive(Integer id) {
        Price p = Price.PRICE;

        LocalDateTime now = LocalDateTime.now();

        return dslContext.update(p)
                .set(p.IS_DELETED, true)
                .set(p.UPDATE_DATE, now)
                .where(p.ID.eq(id))
                .execute();
    }

    /**
     * List deleted prices.
     * @return a list of deleted prices
     */
    public List<PriceListDto> listDeleted() {
        Price p = Price.PRICE;
        Item i = Item.ITEM;
        Location l = Location.LOCATION;

        Result<Record6<Integer, Integer, String, Integer, LocalDate, String>> records = dslContext.select(p.ID, p.ITEM_ID, i.NAME, p.AMOUNT, p.DATE, l.NAME)
                .from(p)
                .leftJoin(i).on(p.ITEM_ID.eq(i.ITEM_ID))
                .leftJoin(l).on(p.LOCATION_ID.eq(l.LOCATION_ID))
                .where(p.IS_DELETED.isTrue())
                .orderBy(p.ID.desc())
                .fetch();

        return records.map(r -> PriceConverter.convertToPriceListDto(p, i, l, r));
    }

    /**
     * Restore a price entry by removing it from deleted prices.
     * @param id ID of the price to be restored
     * @return the number of updated records
     */
    public int restore(Integer id) {
        Price p = Price.PRICE;

        LocalDateTime now = LocalDateTime.now();

        return dslContext.update(p)
                .set(p.IS_DELETED, false)
                .set(p.UPDATE_DATE, now)
                .where(p.ID.eq(id))
                .execute();
    }

    /**
     * Permanently delete a price entry.
     * @param id ID of the price to be deleted
     * @return the number of deleted records
     */
    public int deletePermanently(Integer id) {
        Price p = Price.PRICE;

        return dslContext.delete(p)
                .where(p.ID.eq(id))
                .execute();
    }

}
