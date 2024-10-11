package com.lendingcatalog.model;

import java.time.LocalDate;
import java.util.UUID;

public class Movie implements CatalogItem {
    private String id;
    private String name;
    private String director;
    private LocalDate releaseDate;

    //getters, setters, and constructor


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Movie(String name, String director, LocalDate releaseDate) {
        this.name = name;
        this.director = director;
        this.releaseDate = releaseDate;
    }

    public String toString() { //create string that returns title, director, release date and id number
        return ("* " + name + System.lineSeparator()
        + " - Directed by: " + director + System.lineSeparator()
        + " - Release date: " + releaseDate + System.lineSeparator()
        + " - ID number: " + id);
    }

    // implement interface methods, returning false when searchStr is null,
    // and returning true when search results match ignoring case sensitivity
    public boolean matchesName(String searchStr) {
        if(searchStr == null){
            return false;
        }
        return name.toLowerCase().contains(searchStr.toLowerCase());
    }

    public boolean matchesCreator(String searchStr) {
        if(searchStr == null){
            return false;
        }
        return director.toLowerCase().contains(searchStr.toLowerCase());
    }

    public boolean matchesYear(int searchStr) {
        return releaseDate.getYear() == searchStr;
    }

    public void registerItem(){
        id = UUID.randomUUID().toString();//generate random id number and assign value

    }




}
