package com.rntgroup.reactive.programming.impl.service.impl.decathlon.dto;

import java.util.List;

public class DecathlonSportsDto {

    private List<DecathlonSportDataDto> data;

    public List<DecathlonSportDataDto> getData() {
        return data;
    }

    public void setData(List<DecathlonSportDataDto> data) {
        this.data = data;
    }

}
