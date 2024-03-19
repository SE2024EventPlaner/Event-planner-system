package special.event;
import java.time.LocalDateTime;
import repositories.EventRepository;
public class BookingSystem {

    public static boolean bookEvent(String eventId, String location, LocalDateTime bookingDate, float userBalance) {
        Event event = findEventByIdAndLocation(eventId,location);

        if (event != null) {
            if (isBookingDateValid(bookingDate,event)){
                if (userBalance >=  event.costOfEvent ) {
                    //event.setEventEndTime(bookingDate.plusHours(2)); // Assuming a 2-hour event
                    System.out.println("Booking successful! Event ID: " + eventId + " has been booked for " + bookingDate);
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


    public static boolean isBookingDateValid(LocalDateTime bookingDate,Event event) {
        return event.getEventStartTime().equals(bookingDate);

        //return bookingDate.isAfter(LocalDateTime.now());


    }

    public static boolean isUserBalanceSufficient (float userBalance,Event event) {
        if(event.getCostOfEvent()<= userBalance)
            return true;
        else
            return false;

    }

}
































