package com.zeylin.pricetracker.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class UpdatePriceRequest {

    @NotNull
    private Integer id;
    private Integer itemId;
    private String itemName;
    @NotNull
    private Integer amount;
    @NotNull
    private LocalDate date;
    private Integer locationId;
    private String locationName;
    private Integer cityId;

}
