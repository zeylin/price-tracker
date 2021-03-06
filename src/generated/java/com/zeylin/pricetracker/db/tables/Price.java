/*
 * This file is generated by jOOQ.
 */
package com.zeylin.pricetracker.db.tables;


import com.zeylin.pricetracker.db.Keys;
import com.zeylin.pricetracker.db.Public;
import com.zeylin.pricetracker.db.tables.records.PriceRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row8;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Price extends TableImpl<PriceRecord> {

    private static final long serialVersionUID = 609907138;

    /**
     * The reference instance of <code>public.price</code>
     */
    public static final Price PRICE = new Price();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PriceRecord> getRecordType() {
        return PriceRecord.class;
    }

    /**
     * The column <code>public.price.id</code>.
     */
    public final TableField<PriceRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.price.item_id</code>.
     */
    public final TableField<PriceRecord, Integer> ITEM_ID = createField(DSL.name("item_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.price.amount</code>.
     */
    public final TableField<PriceRecord, Integer> AMOUNT = createField(DSL.name("amount"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.price.date</code>.
     */
    public final TableField<PriceRecord, LocalDate> DATE = createField(DSL.name("date"), org.jooq.impl.SQLDataType.LOCALDATE.nullable(false), this, "");

    /**
     * The column <code>public.price.location_id</code>.
     */
    public final TableField<PriceRecord, Integer> LOCATION_ID = createField(DSL.name("location_id"), org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.price.create_date</code>.
     */
    public final TableField<PriceRecord, LocalDateTime> CREATE_DATE = createField(DSL.name("create_date"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>public.price.update_date</code>.
     */
    public final TableField<PriceRecord, LocalDateTime> UPDATE_DATE = createField(DSL.name("update_date"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>public.price.is_deleted</code>.
     */
    public final TableField<PriceRecord, Boolean> IS_DELETED = createField(DSL.name("is_deleted"), org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.field("false", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * Create a <code>public.price</code> table reference
     */
    public Price() {
        this(DSL.name("price"), null);
    }

    /**
     * Create an aliased <code>public.price</code> table reference
     */
    public Price(String alias) {
        this(DSL.name(alias), PRICE);
    }

    /**
     * Create an aliased <code>public.price</code> table reference
     */
    public Price(Name alias) {
        this(alias, PRICE);
    }

    private Price(Name alias, Table<PriceRecord> aliased) {
        this(alias, aliased, null);
    }

    private Price(Name alias, Table<PriceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Price(Table<O> child, ForeignKey<O, PriceRecord> key) {
        super(child, key, PRICE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<PriceRecord> getPrimaryKey() {
        return Keys.PRICE_PKEY;
    }

    @Override
    public List<UniqueKey<PriceRecord>> getKeys() {
        return Arrays.<UniqueKey<PriceRecord>>asList(Keys.PRICE_PKEY);
    }

    @Override
    public List<ForeignKey<PriceRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<PriceRecord, ?>>asList(Keys.PRICE__PRICE_ITEM_ID_FKEY, Keys.PRICE__PRICE_LOCATION_ID_FKEY);
    }

    public Item item() {
        return new Item(this, Keys.PRICE__PRICE_ITEM_ID_FKEY);
    }

    public Location location() {
        return new Location(this, Keys.PRICE__PRICE_LOCATION_ID_FKEY);
    }

    @Override
    public Price as(String alias) {
        return new Price(DSL.name(alias), this);
    }

    @Override
    public Price as(Name alias) {
        return new Price(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Price rename(String name) {
        return new Price(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Price rename(Name name) {
        return new Price(name, null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<Integer, Integer, Integer, LocalDate, Integer, LocalDateTime, LocalDateTime, Boolean> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}
