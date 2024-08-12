package com.example.jackstylish.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDetailsDto {

    private String shipping;
    private String payment;
    private Integer subtotal;
    private Integer freight;
    private Integer total;
    private RecipientDto recipient;
    private List<ListDto> list;

}
