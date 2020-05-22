package com.zeylin.pricetracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PriceFilterRequest {

    private Integer itemId;
    private String itemName;
    private Integer locationId;
    private LocalDate fromDate;
    private LocalDate toDate;

}
