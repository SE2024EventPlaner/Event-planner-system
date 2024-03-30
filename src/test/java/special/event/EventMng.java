package special.event;

import components.UserComponent;
import components.EventComponent;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import repositories.EventRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.Assert.*;

/*        this.user = new UserComponent().validateLogin(email, password);
 */
public class EventMng {

    public EventMng(){}
    EventRepository EVENTS;
    String nameOfEvent;
    String idOfEvent;
    String nameOfPlace;
    float costOfEvent;
    float constructionCost;
    int capacityOfPlace;
    LocalDateTime eventStartTime;
    LocalDateTime eventEndTime;
    String locationOfPlace;
    User user;
    Event event;
    boolean A;
    EventComponent eventComponent= new EventComponent();



    @Given("The user login as Service Provider with   {string} and {string}")
    public void theUserLoginAsServiceProviderWithAnd(String email, String password) {
        this.user = new UserComponent().validateLoginAsServiceProvider(email, password);
        // assertNotNull(this.user);

    }

    @When("The user add a new event")
    public void theUserAddANewEvent() {

    }
    @Then("The User enter name of event with {string}")
    public void theUserEnterNameOfEventWith(String name) {
        this.nameOfEvent=name;
    }
    @Then("The User enter id of event with {string}")
    public void theUserEnterIdOfEventWith(String id) {
        this.idOfEvent=id;
    }
    @Then("The User enter Cost of the event with {string}")
    public void theUserEnterCostOfTheEventWith(String cost) {
        try {

            this.costOfEvent = Float.parseFloat(cost);


        } catch (NumberFormatException e) {

            System.out.println("Invalid input. Please enter a valid float number.");
        }


    }



    @Then("The User enter Construction Cost Of Event with {string}")
    public void theUserEnterConstructionCostOfEventWith(String constructionCost) {
        try {

            this.constructionCost = Float.parseFloat(constructionCost);


        } catch (NumberFormatException e) {

            System.out.println("Invalid input. Please enter a valid float number.");
        }


    }


