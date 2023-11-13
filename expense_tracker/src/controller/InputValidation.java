package controller;

import java.util.Arrays;

/**
 * InputValidation class defines methods to validate user enetered amount and category
 */
public class InputValidation {

  /**
   * This method validates the transaction amount.
   * Returns true only if amount is greater than 0 and less than 1000 else false
   * 
   * @param amount - the amount field of a transaction
   * @return boolean value - whether amount is valid or not
   */
  public static boolean isValidAmount(double amount) {

    // Check range
    if (amount > 1000) {
      return false;
    }
    if (amount < 0) {
      return false;
    }
    if (amount == 0) {
      return false;
    }
    return true;
  }

  /**
   * This method validates the transaction category.
   * Returns true if the category is in one of the validWords defined below else
   * false
   * 
   * @param category - the category field of a transaction
   * @return boolean value - whether category is valid or not
   */
  public static boolean isValidCategory(String category) {

    if (category == null) {
      return false;
    }

    if (category.trim().isEmpty()) {
      return false;
    }

    if (!category.matches("[a-zA-Z]+")) {
      return false;
    }

    String[] validWords = { "food", "travel", "bills", "entertainment", "other" };

    if (!Arrays.asList(validWords).contains(category.toLowerCase())) {
      // invalid word
      return false;
    }

    return true;

  }

}