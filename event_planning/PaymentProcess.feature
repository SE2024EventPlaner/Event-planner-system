
Feature: Payment Process Using Credit Card

  Scenario: Customer pays the amount using a valid credit card
    Given the user has logged in with email as "hello@gmail.com"
    When the user enters their valid credit card information credit card number as "14526985634785"
    And the user confirms the payment
    Then a payment confirmation message indicating the successful process is displayed to the user


  Scenario: User enters an invalid credit card number
    Given the user has logged in with email as "hello@gmail.com"
    When the user enters their invalid credit card information credit card number as "145269555"
    Then the system should display a message indicating that the credit card number is invalid

  Scenario: Insufficient balance for payment
    Given the user has logged in with email as "hello@gmail.com"
    And The user's account balance is insufficient for the payment
    When The user tries to make a payment
    Then The system should display a message indicating insufficient balance

  Scenario: User cancels the payment process
    Given the user has logged in with email as "hello@gmail.com"
    And The user has initiated the payment process
    When The user decides to cancel the payment
    Then The system should cancel the payment process
    And The user should be returned to the previous screen without completing the payment
