package com.utnphones.utnPhones.dto;

/*
 un dto o una proyecion esto?
 */
public class MostCalledCitiesDto {
    private String cityName;
    private Integer numberOfCalls;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getNumberOfCalls() {
        return numberOfCalls;
    }

    public void setNumberOfCalls(Integer numberOfCalls) {
        this.numberOfCalls = numberOfCalls;
    }
}
