package com.zeylin.pricetracker.dao;

import com.zeylin.pricetracker.db.tables.Item;
import com.zeylin.pricetracker.dto.ItemDto;
import com.zeylin.pricetracker.dto.ItemRequest;
import com.zeylin.pricetracker.utils.PriceConverter;
import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDAO {

    private final DSLContext dsl;

    public ItemDAO(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<ItemDto> list(ItemRequest request) {
        Item i = Item.ITEM;

        SelectJoinStep<Record3<Integer, String, Integer>> from = dsl.select(i.ITEM_ID, i.NAME, i.CATEGORY_ID)
                .from(i);

        if (request.getCategoryId() != null) {
            from.where(i.CATEGORY_ID.eq(request.getCategoryId()));
        }

        Result<Record3<Integer, String, Integer>> records = from.orderBy(i.ITEM_ID).fetch();
        return records.map(r -> PriceConverter.convertToItemDto(i, r));
    }
}
