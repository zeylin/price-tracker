package com.zeylin.pricetracker.dao;

import com.zeylin.pricetracker.db.tables.Location;
import com.zeylin.pricetracker.dto.LocationDto;
import com.zeylin.pricetracker.utils.PriceConverter;
import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationDAO {

    private final DSLContext dsl;

    public LocationDAO(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<LocationDto> list() {
        Location l = Location.LOCATION;

        Result<Record3<Integer, String, Integer>> records = dsl.select(l.LOCATION_ID, l.NAME, l.CITY_ID)
                .from(l)
                .orderBy(l.LOCATION_ID)
                .fetch();

        return records.map(r -> PriceConverter.convertToLocationDto(l, r));
    }

    /**
     * Save a location.
     * @param locationName location name
     * @param cityId ID of location's city
     * @return ID of the saved location
     */
    public Integer saveLocation(String locationName, Integer cityId) {
        Location l = Location.LOCATION;

        Integer locationId = dsl.select(DSL.max(l.LOCATION_ID).plus(1))
                .from(l)
                .fetchOne()
                .value1();

        dsl.insertInto(l)
                .set(l.LOCATION_ID, locationId)
                .set(l.NAME, locationName)
                .set(l.CITY_ID, cityId)
                .execute();
        return locationId;
    }
}
