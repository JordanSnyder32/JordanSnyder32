package com.techelevator;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.*;

public class App {

    // The regex string to split the Strings in the dataset.
    private static final String FIELD_DELIMITER = "\\|";

    private static final int TITLE_FIELD = 0;
    private static final int AUTHOR_FIELD = 1;
    private static final int PUBLISHED_YEAR_FIELD = 2;
    private static final int PRICE_FIELD = 3;

    private final Scanner keyboard = new Scanner(System.in);

    private List<String> titles = new ArrayList<>();
    private List<String> authors = new ArrayList<>();
    private List<Integer> publishedYears = new ArrayList<>();
    private List<BigDecimal> prices = new ArrayList<>();

    public static void main(String[] args) {

        App app = new App();
        app.loadData();
        app.run();

    }

    private void loadData() {

        String[] dataset = Dataset.load();

        for (int i = 0; i < dataset.length; i++) { //separate the user input into strings and add them to their respective lists.
            String[] bookInfo = dataset[i].split(FIELD_DELIMITER);
            titles.add(bookInfo[0]);
            authors.add(bookInfo[1]);
            publishedYears.add(Integer.parseInt(bookInfo[2])); //convert the year string into an Integer
            BigDecimal priceAsDecimal = new BigDecimal(bookInfo[3]);  //convert the price string into big decimal
            prices.add(priceAsDecimal);
        }

        /*
         Requirement: 1
         Populate the instance variables `titles`, `authors`, `publishedYears`,
         and `prices` by splitting each string in the `dataset` array and adding
         the individual fields to the appropriate list.
         See README for additional details.
         COMPLETED
         */

    }

