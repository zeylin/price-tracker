package com.zeylin.pricetracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddPriceRequest {

    private Integer itemId;
    private String itemName;
    private Integer amount;
    private LocalDate date;
    private Integer locationId;
    private String locationName;
    private Integer cityId;

}
