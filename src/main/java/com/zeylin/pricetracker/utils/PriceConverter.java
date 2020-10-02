package com.zeylin.pricetracker.utils;

import com.zeylin.pricetracker.db.tables.Item;
import com.zeylin.pricetracker.db.tables.Location;
import com.zeylin.pricetracker.db.tables.Price;
import com.zeylin.pricetracker.dto.ItemDto;
import com.zeylin.pricetracker.dto.LocationDto;
import com.zeylin.pricetracker.dto.PriceDto;
import com.zeylin.pricetracker.dto.PriceListDto;
import com.zeylin.pricetracker.dto.PriceReportDto;
import org.jooq.Record;

import java.time.format.DateTimeFormatter;

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

    public static ItemDto convertToItemDto(Item i, Record r) {
        ItemDto dto = new ItemDto();
        dto.setItemId(i.ITEM_ID.get(r));
        dto.setName(i.NAME.get(r));
        dto.setCategoryId(i.CATEGORY_ID.get(r));
        return dto;
    }

    public static LocationDto convertToLocationDto(Location l, Record r) {
        LocationDto dto = new LocationDto();
        dto.setLocationId(l.LOCATION_ID.get(r));
        dto.setName(l.NAME.get(r));
        dto.setCityId(l.CITY_ID.get(r));
        return dto;
    }

    public static PriceReportDto convertToPriceReportDto(Price p, Item i, Record r) {
        PriceReportDto dto = new PriceReportDto();
        dto.setItemName(i.NAME.get(r));
        dto.setAmount(p.AMOUNT.get(r));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        dto.setDate(format.format(p.DATE.get(r)));
        return dto;
    }
}
