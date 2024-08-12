package com.example.jackstylish.dto.campaign;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"product_id", "picture", "story"})
public class SendCampaignDto {

    @JsonProperty("product_id")
    private Integer productId;
    private String picture;
    private String story;

}
