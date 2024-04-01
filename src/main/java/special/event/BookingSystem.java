package special.event;
import java.time.LocalDateTime;
import repositories.EventRepository;
import java.util.logging.Logger;
public class BookingSystem {
    private static final Logger logger3 = Logger.getLogger(BookingSystem.class.getName());
    private BookingSystem() {
    }
    public static boolean bookEvent(String eventId, String location, LocalDateTime bookingDate, float userBalance,User loggedInUser) {
        Event event = findEventByIdAndLocation(eventId,location);
        if (loggedInUser.bookedEvent2.isEmpty()){
            loggedInUser.setAccountBalance(userBalance);}
        else {
            loggedInUser.setAccountBalance(loggedInUser.getAccountBalance());}
        if (event != null) {
            if (isBookingDateValid(bookingDate,event)){
                if (loggedInUser.getAccountBalance() >= event.getCostOfEvent() ) {
                    loggedInUser.bookedEvent1.add(event);
                    Notification reservationNotification = new Notification();
                    reservationNotification.createReservationRequest(loggedInUser,event);
                    reservationNotification.sendReservationRequest();
                    logger3.info("We will send you a notification when your reservation request is accepted !");
                    return true;
                } else  {
                    logger3.info("Insufficient funds. Booking failed.");
                }
            } else {
                logger3.info("Invalid booking date. Please choose a future date.");
            }
        } else {
            logger3.info("Event with ID {} in {} not available for booking.".format(eventId, location));
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
    public static boolean processPayment(String cardNumber, Event event, User loggedInUser) {
        if (!isValidCardNumberFormat(cardNumber)) {
            logger3.info("Invalid card number format.");
            return false;
        } else {
            float value = loggedInUser.getAccountBalance()- event.getCostOfEvent();
            loggedInUser.setAccountBalance(value) ;
            loggedInUser.bookedEvent2.add(event);
            logger3.info("Booking successful! ");
        }
        return  true;
    }
    public static boolean isValidCardNumberFormat(String cardNumber) {
        return cardNumber.matches("^\\d{16}$");
    }
    public static boolean isBookingDateValid(LocalDateTime bookingDate,Event event) {
        return event.getEventStartTime().equals(bookingDate);
    }
    public static boolean isUserBalanceSufficient(float userBalance, Event event) {
        return event.getCostOfEvent() <= userBalance;
    }

}