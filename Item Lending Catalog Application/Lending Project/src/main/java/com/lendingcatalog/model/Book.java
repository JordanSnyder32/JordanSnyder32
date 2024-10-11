package com.lendingcatalog.model;


import com.lendingcatalog.util.FileStorageService;
import com.lendingcatalog.util.exception.FileStorageException;

import java.time.LocalDate;
import java.util.UUID;

public class Book implements CatalogItem {

    private String id;
    private String title;
    private String author;
    private LocalDate publishDate;


    //getters, setters, and constructor

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Book(String title, String author, LocalDate publishDate) {
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
    }

    public String toString() { //create string that returns title, director, release date and id number
        return ("* " + title + System.lineSeparator()
                + " - Written by: " + author + System.lineSeparator()
                + " - Publish date: " + publishDate + System.lineSeparator()
                + " - ID number: " + id);

    }

    // implement interface methods, returning false when searchStr is null,
    // and returning true when search results match ignoring case sensitivity
    public boolean matchesName(String searchStr) {
        if (searchStr == null) {
            return false;
        }
        return title.toLowerCase().contains(searchStr.toLowerCase());
    }

    public boolean matchesCreator(String searchStr) {
        if (searchStr == null) {
            return false;
        }
        return author.toLowerCase().contains(searchStr.toLowerCase());
    }

    public boolean matchesYear(int searchStr) {
        return publishDate.getYear() == searchStr;
    }

    public void registerItem() { //generate random id number and assign value
        id = UUID.randomUUID().toString();

    }

}
