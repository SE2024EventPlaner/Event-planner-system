Feature: Search

  Scenario: search about events by name only
    Given I am on the search page
    When I select search by event name "Birthday"
    And I submit the search
    Then I should see the events with this name


  Scenario: search about non-existing events by name
    Given I am on the search page
    When I select search by event name "non-existing"
    And I submit the search
    Then I should not see any result
    And The meesage no result appears


  Scenario: search about events by name and location
    Given I am on the search page
    When I select search by event name "Birthday" and event location "Nablus"
    And I submit the search
    Then I should see the events with this name in the required location

  Scenario: search about non-existing events by name and location
    Given I am on the search page
    When I select search by event name "non-existing" or event location "non-existing"
    And I submit the search
    Then I should not see any result
    And The meesage no result appears


  Scenario:search about events by name and price
    Given I am on the search page
    When I select search by event name "Birthday" and the price range between Min Price "500 " and Max Price "2000"
    And I submit the search
    Then I should see the events with this name and within this price


  Scenario: search about non-existing  events by name and price
    Given I am on the search page
    When I select search by event name "non-existing" or  no event within the price range Min Price "10 " and Max Price "90"
    And I submit the search
    Then I should not see any result
    And The meesage no result appears


  Scenario:search about events by name ,price and location
    Given I am on the search page
    When I select search by event name "Birthday" ,event location "Nablus" and event price range between Min Price "500" and Max Price "2000"
    And I submit the search
    Then I should see the events with this name and within this price in the required location


  Scenario: search about non-existing events by name ,price and location
    Given I am on the search page
    When I select search by event name "non-existing" or event location "non-existing" or or  no event within the price range Min Price "10" and Max Price "90"
    And I submit the search
    Then I should not see any result
    And The meesage no result appears

  Scenario: showing all events
    Given I am on the search page
    When I need to see all events
    Then I should see all events

