package com.example.jackstylish.dto.sendproduct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"color_code", "size", "stock"})
public class SendVariantDto {

    @JsonProperty("color_code")
    private String colorCode;
    @JsonProperty("size")
    private String variantSize;
    private int stock;

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getVariantSize() {
        return variantSize;
    }

    public void setVariantSize(String variantSize) {
        this.variantSize = variantSize;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