    private void run() {

        while (true) {
            // Main menu loop
            printMainMenu();
            int mainMenuSelection = promptForMenuSelection("Please choose an option: ");
            if (mainMenuSelection == 1) {
                // Display data and subsets loop
                while (true) {
                    printDataAndSubsetsMenu();
                    int dataAndSubsetsMenuSelection = promptForMenuSelection("Please choose an option: ");
                    if (dataAndSubsetsMenuSelection == 1) {
                        displayDataset(Dataset.load());
                    } else if (dataAndSubsetsMenuSelection == 2) {
                        displayTitlesList(titles);
                    } else if (dataAndSubsetsMenuSelection == 3) {
                        displayAuthorsList(authors);
                    } else if (dataAndSubsetsMenuSelection == 4) {
                        displayPublishedYearsList(publishedYears);
                    } else if (dataAndSubsetsMenuSelection == 5) {
                        displayPricesList(prices);
                    } else if (dataAndSubsetsMenuSelection == 0) {
                        break;
                    }
                }
            } else if (mainMenuSelection == 2) {
                while (true) {
                    printSearchBooksMenu();
                    int searchBooksMenuSelection = promptForMenuSelection("Please choose an option: ");
                    if (searchBooksMenuSelection == 1) {
                        // Search by title
                        String filterTitle = promptForString("Enter title: ");
                        /*
                         Requirement: 3b
                         Replace `displayTitlesList(titles)` with calls to the
                         `filterByTitle()` and `displaySearchResults()` methods.
                         */
                        List<Integer> matchedTitles = filterByTitle(filterTitle);
                        displaySearchResults(matchedTitles, TITLE_FIELD);
                    } else if (searchBooksMenuSelection == 2) {
                        // Search by author
                        String filterAuthor = promptForString("Enter author: ");
                        /*
                         Requirement: 4b
                         Replace `displayAuthorsList(authors)` with calls to the
                         `filterByAuthor()` and `displaySearchResults()` methods.
                         COMPLETED
                         */
                        List<Integer> matchedAuthors = (filterByAuthor(filterAuthor));
                        displaySearchResults(matchedAuthors, AUTHOR_FIELD);
                    } else if (searchBooksMenuSelection == 3) {
                        // Search by published year
                        int filterYear = promptForPublishedYear("Enter date (YYYY): ");
                        /*
                         Requirement: 5b
                         Replace `displayPublishedYearsList(publishedYears)` with calls
                         to the `filterByPublishedYear()` and `displaySearchResults()` methods.
                         */
                        List<Integer> matchedYears = (filterByPublishedYear(filterYear));
                        displaySearchResults(matchedYears, PUBLISHED_YEAR_FIELD);
                    } else if (searchBooksMenuSelection == 4) {
                        // Search by published year range
                        int filterFromYear = promptForPublishedYear("Enter \"from\" date (YYYY): ");
                        int filterToYear = promptForPublishedYear("Enter \"to\" date (YYYY): ");
                        /*
                         Requirement: 6b
                         Replace `displayPublishedYearsList(publishedYears)` with calls
                         to the `filterByPublishedYearRange()` and `displaySearchResults()` methods.
                         */
                        List<Integer> withinRangeYears = (filterByPublishedYearRange(filterFromYear, filterToYear));
                        displaySearchResults(withinRangeYears, PUBLISHED_YEAR_FIELD);
                    } else if (searchBooksMenuSelection == 5) {
                        // Find the most recent books
                        /*
                         Requirement: 7b
                         Replace `displayPublishedYearsList(publishedYears)` with calls
                         to the `findMostRecentBooks()` and `displaySearchResults()` methods.
                         */
                        List<Integer> mostRecentPublishedYears = (findMostRecentBooks());
                        displaySearchResults(mostRecentPublishedYears, PUBLISHED_YEAR_FIELD);
                    } else if (searchBooksMenuSelection == 6) {
                        // Search by price
                        double filterPrice = promptForPrice("Enter price: ");
                        /*
                         Requirement: 8b
                         Replace `displayPricesList(prices)` with calls to the
                         `filterByPrice()` and `displaySearchResults()` methods.
                         */
                        List<Integer> lessThanOrEqualPrice = (filterByPrice(filterPrice));
                        displaySearchResults(lessThanOrEqualPrice, PRICE_FIELD);

                    } else if (searchBooksMenuSelection == 7) {
                        // Search by price range
                        double filterFromPrice = promptForPrice("Enter \"from\" price: ");
                        double filterToPrice = promptForPrice("Enter \"to\" price: ");
                        /*
                         Requirement: 9b
                         Replace `displayPricesList(prices)` with calls to the
                         `filterByPriceRange()` and `displaySearchResults()` methods.
                         */
                        List<Integer> inPriceRange = filterByPriceRange(filterFromPrice, filterToPrice);
                        displaySearchResults(inPriceRange, PRICE_FIELD);
                    } else if (searchBooksMenuSelection == 8) {
                        // Find the least expensive books
                        /*
                         Requirement: 10b
                         Replace `displayPricesList(prices)` with calls to the
                         `findLeastExpensiveBooks()` and `displaySearchResults()` methods.
                         */
                        List<Integer> leastExpensiveBook = findLeastExpensiveBooks();
                        displaySearchResults(leastExpensiveBook, PRICE_FIELD);
                    } else if (searchBooksMenuSelection == 0) {
                        break;
                    }
                }
            } else if (mainMenuSelection == 0) {
                break;
            }
        }

    }

