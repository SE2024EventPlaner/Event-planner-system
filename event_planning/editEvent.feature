Feature: Search

  Scenario: Search for an existing event by name
    Given I am on the vendor search page
    When I select search by event name "Birthday"
    And I submit the search
    Then I should see the events with name "Birthday"

  Scenario: Search for a non-existing event by name
    Given I am on the vendor search page
    When I select search by event name "non-existing"
    And I submit the search
    Then I should not see the events with name "non-existing"
    And The meesage no result appears

  Scenario: search about existing events by name and location
    Given I am on the vendor search page
    When I select search by event name and event location
    And I submit the search
    Then I should see the events with this name "Birthday" in the required location "Nablus"

  Scenario: search about non-existing events by name and location
    Given I am on the vendor search page
    When I select search by event name and event location
    And I submit the search
    Then I should not see the events with name "Birthday" and location "non-exist location"
    And The meesage no result appears


  Scenario:search about existing events by name and price
    Given I am on the vendor search page
    When I select search by event name "Birthday" and the price range between Min Price "600 " and Max Price "2000"
    And I submit the search
    Then I should see the events with this name and within this price


  Scenario: search about non-existing  events by name and price
    Given I am on the vendor search page
    When I select search by event name "Birthday" or  no event within the price range Min Price "100 " and Max Price "400"
    And I submit the search
    Then I should not see any result
    And The meesage no result appears


  Scenario:search about existing events by name ,price and location
    Given I am on the vendor search page
    When I select search by event name "Birthday" ,event location "Nablus" and event price range between Min Price "600 " and Max Price "2000"
    And I submit the search
    Then I should see the events with this name and within this price in the required location


  Scenario: search about non-existing events by name ,price and location
    Given I am on the vendor search page
    When I select search by event name "Birthday", event location "non-existing" and event within the price range Min Price "100" and Max Price "650"
    And I submit the search
    Then I should not see any result
    And The meesage no result appears

  Scenario: showing all events
    Given I am on the vendor search page
    When I select to show all events
    Then I should see all events
