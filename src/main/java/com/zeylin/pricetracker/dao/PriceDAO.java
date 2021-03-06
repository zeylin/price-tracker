package com.zeylin.pricetracker.dao;

import com.zeylin.pricetracker.db.Sequences;
import com.zeylin.pricetracker.db.tables.Item;
import com.zeylin.pricetracker.db.tables.Location;
import com.zeylin.pricetracker.db.tables.Price;
import com.zeylin.pricetracker.dto.*;
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
     * @return number of updated records; 0 if none were updated.
     */
    public int updatePrice(UpdatePriceRequest request) {
        Price p = Price.PRICE;

        LocalDateTime now = LocalDateTime.now();

        return dslContext.update(p)
                .set(p.ITEM_ID, request.getItemId())
                .set(p.AMOUNT, request.getAmount())
                .set(p.DATE, request.getDate())
                .set(p.LOCATION_ID, request.getLocationId())
                .set(p.UPDATE_DATE, now)
                .where(p.ID.eq(request.getId())
                        .and(p.IS_DELETED.isFalse()))
                .execute();
    }

    /**
     * Delete a price by archiving it.
     * @param id ID of the price to be archived
     * @return the number of deleted records
     */
    public Integer delete(Integer id) {
        Price p = Price.PRICE;

        LocalDateTime now = LocalDateTime.now();

        return dslContext.update(p)
                .set(p.IS_DELETED, true)
                .set(p.UPDATE_DATE, now)
                .where(p.ID.eq(id))
                .execute();
    }

    /**
     * Search prices.
     * @param request parameters to search with
     * @return found prices, if any
     */
    public List<PriceListDto> search(PriceFilterRequest request) {
        Price p = Price.PRICE;
        Item i = Item.ITEM;
        Location l = Location.LOCATION;

        SelectConditionStep<Record6<Integer, Integer, String, Integer, LocalDate, String>> step =
                dslContext.select(p.ID, p.ITEM_ID, i.NAME, p.AMOUNT, p.DATE, l.NAME)
                        .from(p)
                        .leftJoin(i).on(p.ITEM_ID.eq(i.ITEM_ID))
                        .leftJoin(l).on(p.LOCATION_ID.eq(l.LOCATION_ID))
                        .where(p.IS_DELETED.isFalse());

        if (request.getItemId() != null) {
            step.and(p.ITEM_ID.eq(request.getItemId()));
        }

        if (request.getLocationId() != null) {
            step.and(p.LOCATION_ID.eq(request.getLocationId()));
        }

        if (request.getFromDate() != null && request.getToDate() != null) {
            step.and(p.DATE.between(request.getFromDate(), request.getToDate()));
        }

        if (request.getItemName() != null && !request.getItemName().isEmpty()) {
            step.and(i.NAME.like("%" + request.getItemName() + "%"));
        }

        step.orderBy(p.ID.desc());

        Result<Record6<Integer, Integer, String, Integer, LocalDate, String>> records = step.fetch();
        return records.map(r -> PriceConverter.convertToPriceListDto(p, i, l, r));
    }

    /**
     * List prices by month.
     * @param month integer representation of a month, from 1 (January) to 12 (December)
     * @return prices for a given month, if found
     */
    public List<PriceReportDto> listByMonth(Integer month) {
        Price p = Price.PRICE;
        Item i = Item.ITEM;
        Location l = Location.LOCATION;

        Result<Record3<String, Integer, LocalDate>> records = dslContext.select(i.NAME, p.AMOUNT, p.DATE)
                .from(p)
                .leftJoin(i).on(p.ITEM_ID.eq(i.ITEM_ID))
                .leftJoin(l).on(p.LOCATION_ID.eq(l.LOCATION_ID))
                .where(p.IS_DELETED.isFalse())
                .and(DSL.extract(p.DATE, DatePart.MONTH).eq(month))
                .orderBy(p.ID.desc())
                .fetch();

        return records.map(r -> PriceConverter.convertToPriceReportDto(p, i, r));
    }

}
