Feature: Search

  Scenario : search about events by name only
    Given I am on the vendor search page
    When I select search by event name <"name">
    And I submit the search
    Then I should see the events with this name


  Scenario : search about events by name and location
    Given I am on the vendor search page
    When I select search by event name <"name"> and event location <"location">
    And I submit the search
    Then I should see the events with this name in the required location



  Scenario :search about events by name and price
    Given I am on the vendor search page
    When I select search by event name <"name"> and the price range between Min Price <"Min_Price "> and Max Price <"Max_Price">
    And I submit the search
    Then I should see the events with this name and within this price



  Scenario :search about events by name ,price and location
    Given I am on the vendor search page
    When I select search by event name <"name"> ,event location <"location"> and event price range between Min Price <"Min_Price "> and Max Price <"Max_Price">
    And I submit the search
    Then I should see the events with this name and within this price in the required location




