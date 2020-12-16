package com.zeylin.pricetracker.dao;

import com.zeylin.pricetracker.db.tables.Item;
import com.zeylin.pricetracker.db.tables.Location;
import com.zeylin.pricetracker.db.tables.Price;
import com.zeylin.pricetracker.dto.PriceListDto;
import com.zeylin.pricetracker.utils.PriceConverter;
import org.jooq.DSLContext;
import org.jooq.Record6;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ArchiveDAO {

    private final DSLContext dsl;

    public ArchiveDAO(DSLContext dsl) {
        this.dsl = dsl;
    }

    /**
     * List deleted prices.
     * @return a list of deleted prices
     */
    public List<PriceListDto> list() {
        Price p = Price.PRICE;
        Item i = Item.ITEM;
        Location l = Location.LOCATION;

        Result<Record6<Integer, Integer, String, Integer, LocalDate, String>> records = dsl.select(p.ID, p.ITEM_ID, i.NAME, p.AMOUNT, p.DATE, l.NAME)
                .from(p)
                .leftJoin(i).on(p.ITEM_ID.eq(i.ITEM_ID))
                .leftJoin(l).on(p.LOCATION_ID.eq(l.LOCATION_ID))
                .where(p.IS_DELETED.isTrue())
                .orderBy(p.ID.desc())
                .fetch();

        return records.map(r -> PriceConverter.convertToPriceListDto(p, i, l, r));
    }

    /**
     * Permanently delete a price entry.
     * @param id ID of the price to be deleted
     * @return the number of deleted records
     */
    public int deletePermanently(Integer id) {
        Price p = Price.PRICE;

        return dsl.delete(p)
                .where(p.ID.eq(id))
                .execute();
    }

    /**
     * Restore a price entry by removing it from deleted prices.
     * @param id ID of the price to be restored
     * @return the number of updated records
     */
    public int restore(Integer id) {
        Price p = Price.PRICE;

        LocalDateTime now = LocalDateTime.now();

        return dsl.update(p)
                .set(p.IS_DELETED, false)
                .set(p.UPDATE_DATE, now)
                .where(p.ID.eq(id))
                .execute();
    }

    /**
     * Clear archive by delete all archived prices permanently.
     */
    public void deleteAll() {
        Price p = Price.PRICE;

        dsl.delete(p)
                .where(p.IS_DELETED.isTrue())
                .execute();
    }
}
