package com.example.jackstylish.dto.sendproduct;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SendProductListDto {

    private List<SendProductDto> data;
    @JsonProperty("next_paging")
    private Integer nextPage;

    public List<SendProductDto> getData() {
        return data;
    }

    public void setData(List<SendProductDto> data) {
        this.data = data;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

}
