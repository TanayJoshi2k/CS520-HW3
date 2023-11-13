package model.Filter;

import java.util.ArrayList;
import java.util.List;

import model.Transaction;
import controller.InputValidation;

/**
 * AmountFilter class extends TransactionFilter to define the filter method of
 * the latter
 */
public class AmountFilter implements TransactionFilter {

    /**
     * Input amount entered by the user
     */
    private double amountFilter;

    /**
     * This method sets amountFilter equal to the user entered amount
     * 
     * @param amountFilter Input amount entered by the user
     * @throws IllegalArgumentException if the enetered amount fails the
     *                                  InputValidation check
     */
    public AmountFilter(double amountFilter) {
        // Since the AmountFilter constructor is public,
        // the input validation needs to be performed again.
        if (!InputValidation.isValidAmount(amountFilter)) {
            throw new IllegalArgumentException("Invalid amount filter");
        } else {
            this.amountFilter = amountFilter;
        }
    }

    @Override
    /**
     * This method filters the transactions with the set category filter.
     * Transaction passes category filter if the transaction amount matches user
     * entered amount
     * 
     * @param transactions List of transactions to be filtered
     * @return filtered transactions
     */
    public List<Transaction> filter(List<Transaction> transactions) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            // Your solution could use a different comparison here.
            if (transaction.getAmount() == amountFilter) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }

}
