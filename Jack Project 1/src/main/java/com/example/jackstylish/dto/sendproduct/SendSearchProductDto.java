package com.example.jackstylish.dto.sendproduct;

import java.util.List;

public class SendSearchProductDto {

    private List<SearchProductDto> data;

    private Integer nextPaging;

    public List<SearchProductDto> getData() {
        return data;
    }

    public void setData(List<SearchProductDto> data) {
        this.data = data;
    }

    public Integer getNextPage() {
        return nextPaging;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPaging = nextPage;
    }

}
