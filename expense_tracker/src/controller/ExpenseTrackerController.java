package controller;

import view.ExpenseTrackerView;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.ExpenseTrackerModel;
import model.Transaction;
import model.Filter.TransactionFilter;

/**
 * ExpenseTrackerController defines controller which makes facilitates
 * communication between the model and the view. Adds methods to add, delete and
 * filter transactions
 */
public class ExpenseTrackerController {

  /**
   * The data model
   */
  private ExpenseTrackerModel model;

  /**
   * The UI of the app
   */
  private ExpenseTrackerView view;

  /**
   * The Controller is applying the Strategy design pattern.
   * This is the has-a relationship with the Strategy class
   * being used in the applyFilter method.
   */
  private TransactionFilter filter;

  /**
   * Constructor to initialize the model and the view
   * 
   * @param model - The transaction model
   * @param view  - The view
   * 
   */
  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * This method sets the filter based on user selection
   * 
   * @param filter - Sets the filter to either amount or category filter.
   */
  public void setFilter(TransactionFilter filter) {
    // Sets the Strategy class being used in the applyFilter method.
    this.filter = filter;
  }

  /**
   * This method refreshes the view to display the changes
   */
  public void refresh() {
    List<Transaction> transactions = model.getTransactions();
    view.refreshTable(transactions);
  }

  /**
   * This method checks if a transaction is valid.
   * If valid, it adds a transaction to the model and the view, refreshes the view
   * 
   * @param amount   - amount field of a transaction
   * @param category - category field of a transaction
   * @return boolean - whether the transaction was added or not
   */
  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }

    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[] { t.getAmount(), t.getCategory(), t.getTimestamp() });
    refresh();
    return true;
  }

  /**
   * This method applies the selected filter on the list of transactions - either
   * filters by amount or category and also highlights the transactions which pass
   * the filter check.
   * If no filter is applied, it displays message dialog with the said error.
   */
  public List<Transaction> applyFilter() {
    // null check for filter
    List<Transaction> transactions = model.getTransactions();
    if (filter != null) {
      // Use the Strategy class to perform the desired filtering
      List<Transaction> filteredTransactions = filter.filter(transactions);
      List<Integer> rowIndexes = new ArrayList<>();
      for (Transaction t : filteredTransactions) {
        int rowIndex = transactions.indexOf(t);
        if (rowIndex != -1) {
          rowIndexes.add(rowIndex);
        }
      }
      view.highlightRows(rowIndexes);
      return filteredTransactions;
    } else {
      JOptionPane.showMessageDialog(view, "No filter applied");
      view.toFront();
      return transactions;
    }

  }

  /**
   * This method deletes a particular transaction from the model and the view,
   * refreshes the view
   * 
   * @param selectedRow - the row index in the transactions table to be removed
   * @return deleted transaction
   * @throws IndexOutOfBoundsException if tablw is empty and user tries to delete
   *                                   a transaction
   */
  public Transaction deleteRow(int selectedRow) {
    List<Transaction> transactions = model.getTransactions();
    if (transactions.size() == 0) {
      throw new IndexOutOfBoundsException("Table is empty, cannot perform undo");
    }
    view.removeTableRow(selectedRow);
    Transaction t = transactions.get(selectedRow);
    model.removeTransaction(t);
    refresh();
    return t;
  }
}