    private void sortSearchResults(List<Integer> indexes, int primaryField) {
        int size = indexes.size(); //create variable to speed things up a little
        int tempIndex; //create a variable to be a placeholder in the bubble sort code
        if (primaryField == TITLE_FIELD) { //figure out which code to run based on user input
            for (int i = 0; i < size - 1; i++) { //iterate through each of the indexes
                boolean swapped = false; //boolean that will let me know if a swap happened
                for (int j = 0; j < size; j++) { //iterate through the list and make swaps where needed
                    if (indexes.get(i).compareTo(indexes.get(i + 1)) > 0) {
                        tempIndex = indexes.get(j);
                        indexes.set(j, indexes.get(j + 1));
                        indexes.set(j + 1, tempIndex);
                        swapped = true;
                    }
                }
                if (!swapped) {
                    break;
                }
            }
        } else if (primaryField == AUTHOR_FIELD) {
            for (int i = 0; i < size - 1; i++) { //iterate through each of the indexes
                boolean swapped = false; //boolean that will let me know if a swap happened
                for (int j = 0; j < size; j++) { //iterate through the list and make swaps where needed
                    if (indexes.get(i).compareTo(indexes.get(i + 1)) > 0) {
                        tempIndex = indexes.get(j);
                        indexes.set(j, indexes.get(j + 1));
                        indexes.set(j + 1, tempIndex);
                        swapped = true;
                    }
                }
                if (!swapped) {
                    break;
                }
            }
        } else if (primaryField == PUBLISHED_YEAR_FIELD) {
            for (int i = 0; i < size - 1; i++) { //iterate through each of the indexes
                boolean swapped = false; //boolean that will let me know if a swap happened
                for (int j = 0; j < size; j++) { //iterate through the list and make swaps where needed
                    if (indexes.get(i).compareTo(indexes.get(i + 1)) > 0) {
                        tempIndex = indexes.get(j);
                        indexes.set(j, indexes.get(j + 1));
                        indexes.set(j + 1, tempIndex);
                        swapped = true;
                    }
                }
                if (!swapped) {
                    break;
                }
            }
        } else if (primaryField == PRICE_FIELD) {
            for (int i = 0; i < size - 1; i++) { //iterate through each of the indexes
                boolean swapped = false; //boolean that will let me know if a swap happened
                for (int j = 0; j < size; j++) { //iterate through the list and make swaps where needed
                    if (indexes.get(i).compareTo(indexes.get(i + 1)) > 0) {
                        tempIndex = indexes.get(j);
                        indexes.set(j, indexes.get(j + 1));
                        indexes.set(j + 1, tempIndex);
                        swapped = true;
                    }
                }
                if (!swapped) {
                    break;
                }
            }
        }
    }

    private void displaySearchResults(List<Integer> indexes, int primaryField) {
        if (indexes.isEmpty()) {
            System.out.println("There are no matches");
        }
        sortSearchResults(indexes, primaryField);

        if (primaryField == TITLE_FIELD) {
            for (Integer index : indexes) {
                System.out.println(titles.get(index) + ": " + authors.get(index) + ": " + publishedYears.get(index) + ": " + prices.get(index));
            }
        } else if (primaryField == AUTHOR_FIELD) {
            for (Integer index : indexes) {
                System.out.println(authors.get(index) + ": " + titles.get(index) + ": " + publishedYears.get(index) + ": " + prices.get(index));
            }
        } else if (primaryField == PUBLISHED_YEAR_FIELD) {
            for (Integer index : indexes) {
                System.out.println(publishedYears.get(index) + ": " + titles.get(index) + ": " + authors.get(index) + ": " + prices.get(index));
            }
        } else if (primaryField == PRICE_FIELD) {
            for (Integer index : indexes) {
                System.out.println(prices.get(index) + ": " + titles.get(index) + ": " + authors.get(index) + ": " + publishedYears.get(index));
            }
        } else {
            System.out.println("Invalid entry");
        }
    }
    /*
     Requirement: 2
     Write the displaySearchResults(List<Integer> indexes) method.
     See README for additional details.
     COMPLETED
     */

    /*
     Requirement: 3a
     Complete the `filterByTitle()` method.
     See README for additional details.
     */
    private List<Integer> filterByTitle(String filterTitle) {
        List<Integer> titleMatches = new ArrayList<Integer>();
        for (int i = 0; i < titles.size(); i++) {
            if (titles.get(i).toLowerCase().contains(filterTitle.toLowerCase())) {
                titleMatches.add(i);
            }
        }
        return titleMatches;
    }

