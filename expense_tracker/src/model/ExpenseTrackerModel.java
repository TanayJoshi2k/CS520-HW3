package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model stores transactions in a list and defines methods to add, remove and
 * fetch transactions
 */
public class ExpenseTrackerModel {

  // encapsulation - data integrity
  /**
   * A list of transactions to hold transaction objects
   */
  private List<Transaction> transactions;

  /**
   * Initialize the list of transactions
   */
  public ExpenseTrackerModel() {
    transactions = new ArrayList<>();
  }

  /**
   * This method adds a transaction to the list of transactions
   * 
   * @param t Transaction object
   * @throws IllegalArgumentException if transaction is null
   */
  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are
    // non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
  }

  /**
   * This method removes a transaction from the list of transactions
   * 
   * @param t Transaction object
   */
  public void removeTransaction(Transaction t) {
    transactions.remove(t);
  }

  /**
   * This method returns the list of transactions
   * 
   * @return list of transactions
   */
  public List<Transaction> getTransactions() {
    // encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }

}
