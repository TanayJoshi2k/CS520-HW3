# hw1- Manual Review

The homework will be based on this project named "Expense Tracker",where users will be able to add/remove daily transaction. 

## Compile

To compile the code from terminal, use the following command:
```
cd src
javac ExpenseTrackerApp.java
java ExpenseTracker
```

You should be able to view the GUI of the project upon successful compilation. 

## Java Version
This code is compiled with ```openjdk 17.0.7 2023-04-18```. Please update your JDK accordingly if you face any incompatibility issue.

Undo Functionality:

In the view, we added a Delete Transaction button. The ExpenseTrackerApp.java file adds an action listener on this button to listen for clicks. The user first selects a table row with a single click.
The row number chosen by the user is fethed using view.getTransactionsTable().getSelectedRow() 

When the user clicks on delete transaction button, the deleteRow() method of the controller is called with the selected row as an argument. If there are no transactions i.e when table is empty and user clicks on delete transaction table, or tries to delete the last row i.e the row that displays total amount, the corresponding appropriate error message is displayed in a dialog box.

The controller.deleteRow() removes the row from the transactions JTable as well as deletes the selected transaction from the model or throws IndexOutOfBoundsException if user clicks delete transaction button when the table is empty. 

Note - 
The controller.deleteRow() method has been slightly modify to implement test cases more easily. This method also returns the deleted transaction.