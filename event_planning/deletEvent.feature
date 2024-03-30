Feature: Delete Feature

  Scenario Outline :delete an event with Valid information
    Given The user login as Service Provider with   "<email>" and "<password>"
    When The information is valid name is "<name of event>" and id is "<id of event>"
    Then The specified event  must be deleted from  event list
    And The User should see the event successfully deleted
    Examples:
      |id of event    |name of event               |
      |101010         |Birthday event              |
      |202020         |Marriage event              |
      |303030         |Graduation event            |
      |404040         |gender determination event  |
      |505050         |Official event              |


  Scenario Outline: Trying to delete an event that does not exist
    Given The user login as Service Provider with   "<email>" and "<password>"
    When the user deletes an event with not available id "<id of event>" and not available name "<name of event>"
    Then The user should see a message that this event does not exist

    Examples:
      |id of event    |name of event                        |
      |101010         |Birthday event                       |
      |202020         |Marriage event                       |
      |303030         |Graduation event                     |
      |404040         |gender determination event           |
      |505050         |Official event                       |