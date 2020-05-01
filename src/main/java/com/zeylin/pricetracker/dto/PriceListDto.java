package com.zeylin.pricetracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PriceListDto {

    private Integer id;
    private Integer itemId;
    private String itemName;
    private Integer amount;
    private String locationName;
    private LocalDate date;

}
