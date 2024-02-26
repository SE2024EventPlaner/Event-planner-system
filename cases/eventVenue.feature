Feature: Event Venue
  Scenario: Successful Booking
    Given that an event with ID "123" is available for booking
    And the venue is located in "Nablus"
    And the booking price is "1000" dollars
    When the user tries to book the venue with the ID "123" for the next month
    Then the system should confirm the booking with a success message
    And the event status should be updated to "Booked"

  Scenario: Unavailable Event Booking
    Given that an event with ID "456" is not available for booking
    When the user tries to book the venue with the ID "456"
    Then the system should display an error message indicating that the event is not available for booking

  Scenario: Booking with Invalid Dates
    Given that an event with ID "789" is available for booking
    And the event is located in "Ramallah"
    And the booking price is "800 dollars"
    When the user tries to book the venue with the ID "789" for a past date
    Then the system should display an error message indicating that the booking date is not valid

  Scenario: Booking without Sufficient Funds
    Given that an event with ID "101" is available for booking
    And the event is located in "Nablus"
    And the booking price is "1200 dollars"
    And the user's account balance is "800 dollars"
    When the user tries to book the venue with the ID "101"
    Then the system should display an error message indicating insufficient funds