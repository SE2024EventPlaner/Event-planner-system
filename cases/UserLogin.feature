Feature: UserLogin
  Scenario: User can login
    Given that the user is not logged in
    When user enter correct user email <"samya@gmail.com">
    And the password is <"samya1234">
    Then the user login succeeds
    And the user is logged in

  Scenario: User has the wrong password
    Given that the user is not logged in
    And the password is <"samyaxyz">
    Then the user login fails
    And the user is not logged in

  Scenario: User has the wrong email
    Given that the user is not logged in
    And the email is <"Email">
    Then the user login fails
    And the user is not logged in