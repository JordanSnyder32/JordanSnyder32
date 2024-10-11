package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.exception.DaoException;
import com.techelevator.ssgeek.model.Product;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcProductDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Product getProductById(int productId) {
        Product product = null;
        String sql = "SELECT * FROM Product WHERE product_id = ?";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, productId);
            if (result.next()) {
                product = mapRowToProduct(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return product;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product ORDER BY product_id;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Product product = mapRowToProduct(results);
                products.add(product);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return products;
    }

    @Override
    public List<Product> getProductsWithNoSales() {
        List<Product> products = new ArrayList<>();
        // Create query that will select all products that are not listed in the line_item table
        String sql = "SELECT * FROM Product WHERE product_id NOT IN (SELECT product_id FROM line_item) ORDER BY product_id;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Product product = mapRowToProduct(results);
                products.add(product);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return products;
    }

    @Override
    public Product createProduct(Product newProduct) {
        int newId;
        String sql = "INSERT INTO Product (name, description, price, image_name) " +
                "VALUES (?, ?, ?, ?) RETURNING product_id;";
        try {
            newId = jdbcTemplate.queryForObject(sql, int.class, newProduct.getName(),
                    newProduct.getDescription(),
                    newProduct.getPrice(), newProduct.getImageName());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getProductById(newId);
    }

    @Override
    public Product updateProduct(Product updatedProduct) {
        Product afterUpdateProduct = null;
        String sql = "UPDATE Product " +
                "SET name = ?, description = ?, price = ?, image_name = ? WHERE product_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, updatedProduct.getName(),
                    updatedProduct.getDescription(), updatedProduct.getPrice(),
                    updatedProduct.getImageName(), updatedProduct.getProductId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                afterUpdateProduct = getProductById(updatedProduct.getProductId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return afterUpdateProduct;
    }

    @Override
    public int deleteProductById(int productToDeleteId) {
        // Create queries for deleting product from the table it's foreign key table and then it's primary key table
        String lineItemSql = "DELETE FROM line_item WHERE product_id = ?";
        String sql = "DELETE FROM Product WHERE product_id = ?";
        try {
            // Delete from foreign key table first (line_item table)
            jdbcTemplate.update(lineItemSql, productToDeleteId);
            return jdbcTemplate.update(sql, productToDeleteId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Product mapRowToProduct(SqlRowSet results) {
        Product product = new Product();
        product.setProductId(results.getInt("product_id"));
        product.setName(results.getString("name"));
        product.setDescription(results.getString("description"));
        product.setPrice(results.getBigDecimal("price"));
        product.setImageName(results.getString("image_name"));
        return product;
    }
}
