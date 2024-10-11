package com.techelevator.model;

public class Brewery {
    private int id;
    private String name;
    private String city;
    private String stateCode;
    private String description;

    public Brewery(){}

    public Brewery(int id, String name, String city, String stateCode, String description) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.stateCode = stateCode;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
