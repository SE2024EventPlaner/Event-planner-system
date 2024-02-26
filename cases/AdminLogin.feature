Feature: AdminLogin
  Scenario: Admin can login
    Given that the admin is not logged in
    When user enter correct admin email <"admin">
    And the password is <"admin1234">
    Then the admin login succeeds


  Scenario: Admin has the wrong password
    Given that the admin is not logged in
    And the password is <"adminxyz">
    Then the admin login fails
