/*
 * This file is generated by jOOQ.
 */
package com.zeylin.pricetracker.db.tables.records;


import com.zeylin.pricetracker.db.tables.Location;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LocationRecord extends UpdatableRecordImpl<LocationRecord> implements Record3<Integer, String, Integer> {

    private static final long serialVersionUID = -1157932512;

    /**
     * Setter for <code>public.location.location_id</code>.
     */
    public void setLocationId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.location.location_id</code>.
     */
    public Integer getLocationId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.location.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.location.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.location.city_id</code>.
     */
    public void setCityId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.location.city_id</code>.
     */
    public Integer getCityId() {
        return (Integer) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, String, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Location.LOCATION.LOCATION_ID;
    }

    @Override
    public Field<String> field2() {
        return Location.LOCATION.NAME;
    }

    @Override
    public Field<Integer> field3() {
        return Location.LOCATION.CITY_ID;
    }

    @Override
    public Integer component1() {
        return getLocationId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public Integer component3() {
        return getCityId();
    }

    @Override
    public Integer value1() {
        return getLocationId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public Integer value3() {
        return getCityId();
    }

    @Override
    public LocationRecord value1(Integer value) {
        setLocationId(value);
        return this;
    }

    @Override
    public LocationRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public LocationRecord value3(Integer value) {
        setCityId(value);
        return this;
    }

    @Override
    public LocationRecord values(Integer value1, String value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LocationRecord
     */
    public LocationRecord() {
        super(Location.LOCATION);
    }

    /**
     * Create a detached, initialised LocationRecord
     */
    public LocationRecord(Integer locationId, String name, Integer cityId) {
        super(Location.LOCATION);

        set(0, locationId);
        set(1, name);
        set(2, cityId);
    }
}
