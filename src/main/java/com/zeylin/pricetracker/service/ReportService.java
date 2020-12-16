package com.zeylin.pricetracker.service;

public interface ReportService {

    byte[] generateMonthlyReport(Integer month);
    byte[] generateCategorisedMonthlyReport(Integer month);
}