    /*
     Requirement: 4a
     Complete the `filterByAuthor()` method.
     See README for additional details.
     COMPLETED
     */
    private List<Integer> filterByAuthor(String filterAuthor) {
        List<Integer> authorMatches = new ArrayList<Integer>();
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).toLowerCase().contains(filterAuthor.toLowerCase())) {
                authorMatches.add(i);
            }
        }
        return authorMatches;
    }

    /*
     Requirement: 5a
     Complete the `filterByPublishedYear()` method.
     See README for additional details.
     */
    private List<Integer> filterByPublishedYear(int filterYear) {
        List<Integer> yearMatches = new ArrayList<Integer>();
        for (int i = 0; i < publishedYears.size(); i++) {
            if (publishedYears.get(i) == (filterYear)) {
                yearMatches.add(i);
            }
        }
        return yearMatches;
    }

    /*
     Requirement: 6a
     Complete the `filterByPublishedYearRange()` method.
     See README for additional details.
     */
    private List<Integer> filterByPublishedYearRange(int filterFromYear, int filterToYear) {
        List<Integer> withinYearRange = new ArrayList<Integer>();
        for (int i = 0; i < publishedYears.size(); i++) {
            if (publishedYears.get(i) >= filterFromYear && publishedYears.get(i) <= filterToYear) {
                withinYearRange.add(i);
            }
        }
        return withinYearRange;
    }

    /*
     Requirement: 7a
     Add the `private List<Integer> findMostRecentBooks()` method.
     See README for additional details.
     */
    private List<Integer> findMostRecentBooks() {
        List<Integer> mostRecentBooks = new ArrayList<Integer>();
        Integer mostRecentPublishedYear = publishedYears.get(0);
        for (int i = 1; i < publishedYears.size(); i++) {
            if (publishedYears.get(i) > mostRecentPublishedYear) {
                mostRecentPublishedYear = i;
            }
        }
        for (int i = 0; i < publishedYears.size(); i++) {
            if (publishedYears.get(i) == publishedYears.get(mostRecentPublishedYear)) {
                mostRecentBooks.add(i);
            }
        }
        return mostRecentBooks;
    }
    /*
     Requirement: 8a
     Complete the `filterByPrice()` method.
     See README for additional details.
     */

    private List<Integer> filterByPrice(double filterPrice) {
        List<Integer> equalOrLessThanPrice = new ArrayList<Integer>(); //create list to hold results
        BigDecimal priceAsBigDecimal = BigDecimal.valueOf(filterPrice); //convert the filterPrice to decimal for comparison
        for (int i = 0; i < prices.size(); i++) { //iterate through each item in the prices list
            if (prices.get(i).compareTo(priceAsBigDecimal) <= 0) { //if the price is <= the filterPrice, it will return 0 or -1
                equalOrLessThanPrice.add(i); //adds index to results.
            }
        }
        return equalOrLessThanPrice;
    }

    /*
     Requirement: 9a
     Complete the `filterByPriceRange()` method.
     See README for additional details.
     */
    private List<Integer> filterByPriceRange(double filterFromPrice, double filterToPrice) {
        List<Integer> inPriceRange = new ArrayList<Integer>();
        BigDecimal lowPriceAsBigDecimal = BigDecimal.valueOf(filterFromPrice); //convert the from and to prices to big decimal
        BigDecimal highPriceAsBigDecimal = BigDecimal.valueOf(filterToPrice);
        for (int i = 0; i < prices.size(); i++) { //check to make sure the converted price is within the converted price ranges
            if (prices.get(i).compareTo(lowPriceAsBigDecimal) >= 0 && prices.get(i).compareTo(highPriceAsBigDecimal) <= 0) {
                inPriceRange.add(i);
            }
        }
        return inPriceRange;
    }

    /*
     Requirement: 10a
     Add the `private List<Integer> findLeastExpensiveBooks()` method.
     See README for additional details.
     */

    private List<Integer> findLeastExpensiveBooks() {
        List<Integer> leastExpensiveBooks = new ArrayList<Integer>();
        Integer theLeastExpensiveBook = 0; //set index zero as least expensive book
        for (int i = 1; i < prices.size(); i++) { //iterate through list and compare each price and updating least expensive book
            if (prices.get(i).compareTo(prices.get(theLeastExpensiveBook)) < 0) {
                theLeastExpensiveBook = i;
            }
        }
        for (int i = 0; i < prices.size(); i++) { //to make sure we get every book at lowest price, loop through again comparing to the lowest price
            if (prices.get(i).compareTo(prices.get(theLeastExpensiveBook)) == 0) {
                leastExpensiveBooks.add(i);
            }
        }
        return leastExpensiveBooks;
    }

    // UI methods

    private void printMainMenu() {
        System.out.println("1: Display data and subsets");
        System.out.println("2: Search books");
        System.out.println("0: Exit");
        System.out.println();
    }

    private void printDataAndSubsetsMenu() {
        System.out.println("1: Display dataset");
        System.out.println("2: Display titles");
        System.out.println("3: Display authors");
        System.out.println("4: Display published years");
        System.out.println("5: Display prices");
        System.out.println("0: Return to main menu");
        System.out.println();
    }

    private void printSearchBooksMenu() {
        System.out.println("1: Search by title");
        System.out.println("2: Search by author");
        System.out.println("3: Search by published year");
        System.out.println("4: Search by published year range");
        System.out.println("5: Find most recent books");
        System.out.println("6: Search by price");
        System.out.println("7: Search by price range");
        System.out.println("8: Find least expensive books");
        System.out.println("0: Return to main menu");
        System.out.println();
    }

    private void displayDataset(String[] dataset) {
        System.out.println("Dataset");
        System.out.println("-------");
        for (String data : dataset) {
            System.out.println(data);
        }
        System.out.println();
        promptForReturn();
    }

    private void displayTitlesList(List<String> titles) {
        System.out.println("Titles");
        System.out.println("-------");
        for (int i = 0; i < titles.size(); i++) {
            System.out.println(i + ": " + titles.get(i));
        }
        System.out.println();
        promptForReturn();
    }

    private void displayAuthorsList(List<String> authors) {
        System.out.println("Authors");
        System.out.println("-------");
        for (int i = 0; i < authors.size(); i++) {
            System.out.println(i + ": " + authors.get(i));
        }
        System.out.println();
        promptForReturn();
    }

    private void displayPublishedYearsList(List<Integer> publishedYears) {
        System.out.println("Published Years");
        System.out.println("---------------");
        for (int i = 0; i < publishedYears.size(); i++) {
            System.out.println(i + ": " + publishedYears.get(i));
        }
        System.out.println();
        promptForReturn();
    }

    private void displayPricesList(List<BigDecimal> prices) {
        System.out.println("Prices");
        System.out.println("------");
        for (int i = 0; i < prices.size(); i++) {
            System.out.println(i + ": " + prices.get(i));
        }
        System.out.println();
        promptForReturn();
    }

    private int promptForMenuSelection(String prompt) {
        System.out.print(prompt);
        int menuSelection;
        try {
            menuSelection = Integer.parseInt(keyboard.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    private String promptForString(String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private int promptForPublishedYear(String prompt) {
        int year;
        while (true) {
            System.out.println(prompt);
            try {
                year = Integer.parseInt(keyboard.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("The year provided is not well-formed. It must be YYYY.");
            }
        }
        return year;
    }

    private double promptForPrice(String prompt) {
        double price;
        while (true) {
            System.out.println(prompt);
            try {
                price = Double.parseDouble(keyboard.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("The price provided is not a valid monetary value.");
            }
        }
        return price;
    }

    private void promptForReturn() {
        System.out.println("Press RETURN to continue.");
        keyboard.nextLine();
    }
}
