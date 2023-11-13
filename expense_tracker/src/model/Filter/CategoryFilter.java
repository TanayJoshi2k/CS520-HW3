package model.Filter;

import java.util.ArrayList;
import java.util.List;

import model.Transaction;
import controller.InputValidation;

/**
 * CategoryFilter class extends TransactionFilter to define the filter method of
 * the latter
 */
public class CategoryFilter implements TransactionFilter {
    /**
     * Input category entered by the user
     */
    private String categoryFilter;

    /**
     * This method sets categoryFilter equal to the user entered category
     * 
     * @param categoryFilter Input category entered by the user
     * @throws IllegalArgumentException if the enetered category fails the
     *                                  InputValidation check
     */
    public CategoryFilter(String categoryFilter) {
        // Since the CategoryFilter constructor is public,
        // the input validation needs to be performed again.
        if (!InputValidation.isValidCategory(categoryFilter)) {
            throw new IllegalArgumentException("Invalid category filter");
        } else {
            this.categoryFilter = categoryFilter;
        }
    }

    @Override
    /**
     * This method filters the transactions with the set category filter.
     * Transaction passes category filter if the transaction category matches user
     * entered category
     * 
     * @param transactions List of transactions to be filtered
     * @return filtered transactions
     */
    public List<Transaction> filter(List<Transaction> transactions) {

        List<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getCategory().equalsIgnoreCase(categoryFilter)) {
                filteredTransactions.add(transaction);
            }
        }

        return filteredTransactions;
    }
}
