package special.event;
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
    User loggedInUser = UserRepository.users.get(0);
    Event event1,event2,event3;

   /* private Event findEventByIdAndLocation(String eventId, String location) {
        for (Event event : EventRepository.events) {

            if (event.getIdOfEvent().equals(eventId) && event.getPlaceOfEvent().getLocationOfPlace().equals(location)) {

                return event;
            }
        }
        return null;
    }*/
    // public String bookEvent(String eventId, String location, float bookingPrice, LocalDateTime bookingDate, float userBalance) {
    //   BookEvent bookEventInstance = new BookEvent();
    // Event eventToBook = bookEventInstance.findEventByIdAndLocation(eventId, location);
    //return "Booking success message";  // Return appropriate message after booking
    //}

    /* @Given("that an event with ID {string} is available for booking")
     public void thatAnEventWithIDIsAvailableForBooking(String eventId) {
        this.eventId = eventId;


     }
     @Given("the venue is located in {string}")
     public void theVenueIsLocatedIn(String location) {
         this.location = location;

     }
     @Given("the booking price is {float} dollars")
     public void theBookingPriceIsDollars(float bookingPrice) {
        // this.costOfEvent = bookingPrice;
     }
     @When("the user tries to book the venue with the ID {string} for the next month")
     public void theUserTriesToBookTheVenueWithTheIDForTheNextMonth(String eventId) {
        // this.eventId = eventId;
         //this.bookingDate = LocalDateTime.now().plusMonths(1);
         this.userBalance = 2500; // Replace with the actual user's balance
         BookingSystem.bookEvent(eventId, location, bookingDate, userBalance);
     }
     @Then("the system should confirm the booking with a success message")
     public void theSystemShouldConfirmTheBookingWithASuccessMessage() {
         System.out.println("The Event is booked");

     }
     @Then("the event status should be updated to {string}")
     public void theEventStatusShouldBeUpdatedTo(String status) {
         assertNotNull("Event ID should not be null", eventId);
         assertNotNull("Location should not be null", location);

         Event event = BookingSystem.findEventByIdAndLocation(eventId, location);
         Assert.assertNotNull("Event should not be null",event);

         boolean eventStatus = event.getstatusOfEvent();
         assertNotNull("Event status should not be null", eventStatus);

         // Log relevant information for debugging
         System.out.println("Event ID: " + eventId);
         System.out.println("Location: " + location);
         System.out.println("Expected Status: " + status);
         System.out.println("Actual Status: " + eventStatus);

         // Perform the assertion
         assertEquals("Event status does not match", status, eventStatus);
     }*/
    @Given("that an event with ID {string} and with location {string} is available for booking")
    public void thatAnEventWithIDAndWithLocationIsAvailableForBooking(String id, String location) {

        this.location = location;
        this.eventId = id;
        event1 =BookingSystem.findEventByIdAndLocation(id,location);
        assertNotNull(event1);
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


   /* @Given("that an event with ID {string} is not available for booking")
    public void thatAnEventWithIDIsNotAvailableForBooking(String eventId) {
        this.unavailableEventId = eventId;
        // Simulate the event not being available in the repository
        assertNull(BookingSystem.findEventByIdAndLocation(eventId,location));
    }
    @When("the user tries to book the venue with the ID {string}")
    public void theUserTriesToBookTheVenueWithTheID(String string) {
        this.eventId = eventId;
        // Assume you have a method in your booking system to attempt booking
        errorMessage = String.valueOf(BookingSystem.bookEvent(eventId, location, LocalDateTime.now().plusMonths(1), 700));

    }
    @Then("the system should display an error message indicating that the event is not available for booking")
    public void theSystemShouldDisplayAnErrorMessageIndicatingThatTheEventIsNotAvailableForBooking() {
        assertEquals("The event is not available for booking", errorMessage);
    }
    @Given("the event is located in {string}")
    public void theEventIsLocatedIn(String location) {
        this.location = location;
    }
    @Given("the booking price is {string}")
    public void theBookingPriceIs(String bookingPrice) {
        this.costOfEvent = Float.parseFloat(bookingPrice);
    }
    @When("the user tries to book the venue with the ID {string} for a past date")
    public void theUserTriesToBookTheVenueWithTheIDForAPastDate(String string) {
        this.eventId = eventId;
        this.bookingDate = LocalDateTime.now().minusDays(1); // Booking for a past date
        this.userBalance = 2000; // Assuming sufficient funds
        this.errorMessage = String.valueOf(BookingSystem.bookEvent(eventId, location,  bookingDate, userBalance));


    }
    @Then("the system should display an error message indicating that the booking date is not valid")
    public void theSystemShouldDisplayAnErrorMessageIndicatingThatTheBookingDateIsNotValid() {
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("invalid booking date"));
    }
    /*@Given("the user's account balance is {double}")
    public void theUserSAccountBalanceIs(Double userBalance) {
        this.userBalance = userBalance;
    }
    @Given("the user's account balance is {double} dollars")
    public void theUserSAccountBalanceIsDollars(float userBalance) {
        this.userBalance = userBalance;
    }
    @Then("the system should display an error message indicating insufficient funds")
    public void theSystemShouldDisplayAnErrorMessageIndicatingInsufficientFunds() {
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("insufficient funds"));

    }*/

}
