package model;

import controller.InputValidation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Transaction defines what a transaction object will look like and defines
 * properties for the object
 */
public class Transaction {

  /** Date formatter object */
  public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

  // final means that the variable cannot be changed

  /**
   * amount field to store the transaction amount
   */
  private final double amount;

  /**
   * category field to store the transaction category
   */
  private final String category;

  /**
   * category field to store the transaction time
   */
  private final String timestamp;

  /**
   * This method creates a transaction object with the user set amount and
   * category
   * 
   * @param amount   - set the amount field of a transaction
   * @param category - set the category field of a transaction
   * @throws IllegalArgumentException if amount or category is invalid
   */
  public Transaction(double amount, String category) {
    // Since this is a public constructor, perform input validation
    // to guarantee that the amount and category are both valid
    if (InputValidation.isValidAmount(amount) == false) {
      throw new IllegalArgumentException("The amount is not valid.");
    }
    if (InputValidation.isValidCategory(category) == false) {
      throw new IllegalArgumentException("The category is not valid.");
    }

    this.amount = amount;
    this.category = category;
    this.timestamp = generateTimestamp();
  }

  /**
   * This method returns the transaction amount
   * 
   * @return transaction amount
   */
  public double getAmount() {
    return amount;
  }

  // setter method is removed because we want to make the Transaction immutable
  // public void setAmount(double amount) {
  // this.amount = amount;
  // }

  /**
   * This method returns the transaction category
   * 
   * @return transaction category
   */
  public String getCategory() {
    return category;
  }

  // public void setCategory(String category) {
  // this.category = category;
  // }

  /**
   * This method returns the transaction timestamp
   * 
   * @return transaction timestamp
   */
  public String getTimestamp() {
    return timestamp;
  }

  // private helper method to generate timestamp
  /**
   * This method generates timestamp of a transaction
   * 
   * @return date in simple date format
   */
  private String generateTimestamp() {
    return dateFormatter.format(new Date());
  }

}
