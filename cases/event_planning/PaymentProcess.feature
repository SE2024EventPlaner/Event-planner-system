Feature:  payment process

  Scenario: Successful payment
    Given that an event with ID "101010" and with location "Nablus" is available for booking for user with Email "hello@gmail.com" and with password "123123"
    When The user is trying to book an event with valid card number "1234567891056789"
    Then the system should confirm the payment  with a success message


  Scenario: unSuccessful payment
    Given that an event with ID "101010" and with location "Nablus" is available for booking for user with Email "hello@gmail.com" and with password "123123"
    When The user is trying to book an event with  invalid card number "1234556789"
    Then the system should confirm the payment  with a Unsuccess message