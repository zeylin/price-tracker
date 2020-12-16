package com.zeylin.pricetracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceReportDto {

    private String itemName;
    private Integer amount;
    private String date;
    private String category;
}
