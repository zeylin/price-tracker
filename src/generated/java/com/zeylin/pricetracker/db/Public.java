/*
 * This file is generated by jOOQ.
 */
package com.zeylin.pricetracker.db;


import com.zeylin.pricetracker.db.tables.Category;
import com.zeylin.pricetracker.db.tables.City;
import com.zeylin.pricetracker.db.tables.FlywaySchemaHistory;
import com.zeylin.pricetracker.db.tables.Item;
import com.zeylin.pricetracker.db.tables.Location;
import com.zeylin.pricetracker.db.tables.Price;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 164361347;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.category</code>.
     */
    public final Category CATEGORY = Category.CATEGORY;

    /**
     * The table <code>public.city</code>.
     */
    public final City CITY = City.CITY;

    /**
     * The table <code>public.flyway_schema_history</code>.
     */
    public final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>public.item</code>.
     */
    public final Item ITEM = Item.ITEM;

    /**
     * The table <code>public.location</code>.
     */
    public final Location LOCATION = Location.LOCATION;

    /**
     * The table <code>public.price</code>.
     */
    public final Price PRICE = Price.PRICE;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        return Arrays.<Sequence<?>>asList(
            Sequences.PRICE_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            Category.CATEGORY,
            City.CITY,
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            Item.ITEM,
            Location.LOCATION,
            Price.PRICE);
    }
}
