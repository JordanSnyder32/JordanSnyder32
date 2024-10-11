package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Brewery;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcBreweryDao implements BreweryDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcBreweryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Brewery> getBreweries() {
        List<Brewery> breweries = new ArrayList<>();
        String sql = "SELECT * FROM breweries ORDER BY name";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Brewery brewery = mapRowToBrewery(results);
                breweries.add(brewery);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return breweries;
    }

    @Override
    public Brewery getBreweryById(int id) {
        Brewery brewery = null;
        String sql = "SELECT * FROM breweries WHERE brewery_id = ?";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
            if (result.next()) {
                brewery = mapRowToBrewery(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return brewery;
    }

    @Override
    public List<Brewery> getBreweriesByUserId(int id) {
        List<Brewery> breweries = new ArrayList<>();
        String sql = "SELECT * FROM breweries b JOIN user_brewery ub ON b.brewery_id = ub.brewery_id " +
                "JOIN users u ON ub.user_id = u.user_id WHERE u.user_id = ? ORDER BY b.name";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

            while (results.next()) {
                Brewery brewery = mapRowToBrewery(results);
                breweries.add(brewery);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return breweries;
    }

    @Override
    public Brewery addBrewery(Brewery brewery) {
        Integer newBreweryId = 0;
        // Sql that adds new brewery if provided brewery does not exist
        String addSql = "INSERT INTO breweries (name, city, state_code, description) " +
                "VALUES (?, ?, ?, ?)";

        try {
            // Look for existing brewery by id
            Integer count = breweryCount(brewery);

            // if brewery exists, throw exception.
            if (count != null && count > 0) {
                throw new DaoException("Brewery already exists");
            } else {
                // Add new brewery
                newBreweryId = jdbcTemplate.queryForObject(addSql, Integer.class,
                        brewery.getName(), brewery.getCity(), brewery.getStateCode(), brewery.getDescription());

                // Ensure the insert worked and brewery ID is not null
                if (newBreweryId != null) {
                    return getBreweryById(newBreweryId);
                } else {
                    throw new DaoException("Failed to add the brewery, new brewery ID is null");
                }
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public Brewery updateBrewery(Brewery brewery) {
        // Sql that updates brewery if brewery already exists
        String sql = "UPDATE breweries SET name = ?, city = ?, state_code = ?, description = ? " +
                "WHERE  brewery_id = ?";

        try {
            Integer count = breweryCount(brewery);
            // if brewery exists, throw exception.
            if (count == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            } else {
                jdbcTemplate.update(sql, brewery.getName(), brewery.getCity(), brewery.getStateCode(),
                        brewery.getDescription(), brewery.getId());
                return getBreweryById(brewery.getId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    // Helper methods
    public Brewery mapRowToBrewery(SqlRowSet str) {
        Brewery brewery = new Brewery();
        brewery.setId(str.getInt("brewery_id"));
        brewery.setName(str.getString("name"));
        brewery.setCity(str.getString("city"));
        brewery.setStateCode(str.getString("state_code"));
        brewery.setDescription(str.getString("description"));
        return brewery;
    }

    public Integer breweryCount(Brewery brewery) {
        // Sql that checks for existence of provided brewery in database
        String sql = "SELECT COUNT(*) FROM breweries WHERE brewery_id = ?";
        try {
            // Look for existing brewery by id
            return jdbcTemplate.queryForObject(sql, Integer.class, brewery.getId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }
}
