package com.techelevator.dao;

import com.techelevator.model.Beer;

public interface BeerDao {
    Beer addBeer();
    Beer getBeerById();
    void deleteBeer();


}
