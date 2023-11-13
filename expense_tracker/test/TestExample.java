
// package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.beans.Transient;
import java.text.ParseException;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import controller.ExpenseTrackerController;
import controller.InputValidation;
import model.ExpenseTrackerModel;
import model.Transaction;
import model.Filter.AmountFilter;
import model.Filter.CategoryFilter;
import view.ExpenseTrackerView;

public class TestExample {

    private ExpenseTrackerModel model;
    private ExpenseTrackerView view;
    private ExpenseTrackerController controller;

    @Before
    public void setup() {
        model = new ExpenseTrackerModel();
        view = new ExpenseTrackerView();
        controller = new ExpenseTrackerController(model, view);
    }

    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }

    public void checkTransaction(double amount, String category, Transaction transaction) {
        assertEquals(amount, transaction.getAmount(), 0.01);
        assertEquals(category, transaction.getCategory());
        String transactionDateString = transaction.getTimestamp();
        Date transactionDate = null;
        try {
            transactionDate = Transaction.dateFormatter.parse(transactionDateString);
        } catch (ParseException pe) {
            pe.printStackTrace();
            transactionDate = null;
        }
        Date nowDate = new Date();
        assertNotNull(transactionDate);
        assertNotNull(nowDate);
        // They may differ by 60 ms
        assertTrue(nowDate.getTime() - transactionDate.getTime() < 60000);
    }

    @Test
    public void testAddTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());

        // Perform the action: Add a transaction
        double amount = 50.0;
        String category = "food";
        assertTrue(controller.addTransaction(amount, category));

        // Post-condition: List of transactions contains only
        // the added transaction
        assertEquals(1, model.getTransactions().size());

        // Check the contents of the list
        Transaction firstTransaction = model.getTransactions().get(0);
        checkTransaction(amount, category, firstTransaction);

        // Check the total amount
        assertEquals(amount, getTotalCost(), 0.01);
    }

    @Test
    public void testRemoveTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());

        // Perform the action: Add and remove a transaction
        double amount = 50.0;
        String category = "food";
        Transaction addedTransaction = new Transaction(amount, category);
        model.addTransaction(addedTransaction);

        // Pre-condition: List of transactions contains only
        // the added transaction
        assertEquals(1, model.getTransactions().size());
        Transaction firstTransaction = model.getTransactions().get(0);
        checkTransaction(amount, category, firstTransaction);

        assertEquals(amount, getTotalCost(), 0.01);

        // Perform the action: Remove the transaction
        model.removeTransaction(addedTransaction);

        // Post-condition: List of transactions is empty
        List<Transaction> transactions = model.getTransactions();
        assertEquals(0, transactions.size());

        // Check the total cost after removing the transaction
        double totalCost = getTotalCost();
        assertEquals(0.00, totalCost, 0.01);
    }

    // Qestion 1 Add transaction succeeds
    @Test
    public void testAddTransactionView() {
        double amount = 100.0;
        String category = "other";

        // Table should be empty initially
        assertEquals(0, view.getTransactionsTable().getRowCount());

        // Perform the action: Add a transaction
        assertTrue(controller.addTransaction(amount, category));

        // Subtract 1 because an additional row for total is added
        assertEquals(1, view.getTransactionsTable().getRowCount() - 1);

        Transaction transaction = model.getTransactions().get(0);

        checkTransaction(amount, category, transaction);
        assertEquals(amount, getTotalCost(), 0.01);
    }

    // Question 2 Invalid Input Handling
    @Test
    public void testAddTransactionFail() {
        double invalidAmount = 100000.0;
        String invalidCategory = "test";

        // List of transactions is empty initially
        assertEquals(0, model.getTransactions().size());

        assertFalse(controller.addTransaction(invalidAmount, invalidCategory));

        // Transaction should't be added
        assertEquals(0, model.getTransactions().size());
        assertEquals(0, getTotalCost(), 0.01);

    }

    // Question 3 Filter by Amount
    @Test
    public void testAmountFilter() {

        // Set filter amount = 100
        AmountFilter amountFilter = new AmountFilter(100.0);
        controller.setFilter(amountFilter);

        // No transactions initially
        assertEquals(0, view.getTransactionsTable().getRowCount());

        assertTrue(controller.addTransaction(100.0, "food"));
        assertTrue(controller.addTransaction(150.0, "other"));
        assertTrue(controller.addTransaction(100.0, "bills"));
        assertTrue(controller.addTransaction(20.0, "bills"));

        assertEquals(4, view.getTransactionsTable().getRowCount() - 1);
        assertEquals(370.0, getTotalCost(), 0.01);

        ArrayList<Boolean> expected = new ArrayList<>(Arrays.asList(true, false, true, false));
        ArrayList<Boolean> observed = new ArrayList<>();

        Color color = new Color(173, 255, 168);

        controller.applyFilter();
        JTable table = view.getTransactionsTable();

        List<Transaction> transactions = model.getTransactions();

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(Object.class);
        for (int i = 0; i < transactions.size(); i++) {
            Component component = renderer.getTableCellRendererComponent(table, renderer, false, false, i, 0);
            Color bckgColor = component.getBackground();

            if (bckgColor.equals(color)) {
                observed.add(true);
            } else {
                observed.add(false);
            }
        }
        assertEquals(expected, observed);
    }

    @Test
    // Question 4 Filter by Category
    public void testCategoryFilter() {

        // Set filter amount = 100
        CategoryFilter categoryFilter = new CategoryFilter("food");
        controller.setFilter(categoryFilter);

        // No transactions initially
        assertEquals(0, view.getTransactionsTable().getRowCount());

        assertTrue(controller.addTransaction(100.0, "food"));
        assertTrue(controller.addTransaction(150.00, "food"));
        assertTrue(controller.addTransaction(100.0, "bills"));
        assertTrue(controller.addTransaction(20.0, "food"));

        assertEquals(4, view.getTransactionsTable().getRowCount() - 1);
        assertEquals(370.0, getTotalCost(), 0.01);

        ArrayList<Boolean> expected = new ArrayList<>(Arrays.asList(true, true, false, true));
        ArrayList<Boolean> observed = new ArrayList<>();

        Color color = new Color(173, 255, 168);

        controller.applyFilter();
        JTable table = view.getTransactionsTable();

        List<Transaction> transactions = model.getTransactions();

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(Object.class);
        for (int i = 0; i < transactions.size(); i++) {
            Component component = renderer.getTableCellRendererComponent(table, renderer, false, false, i, 0);
            Color bckgColor = component.getBackground();

            if (bckgColor.equals(color)) {
                observed.add(true);
            } else {
                observed.add(false);
            }
        }
        assertEquals(expected, observed);
    }

    // Question 5 Undo Disallowed
    @Test
    public void testUndoDisallowed() {
        assertEquals(0, view.getTransactionsTable().getRowCount());

        List<Transaction> transactions = model.getTransactions();

        int tableRowCount = view.getTransactionsTable().getRowCount();
        
        try {
            controller.deleteRow(0);
        } catch (IndexOutOfBoundsException e) {
            // Check that the exception message is as expected
            assertEquals("Table is empty, cannot perform undo", e.getMessage());
        }
    }

    // Question 6 Undo Allowed
    @Test
    public void testUndoAllowed() {

        // No transactions initially
        assertEquals(0, view.getTransactionsTable().getRowCount());

        assertTrue(controller.addTransaction(100.0, "food"));
        assertTrue(controller.addTransaction(150.0, "other"));
        assertTrue(controller.addTransaction(20.0, "bills"));

        List<Transaction> transactions = model.getTransactions();

        // Subtract 1 because an additional row for total is added. There should be 3
        // transactions in the table
        assertEquals(3, view.getTransactionsTable().getRowCount() - 1);

        // Delete the first transaction
        Transaction deletedTransaction = controller.deleteRow(0);

        // Check if the correct transaction is deleted
        assertEquals(deletedTransaction, transactions.get(0));
        checkTransaction(100.0, "food", deletedTransaction);

        // There should be 2 transactions in the table
        assertEquals(2, view.getTransactionsTable().getRowCount() - 1);

        assertEquals(170.0, getTotalCost(), 0.01);

    }

    @After
    public void tearDown() {
        model = null;
        view = null;
        controller = null;
    }
    
}
