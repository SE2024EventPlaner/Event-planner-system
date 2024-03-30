Feature: BookEvent
  Example:
  |101010|Birthday   | Nablus      | 1000 |
  |303030|Graduation | Nablus      | 1800 |
  |202020|Marriage   | Jenin       | 2900 |
  Scenario: Successful Booking
    Given that an event with ID "101010" and with location "Nablus" is available for booking for user with Email "hello@gmail.com" and with password "123123"
    When The user is trying to book an event with a budget of "1000" and date with "2024-03-29T06:00:00"
    Then the system should confirm the booking with a success message
    And the event status should be updated to "booked"

  Scenario: Unavailable Event Booking
    Given that an event with ID "450" and location "Jenin" is not available for booking
    Then the system should display an error message indicating that the event is not available for booking

  Scenario: Booking with Invalid Dates
    Given that an event with ID "303030" and location with "Nablus" is available for booking
    And the booking date is "2024-03-29T06:00:00"
    When the user tries to book the event for invalid date
    Then the system should display an error message indicating that the booking date is not valid

  Scenario: Booking without Sufficient Funds
    Given that an event with ID "202020" and located in "Jenin" is available for booking
    And the user's account balance is "700" dollars
    When the user tries to book the event with unsufficient funds
    Then the system should display an error message indicating insufficient funds