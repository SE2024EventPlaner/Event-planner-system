Feature: Edit Event


  Scenario Outline: User selects a valid event ID to edit
    Given The user login as Service Provider with   "<email>" and "<password>"
    When the user selects valid  "<id of event>" and "<name of event>"
    And The system will delete the selected event from events list
    Then The User enter name of event with "Birthday event"
    And The User enter id of event with "707070"
    And  The User enter Cost of the event with "2000"
    And The User enter  Event start time with "2024-03-02T10:15:30.908732"
    And The User enter  Event end time with "2024-07-02T23:00:00.908732"
    And The User enter Location of event with 'Ramallah'
    And The User enter Place of event with "City Inn Palace Hotel"
    And The User enter Capacity of Place with "180"
    Then The user will see that the event has been modified
    And the modified event will be added to the events list

    Examples:
      |id of event    |name of event          |
      |101010         |Birthday               |
      |202020         |Marriage               |
      |303030         |Graduation             |
      |404040         |gender determination   |
      |505050         |Official               |

    #And the user can modify the Name of the event to "new name"
    #And the user can modify the id of the event to "new id"
    #And the user can modify the Cost of the event "new cost"
    #And the user can modify the place of event to "new place"
    #And the user can modify the location of place to "new place"
    #And the user can modify the capacity of place to "1000"
    #And the user can modify the Event start time to "2024-03-02T10:15:30.908732"
    #And the user can modify the Event end time to "2024-08-06T10:15:30.908732"
    #Then The event will be modified
    #And the modified event will be added to the events list


  Scenario Outline: User selects an invalid event ID and name to edit
    Given The user login as Service Provider with   "<email>" and "<password>"
    And the user wants to edit an event
    When the user selects an invalid event "<id of event>" and "<name of event>"
    Then the system displays an error message
    Examples:
      |id of event    |name of event          |
      |101010         |Birthday               |
      |202020         |Marriage               |
      |303030         |Graduation             |
      |404040         |gender determination   |
      |505050         |Official               |
