package special.event;

import components.Checker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import repositories.EventRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Search {
    List<Event> resultEvents = new ArrayList<>();
    String searchName;
    String searchLocatin;
    String searchMinPrice;
    String xsearchMaPrice;
    String searchdate;

    @Given("I am on the vendor search page")
    public void iAmOnTheVendorSearchPage() { }

    @When("I select search by event name {string}")
    public void iSelectSearchByEventName(String searchName) { }

    @When("I submit the search")
    public void iSubmitTheSearch() { }

    @Then("I should see the events with name {string}")
    public void iShouldSeeTheEventsWithNameIfTheyExist(String existSearchName) {
        this.resultEvents = Checker.checkNameOfEvent(existSearchName);
        assertNotNull(resultEvents);
    }
    @Then("I should not see the events with name {string}")
    public void iShouldNotSeeTheEventsWithName(String nonExistSearchName) {
        this.resultEvents = Checker.checkNameOfEvent(nonExistSearchName);
        assertNull(searchName);
    }
    @Then("The meesage no result appears")
    public void theMeesageNoResultAppears() {

    }

    @When("I select search by event name and event location")
    public void iSelectSearchByEventNameAndEventLocation() {

    }
    @Then("I should see the events with this name {string} in the required location {string}")
    public void iShouldSeeTheEventsWithThisNameInTheRequiredLocation(String existSearchName, String existSearchLocation) {
        this.resultEvents = Checker.checkNameAndLocationOfEvent(existSearchName,existSearchLocation);
        assertNotNull(resultEvents);
    }


    @Then("I should not see the events with name {string} and location {string}")
    public void iShouldNotSeeTheEventsWithNameAndLocation(String nonExistSearchName, String nonExistSearchLocation) {
        this.resultEvents = Checker.checkNameAndLocationOfEvent(nonExistSearchName,nonExistSearchLocation);
        assertNull(resultEvents);
    }

    @When("I select search by event name {string} and the price range between Min Price {string} and Max Price {string}")
    public void iSelectSearchByEventNameAndThePriceRangeBetweenMinPriceAndMaxPrice(String string, String string2, String string3) {
        this.resultEvents = Checker.checkNameAndPriceOfEvent(string,string2,string3);
        assertNotNull(resultEvents);
    }
    @Then("I should see the events with this name and within this price")
    public void iShouldSeeTheEventsWithThisNameAndWithinThisPrice() {

    }
    @When("I select search by event name {string} or  no event within the price range Min Price {string} and Max Price {string}")
    public void iSelectSearchByEventNameOrNoEventWithinThePriceRangeMinPriceAndMaxPrice(String string, String string2, String string3) {
        this.resultEvents = Checker.checkNameAndPriceOfEvent(string,string2,string3);
        assertNull(resultEvents);
    }
    @Then("I should not see any result")
    public void iShouldNotSeeAnyResult() {  }

    @When("I select search by event name {string} ,event location {string} and event price range between Min Price {string} and Max Price {string}")
    public void iSelectSearchByEventNameEventLocationAndEventPriceRangeBetweenMinPriceAndMaxPrice(String string, String string2, String string3, String string4) {
        this.resultEvents = Checker.checkNameLocationAndPriceOfEvent(string,string2,string3,string4);
        assertNotNull(resultEvents);
    }
    @Then("I should see the events with this name and within this price in the required location")
    public void iShouldSeeTheEventsWithThisNameAndWithinThisPriceInTheRequiredLocation() {

    }
    @When("I select search by event name {string}, event location {string} and event within the price range Min Price {string} and Max Price {string}")
    public void iSelectSearchByEventNameEventLocationAndEventWithinThePriceRangeMinPriceAndMaxPrice(String string, String string2, String string3, String string4) {
        this.resultEvents = Checker.checkNameLocationAndPriceOfEvent(string,string2,string3,string4);
        assertNull(resultEvents);
    }

    @When("I select to show all events")
    public void iSelectToShowAllEvents() {

    }
    @Then("I should see all events")
    public void iShouldSeeAllEvents() {
        this.resultEvents = EventRepository.events;
    }
}
