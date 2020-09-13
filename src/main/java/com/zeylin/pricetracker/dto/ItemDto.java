package com.zeylin.pricetracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {

    private Integer itemId;
    private String name;
    private Integer categoryId;
}
