package com.lendingcatalog.model;

import java.util.UUID;

public class Tool implements CatalogItem {
    private String id;
    private String type;
    private String manufacturer;
    private int count;


    // getters, setters, and constructors
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Tool(String type, String manufacturer, int count) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.count = count;
    }

    public String toString() {
        return ("* " + type + System.lineSeparator()
                + " - Manufacturer: " + manufacturer + System.lineSeparator()
                + " - Count: " + count + System.lineSeparator()
                + " - ID number: " + id);
    }

    // implement interface methods, returning false when searchStr is null,
    // and returning true when search results match ignoring case sensitivity
    public boolean matchesName(String searchStr) {
        if (searchStr == null) {
            return false;
        }
        return type.toLowerCase().contains(searchStr.toLowerCase());
    }

    public boolean matchesCreator(String searchStr) {
        if (searchStr == null) {
            return false;
        }
        return manufacturer.toLowerCase().contains(searchStr.toLowerCase());
    }

    public boolean matchesYear(int searchStr) {
        return false;
    }

    public void registerItem() {
        id = UUID.randomUUID().toString();//generate random id number and assign value

    }


}
