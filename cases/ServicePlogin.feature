Feature: ServiceProviderLogin
  Scenario: ServiceProvider can login
    Given that the ServiceProvider is not logged in
    When user enter correct user email <"shahd@gmail.com">
    And the password is <"shahd1234">
    Then the ServiceProvider login succeeds
    And the ServiceProvider is logged in

  Scenario: ServiceProvider has the wrong password
    Given that the ServiceProvider is not logged in
    And the password is <"shahdxyz">
    Then the ServiceProvider login fails
    And the ServiceProvider is not logged in

  Scenario: ServiceProvider has the wrong email
    Given that the ServiceProvider is not logged in
    And the email is <"Email">
    Then the ServiceProvider login fails
    And the ServiceProvider is not logged in