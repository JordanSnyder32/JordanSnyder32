package com.techelevator.dao;

import com.techelevator.model.Brewery;

import java.util.List;

public interface BreweryDao {
    List<Brewery> getBreweries();

    Brewery getBreweryById(int id);
    List<Brewery> getBreweriesByUserId(int id);
    Brewery addBrewery(Brewery brewery);
    Brewery updateBrewery(Brewery brewery);


}
