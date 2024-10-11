package com.techelevator.model;

public class Beer {
    private int id;
    private int breweryId;
    private String name;
    private int beerTypeId;

    public Beer(int id, int breweryId, String name, int beerTypeId) {
        this.id = id;
        this.breweryId = breweryId;
        this.name = name;
        this.beerTypeId = beerTypeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(int breweryId) {
        this.breweryId = breweryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBeerTypeId() {
        return beerTypeId;
    }

    public void setBeerTypeId(int beerTypeId) {
        this.beerTypeId = beerTypeId;
    }
}
