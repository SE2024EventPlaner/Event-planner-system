Feature: Sign Up


  Scenario: Sign up with valid data
    When The user enter firstName with 'shifaa' and finalName with 'barham' and email with 'shifaa@example.com' and password with 'Secret@567' and Confirm password with 'Secret@567' and type with 'user'
    Then  creating an account successfully
    And The user should see a confirmation message

  Scenario Outline: Sign up with different validation scenarios
    When the user fill the 'email' with "<Email>"
    And the user fill the 'password' with "<Password>"
    And the user fill the 'Confirm password' with "<ConfirmPassword>"
    Then account creation should Failed
    And a "<Message>" should appear

    Examples:
      | Email                 | Password     | ConfirmPassword | Message                               |
      | incoremail.com        | Password123! | Password123!    | invalid email syntax                  |
      | hello@gmail.com       | Password123! | Password123!    | email is already registered           |
      | valid@email.com       | pssword123   | pssword123      |  weak password                        |
      | valid@email.com       | Password123! | Password456!    |  password mismatch                    |


  Scenario: Successful sign-up as a service provider
    When The user enter firstName with 'aseel' and finalName with 'zaid' and email with 'barhamshifaa@gmail.com' and password with 'Secret@567' and Confirm password with 'Secret@567' and type with 'SERVICE_PROVIDER'
    Then the user's application for service provider is stored for review
    And the admin approves the application
    Then the user data is added to the arraylist





















