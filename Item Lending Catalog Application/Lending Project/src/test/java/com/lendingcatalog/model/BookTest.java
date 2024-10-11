package com.lendingcatalog.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
class BookTest {

    @Test
    public void matchesName_tests() {
        //Arrange - use this book object to test method
        Book book = new Book("Test Name", "Test Author", LocalDate.of(2011, 11, 11));
        //Act and assert
        assertTrue(book.matchesName("Test Name")); //checks case sensitive
        assertTrue(book.matchesName("teSt naMe")); //checks case insensitive
        assertFalse(book.matchesName("Name Test")); //checks non matches
        assertFalse(book.matchesName(null)); //checks null
    }

    @Test
    public void matchesCreator_tests() {
        //Arrange - use this book object to test method
        Book book = new Book("Test Name", "Test Author", LocalDate.of(2011, 11, 11));
        //Act and assert
        assertTrue(book.matchesCreator("Test Author")); //checks case sensitive
        assertTrue(book.matchesCreator("teSt aUthOr")); //checks case insensitive
        assertFalse(book.matchesCreator("Author Test")); //checks non matches
        assertFalse(book.matchesCreator(null)); //checks null
    }
    @Test
    public void matchesYear_tests(){
        //Arrange
        Book book = new Book("Test Name", "Test Author", LocalDate.of(2011, 11, 11));
        //Act and Assert
        assertTrue(book.matchesYear(2011)); //Checks if correct year matches
        assertFalse(book.matchesYear(2012)); //Checks if incorrect year returns false
    }
}