package special.event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import repositories.EventRepository;

public class Event {
    private String nameOfEvent;
    private String idOfEvent;
    public static final String[] serviceOfEvent = new String[]{"DJ", "Restaurant", "Studio", "People to organize event", "Decorations"};
    private float costOfEvent;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static String statusOfEvent;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private Place placeOfEvent;
    User eventOwner;

    public Event() {
    }

    public Event(String nameOfEvent, String idOfEvent, float costOfEvent,
                 LocalDateTime eventStartTime, LocalDateTime eventEndTime
            , String nameOfPlace, int capacityOfPlace,
                 String locationOfPlace, String ownerEmail, String ownerPassword) {
        this.nameOfEvent = nameOfEvent;
        this.idOfEvent = idOfEvent;
        this.costOfEvent = costOfEvent;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        placeOfEvent = new Place(nameOfPlace, capacityOfPlace, locationOfPlace);
        eventOwner = new User(ownerEmail, ownerPassword);


    }



    public String getIdOfEvent() {
        return this.idOfEvent;
    }

    public void setIdOfEvent(String idOfEvent) {
        this.idOfEvent = idOfEvent;
    }

    public String getNameOfEvent() {
        return this.nameOfEvent;
    }

    public void setNameOfEvent(String nameOfEvent) {
        this.nameOfEvent = nameOfEvent;
    }

    public float getCostOfEvent() {
        return this.costOfEvent;
    }

    public void setCostOfEvent(float costOfEvent) {
        this.costOfEvent = costOfEvent;
    }

    public LocalDateTime getEventStartTime() {
        return this.eventStartTime;
    }

    public void setEventStartTime(LocalDateTime eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public LocalDateTime getEventEndTime() {
        return this.eventEndTime;
    }

    public void setEventEndTime(LocalDateTime eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getEventLocation() {
        return this.placeOfEvent.getLocationOfPlace();
    }

    public String getstatusOfEvent() {
        return statusOfEvent;
    }

    public void setstatusOfEvent(String statusOfEvent) {
        Event.statusOfEvent = statusOfEvent;
    }

    public static Event theEventExists(String nameOfEvent, String idOfEvent) {
        new EventRepository();
        Iterator var3 = EventRepository.events.iterator();

        Event event;
        do {
            if (!var3.hasNext()) {
                return null;
            }

            event = (Event)var3.next();
        } while(!event.getNameOfEvent().equals(nameOfEvent) || !event.getIdOfEvent().equals(idOfEvent));

        return event;
    }

    public static boolean checkIdOfEvent(String id) {
        if (!(id.equals(0) && id.length() == 6)) {
            return true;
        } else {
            System.out.println("ID of event must not be  zeros or more/less than 6 numbers");
            return false;
        }
    }
  

    public static boolean checkCostOfEvent(float cost) {
        if (cost != 0.0F && !(cost < 0.0F)) {
            return true;
        } else {
            System.out.println("Cost of event must not be  zeros negative value ");
            return false;
        }
    }

    public static boolean addEvent(String nameOfEvent, String idOfEvent, float costOfEvent,
                                   LocalDateTime eventStartTime, LocalDateTime eventEndTime
            , String nameOfPlace, int capacityOfPlace,
                                   String locationOfPlace, String ownerEmail, String ownerPassword) {

        if (theEventExists(nameOfEvent, idOfEvent) == null && checkIdOfEvent(idOfEvent)== true &&checkCostOfEvent(costOfEvent)==true) {
            EventRepository R = new EventRepository();
            R.events.add(new Event(nameOfEvent, idOfEvent, costOfEvent, eventStartTime, eventEndTime, nameOfPlace, capacityOfPlace, locationOfPlace, ownerEmail, ownerPassword));
            System.out.println("The event was added successfully");
            return true;
        } else

            System.out.println("The event you are trying to add already exists");
        return false;

    }


    public static boolean deleteEvent(String name, String id) {
        if (theEventExists(name, id) == null) {
            System.out.println("The event you want to delete does not exist");
            return false;
        } else {
            new EventRepository();

            for(int i = 0; i < EventRepository.events.size(); ++i) {
                if (((Event)EventRepository.events.get(i)).getNameOfEvent().equals(name) && ((Event)EventRepository.events.get(i)).getIdOfEvent().equals(id)) {
                    EventRepository.events.remove(i);
                    System.out.println("The event was successfully deleted");
                }
            }

            return true;
        }
    }

    public Place getPlaceOfEvent() {
        return this.placeOfEvent;
    }

    public User getEventOwner() {
        return this.eventOwner;
    }

    public static List<Event> ReturnsEventBasedOnItsState(String statusOfEvent) {
        List<Event> EventBasedOnItsState = new ArrayList();
        Iterator var2 = EventRepository.events.iterator();

        while(var2.hasNext()) {
            Event event = (Event)var2.next();
            if (event.getstatusOfEvent().equals(statusOfEvent)) {
                EventBasedOnItsState.add(event);
            }
        }

        return EventBasedOnItsState;
    }

    public static LocalDateTime dateConverter(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            return LocalDateTime.parse(date, formatter);
        } catch (Exception var2) {
            System.out.println("Error: Invalid input string or format");
            return null;
        }
    }

    public static void printEvent(String nameOfEvent, String idOfEvent) {
        for(int i = 0; i < EventRepository.events.size(); ++i) {
            if (((Event)EventRepository.events.get(i)).getNameOfEvent().equals(nameOfEvent) && ((Event)EventRepository.events.get(i)).getIdOfEvent().equals(idOfEvent)) {
                System.out.println("Event(" + i + ")");
                System.out.println("Name Of Event :" + ((Event)EventRepository.events.get(i)).getNameOfEvent());
                System.out.println("ID Of Event :" + ((Event)EventRepository.events.get(i)).getIdOfEvent());
                System.out.println("Cost Of Event :" + ((Event)EventRepository.events.get(i)).getCostOfEvent());
                System.out.println("Event Start Time :" + String.valueOf(((Event)EventRepository.events.get(i)).getEventStartTime()));
                System.out.println("Event Start Time :" + String.valueOf(((Event)EventRepository.events.get(i)).getEventEndTime()));
                System.out.println("Location Of Event :" + ((Event)EventRepository.events.get(i)).getPlaceOfEvent().getLocationOfPlace());
                System.out.println("Place Of Event :" + ((Event)EventRepository.events.get(i)).getPlaceOfEvent().getNameOfPlace());
                System.out.println("Capacity Of Event :" + ((Event)EventRepository.events.get(i)).getPlaceOfEvent().getCapacityOfPlace());
                System.out.println("\n");
            }
        }

    }
                           }
