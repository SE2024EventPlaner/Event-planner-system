Feature: Login

  Scenario Outline: Login with valid credentials
    When user tries to login through the system
    And user enters the email "<email>"
    And user enters the password "<password>"
    Then user login succeeds
    And user page "<type>" appears
  Examples:
    | email            | password | type             |
    | hello@gmail.com  | 123123   | USER             |
    | hello2@gmail.com | 123456   | ADMIN            |
    | hello3@gmail.com | 123789   | SERVICE_PROVIDER |



  Scenario Outline: Login with invalid credentials
    When user tries to login through the system
    And the user enters the 'email' with "<Email>"
    And the user enters the 'password' with "<Password>"
    And the "<Message>" should appear

    Examples:
      | Email                     | Password     | Message             |
      | doesnotexist@gmail.com    | 123123       | non-existent email  |
      | hello@gmail.com           | 12356        | invalid password    |