    @Then("The User enter  Event start time with {string}")
    public void theUserEnterEventStartTimeWith(String startTime) {

        this.eventStartTime=LocalDateTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    }
    @Then("The User enter  Event end time with {string}")
    public void theUserEnterEventEndTimeWith(String endTime) {

        this.eventEndTime=LocalDateTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
    @Then("The User enter Location of event with {string}")
    public void theUserEnterLocationOfEventWith(String location) {
        this.locationOfPlace=location;
    }
    @Then("The User enter Place of event with {string}")
    public void theUserEnterPlaceOfEventWith(String place) {
        this.nameOfPlace=place;
    }
    @Then("The User enter Capacity of Place with {string}")
    public void theUserEnterCapacityOfPlaceWith(String capacity) {

        try {

            this.capacityOfPlace = Integer.parseInt(capacity);

        } catch (NumberFormatException e) {

            System.out.println("Invalid input. Please enter a valid integer number.");
        }

    }


    @Then("the new event must  be added to the event list")
    public void theNewEventMustBeAddedToTheEventList() {
        assertTrue(eventComponent.addEvent(nameOfEvent,idOfEvent,costOfEvent,constructionCost,eventStartTime,eventEndTime,nameOfPlace,capacityOfPlace,locationOfPlace,user.getEmail(),user.getPassword()));

    }
    @Then("The user should see a message that the event was added successfully")
    public void theUserShouldSeeAMessageThatTheEventWasAddedSuccessfully() {
        System.out.println("The event was added successfully");
    }



    //second scenario

    @When("The user add an existing event with the id {string} and the name {string}")
    public void theUserAddAnExistingEventWithTheIdAndTheName(String id, String name) {
        this.event =eventComponent.theEventExists(name,id);
        assertNotNull(this.event);
    }

    @Then("the new event must not be added to the event list")
    public void theNewEventMustNotBeAddedToTheEventList() {

    }
    @Then("The user should see a message that adding the event failed")
    public void theUserShouldSeeAMessageThatAddingTheEventFailed() {
        assertTrue(true);

    }

    //third scenario
    @Then("The User fill in {string} with {string}")
    public void theUserFillInWith(String string, String string2)
    {
        if (string.equals("idOfEvent"))
        {
            assertFalse(eventComponent.checkIdOfEvent(string2));
        }
        else if(string.equals("capacityOfPlace"))
        {
            try {
                int c = Integer.parseInt(string2);
                assertFalse(Place.checkCapacityOfPlace(c));

            } catch (NumberFormatException e) {

                System.out.println("Invalid input. Please enter a valid integer number.");
            }

        } else if (string.equals("costOfEvent"))
        {
            try {

                float c = Float.parseFloat(string2);
                assertFalse(eventComponent.checkCostOfEvent(c));

            } catch (NumberFormatException e) {

                System.out.println("Invalid input. Please enter a valid float number.");
            }

        }
    }
    @Then("The user should see  a {string}")
    public void theUserShouldSeeA(String string) {
        assert (true);
    }



    // delete feature

    @When("the user deletes an event with not available id {string} and not available name {string}")
    public void theUserDeletesAnEventWithNotAvailableIdAndNotAvailableName(String name, String id) {
        assertFalse(eventComponent.deleteEvent(name,id ));
    }
    @Then("The user should see a message that this event does not exist")
    public void theUserShouldSeeAMessageThatThisEventDoesNotExist() {
        assert (true);

    }


//edit feature

    @When("the user selects valid  {string} and {string}")
    public void theUserSelectsValidAnd(String id , String name) {
        this.event=eventComponent.theEventExists(name,id);
        assertNotNull(this.event);
    }
    @When("The system will delete the selected event from events list")
    public void theSystemWillDeleteTheSelectedEventFromEventsList() {
        assertTrue(eventComponent.deleteEvent(this.event.getNameOfEvent(),this.event.getIdOfEvent()));
    }
    @Then("The user will see that the event has been modified")
    public void theUserWillSeeThatTheEventHasBeenModified() {
        System.out.println("TheEventHasBeenModified");
    }
    @Then("the modified event will be added to the events list")
    public void theModifiedEventWillBeAddedToTheEventsList() {
        eventComponent.addEvent(this.nameOfEvent,this.idOfEvent,this.costOfEvent,this.constructionCost,this.eventStartTime,this.eventEndTime,this.nameOfPlace,this.capacityOfPlace,this.locationOfPlace,this.event.eventOwner.getEmail(),this.event.eventOwner.getPassword());
    }




    @Given("the user wants to edit an event")
    public void theUserWantsToEditAnEvent() {

    }
    @When("the user selects an invalid event {string} and {string}")
    public void theUserSelectsAnInvalidEventAnd(String name, String id) {
        assertNull(eventComponent.theEventExists(name,id));
    }
    @Then("the system displays an error message")
    public void theSystemDisplaysAnErrorMessage() {
        System.out.println("The event you requested does not exist");
    }




    @Given("The user is logged in as a Service Provider with email {string} and password {string}")
    public void theUserIsLoggedInAsAServiceProviderWithEmailAndPassword(String email, String password) {
        this.user = new UserComponent().validateLoginAsServiceProvider(email, password);

    }


    @When("The user tries to add a new event with location {string} and name of place {string} and start time {string}  and end time {string}")
    public void theUserTriesToAddANewEventWithLocationAndNameOfPlaceAndStartTimeAndEndTime(String location, String namePlace, String startTime, String endTime) {
        locationOfPlace= location;
        this.eventEndTime=LocalDateTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.eventStartTime=LocalDateTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        nameOfPlace = namePlace;
    }
    @When("there is another event with this places and dates")
    public void thereIsAnotherEventWithThisPlacesAndDates() {
        assertTrue(eventComponent.checkSimilarityEvent(nameOfPlace,eventStartTime,eventEndTime,locationOfPlace));
    }
    @Then("A message {string} should be displayed")
    public void aMessageShouldBeDisplayed(String string) {
        System.out.println(string);
    }

    @When("The user tries to add a new event with start time {string} and end time {string}")
    public void theUserTriesToAddANewEventWithStartTimeAndEndTime(String start_time, String end_time) {
        this.eventEndTime=LocalDateTime.parse(end_time, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.eventStartTime=LocalDateTime.parse(start_time, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
    @Then("No save of information should occur")
    public void noSaveOfInformationShouldOccur() {
        assertEquals(eventStartTime,eventEndTime);
    }

}

