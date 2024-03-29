package special.event;
import java.time.LocalDateTime;
import java.util.Scanner;
import repositories.EventRepository;
public class BookingSystem {
    public static boolean bookEvent(String eventId, String location, LocalDateTime bookingDate, float userBalance,User loggedInUser) {
        Event event = findEventByIdAndLocation(eventId,location);
        if (loggedInUser.bookedEvent2.size()==0){
            loggedInUser.setAccountBalance(userBalance);}
        else {
            loggedInUser.setAccountBalance(loggedInUser.getAccountBalance());}
        if (event != null) {
            if (isBookingDateValid(bookingDate,event)){
                if (loggedInUser.getAccountBalance() >= event.getCostOfEvent() ) {
                    // add to arraylist event
                    loggedInUser.bookedEvent1.add(event);
                    Notification ReservationNotification = new Notification();
                    ReservationNotification.createReservationRequest(loggedInUser,event);
                    ReservationNotification.sendReservationRequest();
                    System.out.println("We will send you a notification when your reservation request is accepted !");
                    return true;
                } else  {
                    System.out.println("Insufficient funds. Booking failed.");
                }
            } else {
                System.out.println("Invalid booking date. Please choose a future date.");
            }
        } else {
            System.out.println("Event with ID " + eventId + " in " + location + " not available for booking.");
        }
        return false;
    }
    public static Event findEventByIdAndLocation(String eventId, String location) {
        EventRepository eventRepository = new EventRepository();
        for (Event event : EventRepository.events) {
            if (event.getIdOfEvent().equals(eventId) && event.getPlaceOfEvent().getLocationOfPlace().equals(location)) {
                return event;
            }
        }
        return null;
    }
    public static boolean processPayment(String CardNumber,Event event,User loggedInUser) {
        // Validate card number format
        if (!isValidCardNumberFormat(CardNumber)) {
            System.out.println("Invalid card number format.");
            return false;
        } else {
            float value = loggedInUser.getAccountBalance()- event.getCostOfEvent();
            loggedInUser.setAccountBalance(value) ;
            loggedInUser.bookedEvent2.add(event);
            System.out.println("Booking successful! ");
        }

        return  true;
    }
    public static boolean isValidCardNumberFormat(String CardNumber) {
        // Check if the card number is a 16-digit numeric string
        return CardNumber.matches("^\\d{16}$");
    }
    public static boolean isBookingDateValid(LocalDateTime bookingDate,Event event) {
        return event.getEventStartTime().equals(bookingDate);
    }
    public static boolean isUserBalanceSufficient (float userBalance,Event event) {
        if(event.getCostOfEvent()<= userBalance)
            return true;
        else
            return false;

    }

}

