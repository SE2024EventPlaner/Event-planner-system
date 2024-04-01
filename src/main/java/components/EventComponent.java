package components;

import repositories.EventRepository;
import special.event.Event;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.logging.Logger;

public class EventComponent {
    
    Boolean i=true;
    public EventRepository eventRepository = new EventRepository();
    private static final Logger logger = Logger.getLogger(EventComponent.class.getName());

    public  Event theEventExists(String nameOfEvent, String idOfEvent) {

        for (Event event : EventRepository.events) {
            if (event.getNameOfEvent().equals(nameOfEvent) && event.getIdOfEvent().equals(idOfEvent)) {
                return event;
            }
        }
        return null;

    }
    public  boolean checkSimilarityEvent(String nameOfPlace, LocalDateTime eventStartTime, LocalDateTime eventEndTime, String locationOfPlace) {
        for (Event event : EventRepository.events) {
            if (event.getPlaceOfEvent().getNameOfPlace().equals(nameOfPlace) &&
                    event.getEventStartTime().isEqual(eventStartTime) &&
                    event.getEventEndTime().isEqual(eventEndTime) &&
                    event.getEventLocation().equals(locationOfPlace)) {
                return true;
            }
        }
        return false;
    }


    public  boolean checkIdOfEvent(String id) {
        if (String.valueOf(id).length() != 6 || id.equals("000000")) {

            logger.info("ID of event must not be  zeros or more/less than 6 numbers");
            return false;
        } else
            return true;
    }

    public static  LocalDateTime dateConverter(String date){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            return  LocalDateTime.parse(date, formatter);


    }

    public boolean deleteEvent(String name, String id) {
        Event eventToDelete = theEventExists(name, id);
        if (eventToDelete == null) {
            logger.info("The event you want to delete does not exist");
            return false;
        } else {
            Iterator<Event> iterator = EventRepository.events.iterator();
            while (iterator.hasNext()) {
                Event event = iterator.next();
                if (event.equals(eventToDelete)) {
                    iterator.remove(); 
                    logger.info("The event was successfully deleted");
                    return true;
                }
            }
            return false;
        }
    }

    public  boolean addEvent(String nameOfEvent, String idOfEvent, float costOfEvent,float eventConstructionCost,
                             LocalDateTime eventStartTime, LocalDateTime eventEndTime
            , String nameOfPlace, int capacityOfPlace,
                             String locationOfPlace, String ownerEmail, String ownerPassword) {

           if (costOfEvent<= 0) {
            i=false;
             }
        if (theEventExists(nameOfEvent, idOfEvent) == null && checkIdOfEvent(idOfEvent) &&i) {
            EventRepository.events.add(new Event(nameOfEvent, idOfEvent, costOfEvent,eventConstructionCost, eventStartTime, eventEndTime, nameOfPlace, capacityOfPlace, locationOfPlace, ownerEmail, ownerPassword));
            logger.info("The event was added successfully");
            return true;
        } else

            logger.info("The event you are trying to add already exists");
        return false;

    }

}
