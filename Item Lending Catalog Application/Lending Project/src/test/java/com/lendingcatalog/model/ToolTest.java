package com.lendingcatalog.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ToolTest {

    @Test
    void matchesNameTest() {
        //Arrange - use this book object to test method
        Tool tool = new Tool("Hammer", "Mario Bros", 1);
        //Act and assert
        assertTrue(tool.matchesName("Hammer")); //checks case sensitive
        assertTrue(tool.matchesName("hAmMeR")); //checks case insensitive
        assertFalse(tool.matchesName("Sword")); //checks non matches
        assertFalse(tool.matchesName(null)); //checks null
    }

    @Test
    void matchesCreatorTest() {
        //Arrange - use this book object to test method
        Tool tool = new Tool("Hammer", "Mario Bros", 1);
        //Act and assert
        assertTrue(tool.matchesCreator("Mario Bros")); //checks case sensitive
        assertTrue(tool.matchesCreator("mArIo BrOs")); //checks case insensitive
        assertFalse(tool.matchesCreator("Luigi Bros")); //checks non matches
        assertFalse(tool.matchesCreator(null)); //checks null
    }

}