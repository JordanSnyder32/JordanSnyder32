package com.techelevator.model;

public class BeerType {
    private int id;
    private String beerType;

    public BeerType(int id, String beerType) {
        this.id = id;
        this.beerType = beerType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeerType() {
        return beerType;
    }

    public void setBeerType(String beerType) {
        this.beerType = beerType;
    }

}
