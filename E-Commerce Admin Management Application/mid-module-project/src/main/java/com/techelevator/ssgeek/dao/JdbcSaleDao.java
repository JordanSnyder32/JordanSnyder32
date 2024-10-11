package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.exception.DaoException;
import com.techelevator.ssgeek.model.Product;
import com.techelevator.ssgeek.model.Sale;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcSaleDao implements SaleDao {
    private final JdbcTemplate jdbcTemplate;
    private final String SALE_SELECT_STATEMENT = "sale_id, s.customer_id, sale_date, ship_date, name";

    public JdbcSaleDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Sale getSaleById(int saleId) {
        Sale sale = null;
        String sql = "SELECT " + SALE_SELECT_STATEMENT + " FROM sale s JOIN customer c ON s.customer_id = c.customer_id WHERE sale_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, saleId);
            if (result.next()) {
                sale = mapRowToSale(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return sale;
    }

    @Override
    public List<Sale> getUnshippedSales() {
        List<Sale> sales = new ArrayList<>();
        // Create query that will select all sales that have ship_date equal to null
        String sql = "SELECT " + SALE_SELECT_STATEMENT + " FROM sale s JOIN customer c ON s.customer_id = c.customer_id " +
                "WHERE ship_date IS NULL ORDER BY sale_id";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Sale sale = mapRowToSale(results);
                sales.add(sale);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return sales;
    }

    @Override
    public List<Sale> getSalesByCustomerId(int customerId) {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT " + SALE_SELECT_STATEMENT + " FROM sale s JOIN customer c ON s.customer_id = c.customer_id " +
                "WHERE s.customer_id = ?";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, customerId);
            while (result.next()) {
                Sale sale = mapRowToSale(result);
                sales.add(sale);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return sales;
    }
    @Override
    public List<Sale> getSalesByProductId(int productId) {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT s.sale_id, s.customer_id, sale_date, ship_date, name FROM sale s JOIN line_Item li ON s.sale_id = li.sale_id " +
                "JOIN customer c ON s.customer_id = c.customer_id WHERE li.product_id = ? ORDER BY s.sale_id;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, productId);
            while (result.next()) {
                Sale sale = mapRowToSale(result);
                sales.add(sale);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return sales;
    }
    @Override
    public Sale createSale(Sale newSale) {
        int newId;
        String sql = "INSERT INTO Sale (customer_id, sale_date, ship_date) " +
                "VALUES (?, ?, ?) RETURNING sale_id;";
        try {
            newId = jdbcTemplate.queryForObject(sql, int.class, newSale.getCustomerId(),
                    newSale.getSaleDate(), newSale.getShipDate());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getSaleById(newId);
    }
    @Override
    public Sale updateSale(Sale updatedSale) {
        Sale afterSaleProduct = null;
        String sql = "UPDATE Sale " +
                "SET customer_id = ?, sale_date = ?, ship_date = ? WHERE sale_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, updatedSale.getCustomerId(),
                    updatedSale.getSaleDate(), updatedSale.getShipDate(),  updatedSale.getSaleId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                afterSaleProduct = getSaleById(updatedSale.getSaleId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return afterSaleProduct;
    }
    @Override
    public int deleteSaleById(int saleToDelete) {
        // Create queries for deleting sale from the table it's foreign key table and then it's primary key table
        String lineItemSql = "DELETE FROM line_item WHERE sale_id = ?";
        String sql = "DELETE FROM Sale WHERE sale_id = ? AND ship_date IS NULL";
        try {
            // Delete from foreign key table first (line_item table)
            jdbcTemplate.update(lineItemSql, saleToDelete);
            return jdbcTemplate.update(sql, saleToDelete);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }


    private Sale mapRowToSale(SqlRowSet results) {
        Sale sale = new Sale();
        sale.setSaleId(results.getInt("sale_id"));
        sale.setCustomerId(results.getInt("customer_id"));
        sale.setSaleDate(results.getDate("sale_date").toLocalDate());
        if(results.getDate("ship_date") != null) {
            sale.setShipDate(results.getDate("ship_date").toLocalDate());
        }
        sale.setCustomerName(results.getString("name"));
        return sale;
    }
}
