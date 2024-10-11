package com.techelevator.controller;

import com.techelevator.dao.BreweryDao;
import com.techelevator.model.Brewery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.PermitAll;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/breweries")
@PermitAll
public class BreweryController {
@Autowired
    BreweryDao breweryDao;

@GetMapping("")
public List<Brewery> list(Principal principal) {
    return breweryDao.getBreweries();
}
@GetMapping("/{id}")
public Brewery get(@PathVariable int id) {
    Brewery brewery = breweryDao.getBreweryById(id);
    if(brewery == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return brewery;
}



}
