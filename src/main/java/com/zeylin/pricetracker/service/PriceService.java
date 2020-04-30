package com.zeylin.pricetracker.service;

public interface PriceService {

    public void add();
    public void update();
    public void load();
    public void list();
    public void search();
    public void listPriceDynamics();
    public void delete();
    public void listRecentlyDeleted();
    public void deletePermanently();

}
