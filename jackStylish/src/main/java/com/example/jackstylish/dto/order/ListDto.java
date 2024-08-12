package com.example.jackstylish.dto.order;

import com.example.jackstylish.dto.sendproduct.SendColorDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListDto {

    private String id;
    private String name;
    private Integer price;
    private SendColorDto color;
    private String size;
    private Integer qty;

}
