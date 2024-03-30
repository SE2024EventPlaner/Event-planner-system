package special.event;
import components.UserComponent;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class BookEvent {
    private String eventId;
    private String location;
    private float costOfEvent;
    private LocalDateTime bookingDate;
    private float userBalance;
    private String errorMessage;
    private String unavailableEventId;
    User loggedInUser ;


    UserComponent userComponent =new UserComponent();
    Event event1,event2,event3;







    @Given("that an event with ID {string} and with location {string} is available for booking for user with Email {string} and with password {string}")
    public void thatAnEventWithIDAndWithLocationIsAvailableForBookingForUserWithEmailAndWithPassword(String id, String location, String email, String password) {

        this.location = location;
        this.eventId = id;
        event1 =BookingSystem.findEventByIdAndLocation(id,location);
        loggedInUser= userComponent.validateLogin(email,password);
        assertNotNull(event1);
        assertNotNull(loggedInUser);


    }


    @When("The user is trying to book an event with a budget of {string} and date with {string}")
    public void theUserIsTryingToBookAnEventWithABudgetOfAndDateWith(String s1, String s2) {
        try {
            // Convert string to float
            this.userBalance = Float.parseFloat(s1);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input string");
        }
        try {
            // Define a DateTimeFormatter for parsing the date-time string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            // Parse the string to LocalDateTime using the defined formatter
            this.bookingDate = LocalDateTime.parse(s2, formatter);

        } catch (Exception e) {
            System.out.println("Error: Invalid input string or format");
        }
        assertTrue(BookingSystem.bookEvent(this.eventId,this.location,this.bookingDate,this.userBalance,this.loggedInUser));


    }
    @Then("the system should confirm the booking with a success message")
    public void theSystemShouldConfirmTheBookingWithASuccessMessage() {
        System.out.println("The Event is booked");

    }
    @Then("the event status should be updated to {string}")
    public void theEventStatusShouldBeUpdatedTo(String string) {

        event1.setstatusOfEvent(string);
    }


    //second scenario
    @Given("that an event with ID {string} and location {string} is not available for booking")
    public void thatAnEventWithIDAndLocationIsNotAvailableForBooking(String id, String location) {

        assertNull(BookingSystem.findEventByIdAndLocation(id,location));

    }

    @Then("the system should display an error message indicating that the event is not available for booking")
    public void theSystemShouldDisplayAnErrorMessageIndicatingThatTheEventIsNotAvailableForBooking() {
        System.out.println("The event you are trying to book is not available");
    }

    // thired scenario
    @Given("that an event with ID {string} and location with {string} is available for booking")
    public void thatAnEventWithIDAndLocationWithIsAvailableForBooking(String string, String string2) {
        event2= BookingSystem.findEventByIdAndLocation(string,string2);
        assertNotNull(event2);
    }
    @Given("the booking date is {string}")
    public void theBookingDateIs(String s) {

        try {
            // Define a DateTimeFormatter for parsing the date-time string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            // Parse the string to LocalDateTime using the defined formatter
            this.bookingDate = LocalDateTime.parse(s, formatter);

        } catch (Exception e) {
            System.out.println("Error: Invalid input string or format");
        }

    }
    @When("the user tries to book the event for invalid date")
    public void theUserTriesToBookTheEventForInvalidDate() {
        assertFalse(BookingSystem.isBookingDateValid(this.bookingDate,event2));
    }
    @Then("the system should display an error message indicating that the booking date is not valid")
    public void theSystemShouldDisplayAnErrorMessageIndicatingThatTheBookingDateIsNotValid() {
        System.out.println("The date you entered is invalid");
    }

//last senario

    @Given("that an event with ID {string} and located in {string} is available for booking")
    public void thatAnEventWithIDAndLocatedInIsAvailableForBooking(String S, String S2) {
        event3 =BookingSystem.findEventByIdAndLocation(S,S2);
        assertNotNull(event3);
    }
    @Given("the user's account balance is {string} dollars")
    public void theUserSAccountBalanceIsDollars(String s) {

        try {
            // Convert string to float
            this.userBalance = Float.parseFloat(s);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input string");
        }

    }
    @When("the user tries to book the event with unsufficient funds")
    public void theUserTriesToBookTheEventWithUnsufficientFunds() {
        assertFalse(BookingSystem.isUserBalanceSufficient(this.userBalance,event3));
    }
    @Then("the system should display an error message indicating insufficient funds")
    public void theSystemShouldDisplayAnErrorMessageIndicatingInsufficientFunds() {
        System.out.println("The user tries to book the event with unsufficient funds");
    }




}