Feature: notifications

  Scenario: Admin Approval for Business Account Creation
    Given the admin is on the notification page in the admin account with email "aaadmain78@gmail.com"
    When a user submits a request to create a business account from the email "barhamshifaa@gmail.com" and write the "message" of his service
    And the admin receives a notification regarding the request for a business account
    And the admin approves the request
    Then a confirmation message "Your request has been approved. You can now log in as a service provider" is sent to the user's email address "barhamshifaa@gmail.com"

  Scenario: Admin Rejection for Business Account Creation
    Given the admin is on the notification page in the admin account with email "aaadmain78@gmail.com"
    When a user submits a request to create a business account from the email "barhamshifaa@gmail.com" and write the "message" of his service
    And the admin receives a notification regarding the request for a business account
    And the admin rejects the request
    Then a rejection message "Your request has been rejected" is sent to the user's email address "barhamshifaa@gmail.com"

  Scenario: Announcement notification from admin to all users
    Given he admin is on the Announcement page in the admin account with email "aaadmain78@gmail.com"
    And the admin wants to send an announcement message to users "hello@gmail.com"
    When the admin submits the message "Updates to our Privacy Policy or Terms and Conditions"
    Then all users receive the announcement message


  Scenario: User Reservation Request Approval
    When a user with email "hello@gmail.com" submits a reservation request an Event
    And the service provider "hello3@gmail.com" receives a notification regarding the reservation request
    And the service provider approves the request
    Then a confirmation message "Your reservation request has been approved" is sent to the user as notification

  Scenario: User Reservation Request Rejection
    When a user with email "hello@gmail.com" submits a reservation request an Event
    And the service provider "hello3@gmail.com" receives a notification regarding the reservation request
    And the service provider rejects the request
    Then a rejection message "Your reservation request has been rejected" is sent to the user as notification

