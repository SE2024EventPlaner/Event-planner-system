Feature: Login

  Scenario Outline: Login with valid credentials
    When user tries to login through the system
    And user enters the email "<email>"
    And user enters the password "<password>"
    Then user login succeeds
    And user page "<type>" appears
  Examples:
    | email | password | type |
    | hello@gmail.com | 123123 | USER |
    | hello2@gmail.com | 123456 | ADMIN |
    | hello3@gmail.com | 123789 | SERVICE_PROVIDER |

  Scenario Outline: Login with invalid password
    When user tries to login through the system
    And user enters the email "<email>"
    And user enters the password "<password>"
    Then user not login succeeds
    And A message appears that the password is wrong
    Examples:
      | email            | password |
      | hello@gmail.com  | 103123   |
      | hello2@gmail.com | 123056   |
      | hello3@gmail.com | 120789   |


  Scenario Outline: Login with invalid email
    When user tries to login through the system
    And user enters the email "<email>"
    And user enters the password "<password>"
    Then user not login succeeds
    And A message appears that the email is wrong
    Examples:
      | email             | password |
      | hel2lo@gmail.com  | 103123   |
      | hellao2@gmail.com | 123056   |
      | helrlo3@gmail.com | 120789   |









