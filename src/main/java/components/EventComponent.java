package components;

import repositories.EventRepository;
import special.event.Event;
import special.event.Place;
import special.event.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventComponent {
    public EventRepository eventRepository = new EventRepository();
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
        if (id.equals(0) || id.length() != 6) {
            System.out.println("ID of event must not be  zeros or more/less than 6 numbers");
            return false;
        } else
            return true;
    }

    public  boolean checkCostOfEvent(float cost) {
        if (cost == 0 || cost < 0) {
            System.out.println("Cost of event must not be  zeros negative value ");
            return false;
        } else
            return true;
    }

    public static  LocalDateTime dateConverter(String date){
        try {
            // Define a DateTimeFormatter for parsing the date-time string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            // Parse the string to LocalDateTime using the defined formatter
            return  LocalDateTime.parse(date, formatter);

        } catch (Exception e) {
            System.out.println("Error: Invalid input string or format");
        }
        return null;

    }

    public  boolean deleteEvent(String name, String id) {
        if (theEventExists(name, id) == null) {
            System.out.println("The event you want to delete does not exist");
            return false;
        } else {

            for (int i = 0; i < EventRepository.events.size(); i++) {
                if( EventRepository.events.get(i).getNameOfEvent().equals(name)&&EventRepository.events.get(i).getIdOfEvent().equals(id))
                {
                    EventRepository.events.remove(i);
                    System.out.println("The event was successfully deleted");
                }
            }

            return true;
        }
    }
    public  boolean addEvent(String nameOfEvent, String idOfEvent, float costOfEvent,float eventConstructionCost,
                             LocalDateTime eventStartTime, LocalDateTime eventEndTime
            , String nameOfPlace, int capacityOfPlace,
                             String locationOfPlace, String ownerEmail, String ownerPassword) {

        if (theEventExists(nameOfEvent, idOfEvent) == null && checkIdOfEvent(idOfEvent) &&checkCostOfEvent(costOfEvent)) {
            EventRepository.events.add(new Event(nameOfEvent, idOfEvent, costOfEvent,eventConstructionCost, eventStartTime, eventEndTime, nameOfPlace, capacityOfPlace, locationOfPlace, ownerEmail, ownerPassword));
            System.out.println("The event was added successfully");
            return true;
        } else

            System.out.println("The event you are trying to add already exists");
        return false;

    }

    public  void printEvent (String nameOfEvent,String idOfEvent){

        for (int i = 0; i < EventRepository.events.size(); i++) {
            if( EventRepository.events.get(i).getNameOfEvent().equals(nameOfEvent)&&EventRepository.events.get(i).getIdOfEvent().equals(idOfEvent))
            {
                System.out.println("Event"+"("+i+")");
                System.out.println("Name Of Event :"+EventRepository.events.get(i).getNameOfEvent());
                System.out.println("ID Of Event :"+EventRepository.events.get(i).getIdOfEvent());
                System.out.println("Cost Of Event :"+ EventRepository.events.get(i).getCostOfEvent());
                System.out.println("Event Start Time :"+EventRepository.events.get(i).getEventStartTime());
                System.out.println("Event Start Time :"+EventRepository.events.get(i).getEventEndTime());
                System.out.println("Location Of Event :"+ EventRepository.events.get(i).getPlaceOfEvent().getLocationOfPlace());
                System.out.println("Place Of Event :" +EventRepository.events.get(i).getPlaceOfEvent().getNameOfPlace());
                System.out.println("Capacity Of Event :" +EventRepository.events.get(i).getPlaceOfEvent().getCapacityOfPlace());

                System.out.println("\n");
            }
        }
    }
}