package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.model.Sale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcSaleDaoTest extends BaseDaoTests{
    // Create constants to use through testing
    private static final Sale SALE_1 = new Sale(1, 1, LocalDate.parse("2022-01-01"), null, "Customer 1");
    private static final Sale SALE_2 = new Sale(2, 1, LocalDate.parse("2022-02-01"), LocalDate.parse("2022-02-02"), "Customer 1");
    private static final Sale SALE_3 = new Sale(3, 2, LocalDate.parse("2022-03-01"), null, "Customer 2");
    private static final Sale SALE_4 = new Sale(4, 2, LocalDate.parse("2022-01-01"), LocalDate.parse("2022-01-02"), "Customer 2");

    private static final List<Sale> SALE_LIST = new ArrayList<>(List.of(new Sale[]{SALE_1, SALE_2, SALE_3, SALE_4}));

    private JdbcSaleDao dao;
    @Before
    public void setup() {
        dao = new JdbcSaleDao(dataSource);
    }

    @Test
    public void getSaleById_with_valid_id_returns_sale() {

        // Create variables type Sale to hold the result of a null and not null ship date
        Sale nullShipDate = dao.getSaleById(SALE_1.getSaleId());
        Sale notNullShipDate = dao.getSaleById(SALE_2.getSaleId());

        // Assert result is not null and that correct sale is returned
        Assert.assertNotNull("Returned null sale with valid ID", nullShipDate);
        Assert.assertNotNull("Returned null sale with valid ID", notNullShipDate);

        assertSalesMatch(SALE_1, nullShipDate);
        assertSalesMatch(SALE_2, notNullShipDate);
    }
    @Test
    public void getSaleById_with_invalid_id_returns_null() {
        // Create variable type Sale to hold the result
        Sale result = dao.getSaleById(5);

        // Assert result is null when provided invalid sale ID
        Assert.assertNull("Invalid ID should return null", result);
    }

    @Test
    public void getUnshippedSales_returns_complete_list_sales_with_null_ship_dates() {
        //Create list to hold unshipped sales returned by method
        List<Sale> results = dao.getUnshippedSales();

        // Create list of expected sales to be returned
        List<Sale> expected = new ArrayList<>();
        expected.add(SALE_1);
        expected.add(SALE_3);

        // Assert list is not null, is the right length, and same objects
        Assert.assertNotNull(results);
        Assert.assertEquals(expected.size(), results.size());

        // Use for loop to go through each object in list and compare results
        for (int i = 0; i < results.size(); i++) {
            assertSalesMatch(expected.get(i), results.get(i));
        }
    }
    @Test
    public void getSalesByCustomerId_returns_complete_list_sales() {
        //Create list to hold sales returned by method
        List<Sale> results = dao.getSalesByCustomerId(1);

        // Create list of expected sales to be returned
        List<Sale> expectedNotNull = new ArrayList<>();
        expectedNotNull.add(SALE_1);
        expectedNotNull.add(SALE_2);

        // Assert list is not null, is the right length, and same objects
        Assert.assertNotNull(results);
        Assert.assertEquals(expectedNotNull.size(), results.size());

        // Use for loop to go through each object in list and compare results
        for (int i = 0; i < results.size(); i++) {
            assertSalesMatch(expectedNotNull.get(i), results.get(i));
        }
    }
    @Test
    public void getSalesByProductId_returns_complete_list_sales() {
        //Create list to hold sales returned by method
        List<Sale> results = dao.getSalesByProductId(1);

        // Create list of expected sales to be returned
        List<Sale> expected = new ArrayList<>();
        expected.add(SALE_1);
        expected.add(SALE_2);
        expected.add(SALE_3);

        // Assert list is not null, is the right length, and same objects
        Assert.assertNotNull(results);
        Assert.assertEquals(expected.size(), results.size());

        // Use for loop to go through each object in list and compare results
        for (int i = 0; i < results.size(); i++) {
            assertSalesMatch(expected.get(i), results.get(i));
        }
    }
    @Test
    public void createSale_creates_sale() {
        // Create sale to add into the database and then make it the parameter for the createSale method
        Sale newSale = new Sale(5, 1, LocalDate.parse("2022-01-01"), LocalDate.parse("2022-01-02"), "Customer 1");
        Sale createdSale = dao.createSale(newSale);

        // Get the id of the created sales
        int newId = createdSale.getSaleId();

        // Test to make sure created sale has an id
        Assert.assertTrue("Created sale does not have an id", newId > 0);

        // Make sure created sale matches the new sale
        assertSalesMatch(newSale, createdSale);
    }

    @Test
    public void updateSale_updates_sale() {
        // Create a sale to hold the values to update the existing sale with, making sure
        // to change all of the values aside from the sale id we intend to update
       Sale sale = new Sale();
        sale.setSaleId(SALE_1.getSaleId());
        sale.setCustomerId(2);
        sale.setSaleDate(LocalDate.parse("2022-01-02"));
        sale.setShipDate(LocalDate.parse("2022-01-15"));
        sale.setCustomerName("Customer 2");

        // call upon the method we are testing using the new sale to update
        Sale updatedSale = dao.updateSale(sale);

        // Test to make sure sale was properly updated and is not null
        Assert.assertNotNull("Returned null sale", updatedSale);
        assertSalesMatch(sale, updatedSale);
    }
    @Test
    public void deleteSaleById_deletes_the_correct_sale(){
        // Delete a sale from the database and assign results as an integer to show deletion occurred
        int results = dao.deleteSaleById(SALE_1.getSaleId());

        // Create a sale object for the deleted sale to show that it has been deleted
        Sale sale = dao.getSaleById(SALE_1.getSaleId());

        // Assert that the sale returns null and that only 1 deletion occurred
        Assert.assertNull(sale);
        Assert.assertEquals("Expected only 1 deletion", 1, results);
    }

    private void assertSalesMatch(Sale expected, Sale actual) {
        Assert.assertEquals(expected.getSaleId(), actual.getSaleId());
        Assert.assertEquals(expected.getCustomerId(), actual.getCustomerId());
        Assert.assertEquals(expected.getSaleId(), actual.getSaleId());
        Assert.assertEquals(expected.getShipDate(), actual.getShipDate());
        Assert.assertEquals(expected.getCustomerName(), actual.getCustomerName());
    }
}
