package model.Filter;

import java.util.List;

import model.Transaction;

/**
 * The TransactionFilter supports filtering the transaction list.
 *
 * NOTE) The Strategy design pattern is being applied. This is the Strategy interface.
 */
public interface TransactionFilter {

  /**
   * This method is a wrapper. It filters by either amount or category
   * @param transactions List of transactions
   * @return filtered transactions
   */
  public List<Transaction> filter(List<Transaction> transactions);

}
