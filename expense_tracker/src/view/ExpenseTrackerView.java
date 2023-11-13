package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import model.Transaction;

import java.util.List;

/**
 * View defines all the texts, labels, buttons, table models among other UI elements.
 */
public class ExpenseTrackerView extends JFrame {

  /**
   * Transactions table in the view
   */
  private JTable transactionsTable;

  /**
   * Add transaction button table
   */
  private JButton addTransactionBtn;

  /**
   * Delete transaction button table
   */
  private JButton deleteTransactionBtn;

  /**
   * Amount text field for a transaction
   */
  private JFormattedTextField amountField;

  /**
   * Category text field for a transaction
   */
  private JTextField categoryField;

  private DefaultTableModel model;

  // private JTextField dateFilterField;

  /**
   * Category filter text field in the dialog box
   */
  private JTextField categoryFilterField;

  /**
   * The category filter button which pops open the categoryFilterField
   */
  private JButton categoryFilterBtn;

  /**
   * Amount filter text field in the dialog box
   */
  private JTextField amountFilterField;

  /**
   * The amount filter button which pops open the amountFilterField
   */
  private JButton amountFilterBtn;

  /**
   * Initializes the UI elements - JTable, button, amount and category filters,
   * labels
   */
  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(600, 400); // Make GUI larger

    String[] columnNames = { "serial", "Amount", "Category", "Date" };
    this.model = new DefaultTableModel(columnNames, 0);

    // Create table
    transactionsTable = new JTable(model);
    transactionsTable.setDefaultEditor(Object.class, null);

    addTransactionBtn = new JButton("Add Transaction");

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);

    JLabel categoryFilterLabel = new JLabel("Filter by Category:");
    categoryFilterField = new JTextField(10);
    categoryFilterBtn = new JButton("Filter by Category");

    JLabel amountFilterLabel = new JLabel("Filter by Amount:");
    amountFilterField = new JTextField(10);
    amountFilterBtn = new JButton("Filter by Amount");

    deleteTransactionBtn = new JButton("Delete Transaction");

    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel);
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);
    inputPanel.add(deleteTransactionBtn);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(amountFilterBtn);
    buttonPanel.add(categoryFilterBtn);

    // Add panels to frame
    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    // Set frame properties
    setSize(600, 400); // Increase the size for better visibility
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  /**
   * This method return a reference to the view table model
   * 
   * @return table model
   */
  public DefaultTableModel getTableModel() {
    return model;
  }

  /**
   * This method calls the removeRow of the model to delete the selected
   * transaction
   * 
   * @param selectedRow - Row index. The row to be removed from the table
   *                    This method removes the selected row from the transactions
   *                    table
   */
  public void removeTableRow(int selectedRow) {
    model.removeRow(selectedRow);
  }

  /**
   * This method return a reference to the transactions JTable
   * 
   * @return transactions table
   */
  public JTable getTransactionsTable() {
    return transactionsTable;
  }

  /**
   * This method returns the user entered double-parsed transaction amount
   * 
   * @return the returns the user entered double-parsed transaction amount
   */
  public double getAmountField() {
    if (amountField.getText().isEmpty()) {
      return 0;
    } else {
      double amount = Double.parseDouble(amountField.getText());
      return amount;
    }
  }

  /**
   * This method sets the amount field in the table for a transaction
   * 
   * @param amountField amount 
   */
  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  /**
   * This method returns the user entered category of a transaction
   * 
   * @return the text in the category field
   */
  public String getCategoryField() {
    return categoryField.getText();
  }

  /**
   * This method sets the category field of a
   * transaction
   * 
   * @param categoryField User enetered category
   */
  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }

  /**
   * This method adds action listener to the category filter button
   * 
   * @param listener - the listener to be attached to the button
   */
  public void addApplyCategoryFilterListener(ActionListener listener) {
    categoryFilterBtn.addActionListener(listener);
  }

  /**
   * This method displays the dialog box to accept category filter user input
   * 
   * @return the JOptionPane dialog box
   */
  public String getCategoryFilterInput() {
    return JOptionPane.showInputDialog(this, "Enter Category Filter:");
  }

  /**
   * This method adds action listener to the amount filter button
   * 
   * @param listener - the listener to be attached to the button
   */
  public void addApplyAmountFilterListener(ActionListener listener) {
    amountFilterBtn.addActionListener(listener);
  }

  /**
   * This method reads the input amount filter entered by the user and type casts
   * into Double
   * 
   * @return The parsed input amount
   */
  public double getAmountFilterInput() {
    String input = JOptionPane.showInputDialog(this, "Enter Amount Filter:");
    try {
      return Double.parseDouble(input);
    } catch (NumberFormatException e) {
      // Handle parsing error here
      // You can show an error message or return a default value
      return 0.0; // Default value (or any other appropriate value)
    }
  }

  /**
   * This method refreshes the table in the view to reflect the latest changes
   * 
   * @param transactions - list of transactions
   *                     This method refreshes the transactions Jtable to reflect
   *                     the latest changes in the data model
   *                     It updates the row count, total amount.
   */
  public void refreshTable(List<Transaction> transactions) {
    // Clear existing rows
    model.setRowCount(0);
    // Get row count
    int rowNum = model.getRowCount();
    double totalCost = 0;
    // Calculate total cost
    for (Transaction t : transactions) {
      totalCost += t.getAmount();
    }

    // Add rows from transactions list
    for (Transaction t : transactions) {
      model.addRow(new Object[] { rowNum += 1, t.getAmount(), t.getCategory(), t.getTimestamp() });
    }
    // Add total row
    Object[] totalRow = { "Total", null, null, totalCost };
    model.addRow(totalRow);

    // Fire table update
    transactionsTable.updateUI();

  }

  /**
   * This method returns a reference to the add transaction button
   * 
   * @return the reference to the add transaction button
   */
  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }

  /**
   * This method returns a reference to the delete transaction button
   * 
   * @return the reference to the delete transaction button
   */
  public JButton getDeleteTransactionBtn() {
    return deleteTransactionBtn;
  }

  /**
   * This method highlights the transactions which passes the filter check
   * 
   * @param rowIndexes the row indexes of the transactions list
   */
  public void highlightRows(List<Integer> rowIndexes) {
    // The row indices are being used as hashcodes for the transactions.
    // The row index directly maps to the the transaction index in the list.
    transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
          boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (rowIndexes.contains(row)) {
          c.setBackground(new Color(173, 255, 168)); // Light green
        } else {
          c.setBackground(table.getBackground());
        }
        return c;
      }
    });

    transactionsTable.repaint();
  }

}
