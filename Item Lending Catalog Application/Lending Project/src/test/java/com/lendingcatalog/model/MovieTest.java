package com.lendingcatalog.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void matchesNameTest() {
        //Arrange - use this book object to test method
        Movie movie = new Movie("Test Name", "Test Director", LocalDate.of(2011, 11, 11));
        //Act and assert
        assertTrue(movie.matchesName("Test Name")); //checks case sensitive
        assertTrue(movie.matchesName("teSt naMe")); //checks case insensitive
        assertFalse(movie.matchesName("Name Test")); //checks non matches
        assertFalse(movie.matchesName(null)); //checks null
    }

    @Test
    void matchesCreatorTest() {
        //Arrange - use this book object to test method
        Movie movie = new Movie("Test Name", "Test Director", LocalDate.of(2011, 11, 11));
        //Act and assert
        assertTrue(movie.matchesCreator("Test Director")); //checks case sensitive
        assertTrue(movie.matchesCreator("teSt diRecTor")); //checks case insensitive
        assertFalse(movie.matchesCreator("Director Test")); //checks non matches
        assertFalse(movie.matchesCreator(null)); //checks null
    }

    @Test
    void matchesYearTest() {
        //Arrange
        Movie movie = new Movie("Test Name", "Test Director", LocalDate.of(2011, 11, 11));
        //Act and Assert
        assertTrue(movie.matchesYear(2011)); //Checks if correct year matches
        assertFalse(movie.matchesYear(2012)); //Checks if incorrect year returns false
    }
}