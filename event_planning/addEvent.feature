Feature: Adding new Event

  Scenario Outline:error whit inputs
    Given The user login as Service Provider with   "<email>" and "<password>"
    When The user add a new event
    Then The User fill in 'id of event' with "<id of event>"
    And The User fill in 'Capacity of Place' with "<Capacity of Place>"
    And The User fill in 'Cost of the event' with "<Cost of the event>"
    Then The user should see  a "<message>"
    And the new event must not be added to the event list
    Examples:
      |id of event |Capacity of Place|Cost of the event|message                                      |
      |000000      |200              |500              |ID of event must not be  zeros               |
      |9090901     |200              |500              |ID of event  must not be more than 6 numbers |
      |9090        |200              |500              |ID of event  must not be less than  6 numbers|
      |909090      |0                |500              |Capacity of place must  not be zero          |
      |909090      |-200             |500              |Capacity of place must be positive           |
      |909090      |200              |0                |Cost of event must not be zero $             |
      |909090      |200              |-500             |Cost of event must be positive               |


  Scenario Outline: trying to add event with an existing id and name
    Given The user login as Service Provider with   "<email>" and "<password>"
    When The user add an existing event with the id "<id of event>" and the name "<name of event>"
    Then the new event must not be added to the event list
    And The user should see a message that adding the event failed


    Examples:
      |id of event    |name of event          |
      |101010         |Birthday               |
      |202020         |Marriage               |
      |303030         |Graduation             |
      |404040         |gender determination   |
      |505050         |Official               |



  Scenario Outline: success information
    Given The user login as Service Provider with   "<email>" and "<password>"
    When The user add a new event
    Then The User enter name of event with "event"
    And The User enter id of event with "707070"
    And  The User enter Cost of the event with "2000"
    And  The User enter Construction Cost Of Event with "1500"
    And The User enter  Event start time with "2024-03-02T10:15:30.908732"
    And The User enter  Event end time with "2024-03-02T23:00:00.908732"
    And The User enter Location of event with 'Ramallah'
    And The User enter Place of event with "City Inn Palace Hotel"
    And The User enter Capacity of Place with "180"
    Then the new event must  be added to the event list
    And The user should see a message that the event was added successfully
    Examples:
      | email                   | password    |
      | hello3@gmail.com  | 123789      |



  Scenario: Conflict warning when adding an event with the same location and time as another event
    Given The user is logged in as a Service Provider with email "samyahamed22@gmail.com" and password "s1s2s300"
    When The user tries to add a new event with location "Nablus" and name of place "Farah hall for events" and start time "2024-03-29T06:00:00"  and end time "2024-03-29T08:00:00"
    And there is another event with this places and dates
    Then A message "It is not possible to add. There has been a conflict in dates and locations" should be displayed

  Scenario: Start time of event is equal to the end time of event
    Given The user is logged in as a Service Provider with email "samyahamed22@gmail.com" and password "s1s2s300"
    When The user tries to add a new event with start time "2024-03-05T10:00:00" and end time "2024-03-05T10:00:00"
    Then No save of information should occur
    And  A message "You need to set a valid duration for the event" should be displayed
