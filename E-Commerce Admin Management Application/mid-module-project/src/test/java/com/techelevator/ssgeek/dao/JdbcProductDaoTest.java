package com.techelevator.ssgeek.dao;


import com.techelevator.ssgeek.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDaoTest extends BaseDaoTests{
    // Create constants to use through testing
    private static final Product PRODUCT_1 = new Product(1, "Product 1", "Description 1",  new BigDecimal("9.99"), "product-1.png");
    private static final Product PRODUCT_2 = new Product(2, "Product 2", "Description 2", new BigDecimal("19.00"), "product-2.png");
    private static final Product PRODUCT_3 = new Product(3, "Product 3", "Description 3", new BigDecimal("123.45"), "product-3.png");
    private static final Product PRODUCT_4 = new Product(4, "Product 4", "Description 4", new BigDecimal("0.99"), "product-4.png");

    private static final List<Product> PRODUCT_LIST = new ArrayList<>(List.of(new Product[]{PRODUCT_1, PRODUCT_2, PRODUCT_3, PRODUCT_4}));

    private JdbcProductDao dao;
    @Before
    public void setup() {
        dao = new JdbcProductDao(dataSource);
    }
    @Test
    public void getProductById_with_valid_id_returns_product() {

        // Create variable type Product to hold the result
        Product result = dao.getProductById(PRODUCT_1.getProductId());

        // Assert result is not null and that correct product is returned
        Assert.assertNotNull("Returned null product", result);
        assertProductsMatch(PRODUCT_1, result);
    }
    @Test
    public void getProductById_with_invalid_id_returns_null() {
        // Create variable type Product to hold the result
        Product result = dao.getProductById(5);

        // Assert result is null when provided invalid product ID
        Assert.assertNull("Invalid ID should return null", result);
    }
    @Test
    public void getProducts_retrieves_all_products() {
        // Create list while calling on getProducts
        List<Product> results = dao.getProducts();

        // Assert list is not null and that list length is the correct size
        Assert.assertNotNull(results);
        Assert.assertEquals(PRODUCT_LIST.size(), results.size());

        // Use for loop to go through each object in list and compare results
        for (int i = 0; i < results.size(); i++) {
            assertProductsMatch(PRODUCT_LIST.get(i), results.get(i));
        }
    }
    @Test
    public void getProductsWithNoSales_returns_complete_list_products_with_no_sales() {
        //Create list to hold no sale products returned by method
        List<Product> results = dao.getProductsWithNoSales();

        // Create list of expected products to be returned
        List<Product> expected = new ArrayList<>();
        expected.add(PRODUCT_3);

        // Assert list is not null, is the right length, and same objects
        Assert.assertNotNull(results);
        Assert.assertEquals(expected.size(), results.size());

        // Use for loop to go through each object in list and compare results
        for (int i = 0; i < results.size(); i++) {
            assertProductsMatch(expected.get(i), results.get(i));
        }
    }

    @Test
    public void createProduct_creates_product() {
        // Create product to add into the database and then make it the parameter for the createProduct method
        Product newProduct = new Product(5, "Product 5", "Description 5",  new BigDecimal("12.99"), "product-5.png");
        Product createdProduct = dao.createProduct(newProduct);

        // Get the id of the created customer
        int newId = createdProduct.getProductId();

        // Test to make sure created product has an id
        Assert.assertTrue("Created product does not have an id", newId > 0);

        // Make sure created product matches the new product
        assertProductsMatch(newProduct, createdProduct);
    }

    @Test
    public void updateProduct_updates_product() {
        // Create a product to hold the values to update the existing product with, making sure
        // to change all of the values aside from the product id we intend to update
        Product product = new Product();
        product.setProductId(PRODUCT_1.getProductId());
        product.setName("New Product 1");
        product.setDescription("New Description");
        product.setPrice(new BigDecimal("0.01"));
        product.setImageName("New png");

        // call upon the method we are testing using the new product to update
        Product updatedProduct = dao.updateProduct(product);

        // Test to make sure product was properly updated and is not null
        Assert.assertNotNull("Returned null product", updatedProduct);
        assertProductsMatch(product, updatedProduct);
    }
    @Test
    public void deleteProductById_deletes_the_correct_product(){
        // Delete a product from the database and assign results as an integer to show deletion occurred
        int results = dao.deleteProductById(PRODUCT_1.getProductId());

        // Create a product object for the deleted product to show that it has been deleted
        Product product = dao.getProductById(PRODUCT_1.getProductId());

        // Assert that the product returns null and that only 1 deletion occurred
        Assert.assertNull(product);
        Assert.assertEquals("Expected only 1 deletion", 1, results);
    }

    private void assertProductsMatch(Product expected, Product actual) {
        Assert.assertEquals(expected.getProductId(), actual.getProductId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
//        Assert.assertEquals(expected.getPrice(), actual.getPrice(), 0.001);
        Assert.assertEquals(expected.getImageName(), actual.getImageName());
    }
}
