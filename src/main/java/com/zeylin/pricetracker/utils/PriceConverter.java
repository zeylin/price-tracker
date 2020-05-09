package com.zeylin.pricetracker.utils;

import com.zeylin.pricetracker.db.tables.Item;
import com.zeylin.pricetracker.db.tables.Location;
import com.zeylin.pricetracker.db.tables.Price;
import com.zeylin.pricetracker.dto.PriceDto;
import com.zeylin.pricetracker.dto.PriceListDto;
import org.jooq.Record;

public class PriceConverter {

    public static PriceDto convertToPriceDto(Price p, Item i, Location l, Record r) {
        PriceDto dto = new PriceDto();
        dto.setId(p.ID.get(r));
        dto.setItemId(p.ITEM_ID.get(r));
        dto.setItemName(i.NAME.get(r));
        dto.setAmount(p.AMOUNT.get(r));
        dto.setDate(p.DATE.get(r));
        dto.setLocationId(p.LOCATION_ID.get(r));
        dto.setLocationName(l.NAME.get(r));
        dto.setIsDeleted(p.IS_DELETED.get(r));
        return dto;
    }

    public static PriceListDto convertToPriceListDto(Price p, Item i, Location l, Record r) {
        PriceListDto dto = new PriceListDto();
        dto.setId(p.ID.get(r));
        dto.setItemId(p.ITEM_ID.get(r));
        dto.setItemName(i.NAME.get(r));
        dto.setAmount(p.AMOUNT.get(r));
        dto.setDate(p.DATE.get(r));
        dto.setLocationName(l.NAME.get(r));
        return dto;
    }

}
