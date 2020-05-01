/*
 * This file is generated by jOOQ.
 */
package com.zeylin.pricetracker.db.tables.records;


import com.zeylin.pricetracker.db.tables.Price;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PriceRecord extends UpdatableRecordImpl<PriceRecord> implements Record7<Integer, Integer, Integer, LocalDate, Integer, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1201272635;

    /**
     * Setter for <code>public.price.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.price.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.price.item_id</code>.
     */
    public void setItemId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.price.item_id</code>.
     */
    public Integer getItemId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.price.amount</code>.
     */
    public void setAmount(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.price.amount</code>.
     */
    public Integer getAmount() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.price.date</code>.
     */
    public void setDate(LocalDate value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.price.date</code>.
     */
    public LocalDate getDate() {
        return (LocalDate) get(3);
    }

    /**
     * Setter for <code>public.price.location_id</code>.
     */
    public void setLocationId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.price.location_id</code>.
     */
    public Integer getLocationId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.price.create_date</code>.
     */
    public void setCreateDate(LocalDateTime value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.price.create_date</code>.
     */
    public LocalDateTime getCreateDate() {
        return (LocalDateTime) get(5);
    }

    /**
     * Setter for <code>public.price.update_date</code>.
     */
    public void setUpdateDate(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.price.update_date</code>.
     */
    public LocalDateTime getUpdateDate() {
        return (LocalDateTime) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<Integer, Integer, Integer, LocalDate, Integer, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Integer, Integer, Integer, LocalDate, Integer, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Price.PRICE.ID;
    }

    @Override
    public Field<Integer> field2() {
        return Price.PRICE.ITEM_ID;
    }

    @Override
    public Field<Integer> field3() {
        return Price.PRICE.AMOUNT;
    }

    @Override
    public Field<LocalDate> field4() {
        return Price.PRICE.DATE;
    }

    @Override
    public Field<Integer> field5() {
        return Price.PRICE.LOCATION_ID;
    }

    @Override
    public Field<LocalDateTime> field6() {
        return Price.PRICE.CREATE_DATE;
    }

    @Override
    public Field<LocalDateTime> field7() {
        return Price.PRICE.UPDATE_DATE;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getItemId();
    }

    @Override
    public Integer component3() {
        return getAmount();
    }

    @Override
    public LocalDate component4() {
        return getDate();
    }

    @Override
    public Integer component5() {
        return getLocationId();
    }

    @Override
    public LocalDateTime component6() {
        return getCreateDate();
    }

    @Override
    public LocalDateTime component7() {
        return getUpdateDate();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getItemId();
    }

    @Override
    public Integer value3() {
        return getAmount();
    }

    @Override
    public LocalDate value4() {
        return getDate();
    }

    @Override
    public Integer value5() {
        return getLocationId();
    }

    @Override
    public LocalDateTime value6() {
        return getCreateDate();
    }

    @Override
    public LocalDateTime value7() {
        return getUpdateDate();
    }

    @Override
    public PriceRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public PriceRecord value2(Integer value) {
        setItemId(value);
        return this;
    }

    @Override
    public PriceRecord value3(Integer value) {
        setAmount(value);
        return this;
    }

    @Override
    public PriceRecord value4(LocalDate value) {
        setDate(value);
        return this;
    }

    @Override
    public PriceRecord value5(Integer value) {
        setLocationId(value);
        return this;
    }

    @Override
    public PriceRecord value6(LocalDateTime value) {
        setCreateDate(value);
        return this;
    }

    @Override
    public PriceRecord value7(LocalDateTime value) {
        setUpdateDate(value);
        return this;
    }

    @Override
    public PriceRecord values(Integer value1, Integer value2, Integer value3, LocalDate value4, Integer value5, LocalDateTime value6, LocalDateTime value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PriceRecord
     */
    public PriceRecord() {
        super(Price.PRICE);
    }

    /**
     * Create a detached, initialised PriceRecord
     */
    public PriceRecord(Integer id, Integer itemId, Integer amount, LocalDate date, Integer locationId, LocalDateTime createDate, LocalDateTime updateDate) {
        super(Price.PRICE);

        set(0, id);
        set(1, itemId);
        set(2, amount);
        set(3, date);
        set(4, locationId);
        set(5, createDate);
        set(6, updateDate);
    }
}