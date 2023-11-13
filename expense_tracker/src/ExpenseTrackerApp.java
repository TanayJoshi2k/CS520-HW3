import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import view.ExpenseTrackerView;
import model.Filter.AmountFilter;
import model.Filter.CategoryFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Entry point of the app.
 * Initializes model, view, controller and action listeners
 */
public class ExpenseTrackerApp {

  /**
   * This class bundles MVC together
   * 
   * @param args - Command line arguments - array of strings
   */
  public static void main(String[] args) {

    // Create MVC components
    ExpenseTrackerModel model = new ExpenseTrackerModel();
    ExpenseTrackerView view = new ExpenseTrackerView();
    ExpenseTrackerController controller = new ExpenseTrackerController(model, view);

    JTable transactionsTableRef = view.getTransactionsTable();
    JButton deleteTransactionButton = view.getDeleteTransactionBtn();

    // Initialize view
    view.setVisible(true);

    // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
      // Get transaction data from view
      double amount = view.getAmountField();
      String category = view.getCategoryField();

      // Call controller to add transaction
      boolean added = controller.addTransaction(amount, category);

      if (!added) {
        JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
        view.toFront();
      }
    });

    view.getDeleteTransactionBtn().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int selectedRow = view.getTransactionsTable().getSelectedRow();
        int tableRows = view.getTransactionsTable().getRowCount();
        if (tableRows == 0) {
          JOptionPane.showMessageDialog(view, "Nothing to delete!");
        } else if (selectedRow == -1) {
          JOptionPane.showMessageDialog(view, "Select a row to delete");
        } else if (selectedRow != -1 && selectedRow != tableRows - 1) {
          controller.deleteRow(selectedRow);
        } else {
          JOptionPane.showMessageDialog(view, "Cannot delete total amount row");
        }
      }
    });

    // Add action listener to the "Apply Category Filter" button
    view.addApplyCategoryFilterListener(e -> {
      try {
        String categoryFilterInput = view.getCategoryFilterInput();
        CategoryFilter categoryFilter = new CategoryFilter(categoryFilterInput);
        if (categoryFilterInput != null) {
          // controller.applyCategoryFilter(categoryFilterInput);
          controller.setFilter(categoryFilter);
          controller.applyFilter();
        }
      } catch (IllegalArgumentException exception) {
        JOptionPane.showMessageDialog(view, exception.getMessage());
        view.toFront();
      }
    });

    // Add action listener to the "Apply Amount Filter" button
    view.addApplyAmountFilterListener(e -> {
      try {
        double amountFilterInput = view.getAmountFilterInput();
        AmountFilter amountFilter = new AmountFilter(amountFilterInput);
        if (amountFilterInput != 0.0) {
          controller.setFilter(amountFilter);
          controller.applyFilter();
        }
      } catch (IllegalArgumentException exception) {
        JOptionPane.showMessageDialog(view, exception.getMessage());
        view.toFront();
      }
    });

  }
}
