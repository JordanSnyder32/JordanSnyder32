package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.model.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JdbcCustomerDaoTest extends BaseDaoTests {

    // Create constants to use through testing
    private static final Customer CUSTOMER_1 = new Customer(1, "Customer 1", "Addr 1-1", "Addr 1-2", "City 1", "S1", "11111");
    private static final Customer CUSTOMER_2 = new Customer(2, "Customer 2", "Addr 2-1", "Addr 2-2", "City 2", "S2", "22222");
    private static final Customer CUSTOMER_3 = new Customer(3, "Customer 3", "Addr 3-1", null, "City 3", "S3", "33333");
    private static final Customer CUSTOMER_4 = new Customer(4, "Customer 4", "Addr 4-1", null, "City 4", "S4", "44444");

    private static final List<Customer> CUSTOMER_LIST = new ArrayList<>(List.of(new Customer[]{CUSTOMER_1, CUSTOMER_2, CUSTOMER_3, CUSTOMER_4}));

    private JdbcCustomerDao dao;

    @Before
    public void setup() {
        dao = new JdbcCustomerDao(dataSource);
    }

    @Test
    public void getCustomerById_with_valid_id_returns_customer() {

        // Create variable type Customer to hold the result
        Customer result = dao.getCustomerById(CUSTOMER_1.getCustomerId());

        // Assert result is not null and that correct customer is returned
        Assert.assertNotNull("Returned null customer", result);
        assertCustomersMatch(CUSTOMER_1, result);
    }

    @Test
    public void getCustomerById_with_invalid_id_returns_null() {
        // Create variable type Customer to hold the result
        Customer result = dao.getCustomerById(5);

        // Assert result is null when provided invalid customer ID
        Assert.assertNull("Invalid ID should return null", result);
    }

    @Test
    public void getCustomers_retrieves_all_customers() {
        // Create list while calling on getCustomers
        List<Customer> results = dao.getCustomers();

        // Assert list is not null and that list length is the correct size
        Assert.assertNotNull(results);
        Assert.assertEquals(CUSTOMER_LIST.size(), results.size());

        // Use for loop to go through each object in list and compare results
        for (int i = 0; i < results.size(); i++) {
            assertCustomersMatch(CUSTOMER_LIST.get(i), results.get(i));
        }
    }

    @Test
    public void createCustomer_creates_customer() {
        // Create customer to add into the database and then make it the parameter for the createCustomer method
        Customer newCustomer = new Customer(5, "Customer 5", "Addr 5-1",
                null, "City 5", "S5", "55555");
        Customer createdCustomer = dao.createCustomer(newCustomer);

        // Get the id of the created customer
        int newId = createdCustomer.getCustomerId();

        // Test to make sure created customer has an id
        Assert.assertTrue("Created customer does not have an id", newId > 0);

        // Make sure created customer matches the new customer
        assertCustomersMatch(newCustomer, createdCustomer);
    }
    @Test
    public void updateCustomer_updates_customer() {
        // Create a customer to hold the values to update the existing customer with, making sure
        // to change all of the values aside from the customer id we intend to update
        Customer customer = new Customer();
        customer.setCustomerId(CUSTOMER_1.getCustomerId());
        customer.setName("New Customer 1");
        customer.setStreetAddress1("New address");
        customer.setStreetAddress2(null);
        customer.setCity("New City");
        customer.setState("NS");
        customer.setZipCode("12345");

        // call upon the method we are testing using the new customer to update
        Customer updatedCustomer = dao.updateCustomer(customer);

        // Test to make sure customer was properly updated and is not null
        Assert.assertNotNull("Returned null customer", updatedCustomer);
        assertCustomersMatch(customer, updatedCustomer);
    }

    private void assertCustomersMatch(Customer expected, Customer actual) {
        Assert.assertEquals(expected.getCustomerId(), actual.getCustomerId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getStreetAddress1(), actual.getStreetAddress1());
        Assert.assertEquals(expected.getStreetAddress2(), actual.getStreetAddress2());
        Assert.assertEquals(expected.getCity(), actual.getCity());
        Assert.assertEquals(expected.getState(), actual.getState());
        Assert.assertEquals(expected.getZipCode(), actual.getZipCode());
    }
}
