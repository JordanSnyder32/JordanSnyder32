package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.model.LineItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcLineItemDaoTest extends BaseDaoTests {
    // Create constants to use through testing
    private static final LineItem LINE_ITEM_1 = new LineItem(1, 1, 1, 1, "Product 1", new BigDecimal("9.99"));
    private static final LineItem LINE_ITEM_2 = new LineItem(2, 1, 2, 1, "Product 2", new BigDecimal("19.00"));
    private static final LineItem LINE_ITEM_3 = new LineItem(3, 1, 4, 1, "Product 4", new BigDecimal("0.99"));

    private JdbcLineItemDao dao;
    @Before
    public void setup() {
        dao = new JdbcLineItemDao(dataSource);
    }

    @Test
    public void getLineItemsBySaleId_returns_complete_list_line_items() {
        //Create list to hold line items returned by method
        List<LineItem> results = dao.getLineItemsBySaleId(1);

        // Create list of expected line items to be returned
        List<LineItem> expected = new ArrayList<>();
        expected.add(LINE_ITEM_1);
        expected.add(LINE_ITEM_2);
        expected.add(LINE_ITEM_3);

        // Assert list is not null, is the right length, and same objects
        Assert.assertNotNull(results);
        Assert.assertEquals(expected.size(), results.size());


        // Use for loop to go through each object in list and compare results
        for (int i = 0; i < results.size(); i++) {
            assertLineItemsMatch(expected.get(i), results.get(i));
        }
    }

    private void assertLineItemsMatch(LineItem expected, LineItem actual) {
        Assert.assertEquals(expected.getLineItemId(), actual.getLineItemId());
        Assert.assertEquals(expected.getSaleId(), actual.getSaleId());
        Assert.assertEquals(expected.getProductId(), actual.getProductId());
        Assert.assertEquals(expected.getQuantity(), actual.getQuantity());
        Assert.assertEquals(expected.getProductName(), actual.getProductName());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
    }
}
