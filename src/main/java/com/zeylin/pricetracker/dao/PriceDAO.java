package com.zeylin.pricetracker.dao;

import com.zeylin.pricetracker.db.Sequences;
import com.zeylin.pricetracker.db.tables.Item;
import com.zeylin.pricetracker.db.tables.Location;
import com.zeylin.pricetracker.db.tables.Price;
import com.zeylin.pricetracker.dto.AddPriceRequest;
import com.zeylin.pricetracker.dto.PriceDto;
import com.zeylin.pricetracker.dto.PriceListDto;
import com.zeylin.pricetracker.utils.PriceConverter;
import org.jooq.DSLContext;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Result;
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

        Record7<Integer, Integer, String, Integer, LocalDate, Integer, String> record = dslContext.select(p.ID, p.ITEM_ID, i.NAME, p.AMOUNT, p.DATE, p.LOCATION_ID, l.NAME)
                .from(p)
                .leftJoin(i).on(p.ITEM_ID.eq(i.ITEM_ID))
                .leftJoin(l).on(p.LOCATION_ID.eq(l.LOCATION_ID))
                .where(p.ID.eq(id))
                .fetchOne();

        PriceDto priceDto = record.map(r -> PriceConverter.convertToPriceDto(p, i, l, r));

        return priceDto;
    }

    /**
     * List all prices.
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
                .orderBy(p.ID.desc())
                .fetch();

        List<PriceListDto> list = records.map(r -> PriceConverter.convertToPriceListDto(p, i, l, r));

        return list;
    }

}
