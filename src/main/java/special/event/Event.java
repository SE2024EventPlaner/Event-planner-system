package special.event;

import com.thoughtworks.qdox.model.expression.Constant;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.maven.surefire.shared.lang3.ObjectUtils;
import repositories.EventRepository;
import repositories.UserRepository;


public class Event {
    String nameOfEvent;
    String idOfEvent;
    // ArrayList<String> serviceOfEvent ;
    public static final String[] serviceOfEvent = {"DJ", "Restaurant", "Studio", "People to organize event", "Decorations"};

    float costOfEvent;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime eventStartTime;
    LocalDateTime eventEndTime;
    Place placeOfEvent;
    User eventOwner;


    //   public ArrayList<Reservation> timeSlots;
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




  /*  public Event(){
        timeSlots = new ArrayList<Reservation>();
    }

    public Event(String name ,String id ,)*/

    public String getIdOfEvent() {
        return idOfEvent;
    }

    public void setIdOfEvent(String idOfEvent) {
        this.idOfEvent = idOfEvent;
    }

    public String getNameOfEvent() {
        return nameOfEvent;
    }

    public void setNameOfEvent(String nameOfEvent) {
        this.nameOfEvent = nameOfEvent;
    }

    public float getCostOfEvent() {
        return costOfEvent;
    }

    public void setCostOfEvent(float costOfEvent) {
        this.costOfEvent = costOfEvent;
    }

    public LocalDateTime getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(LocalDateTime eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public LocalDateTime getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(LocalDateTime eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public static Event theEventExists(String nameOfEvent, String idOfEvent) {
        EventRepository eventRepository = new EventRepository();
        for (Event event : EventRepository.events) {
            if (event.getNameOfEvent().equals(nameOfEvent) && event.getIdOfEvent().equals(idOfEvent)) {
                return event;
            }
        }
        return null;

    }

    public static boolean checkIdOfEvent(String id) {
        if (id.equals(0) || id.length() != 6) {
            System.out.println("ID of event must not be  zeros or more/less than 6 numbers");
            return false;
        } else
            return true;
    }

    public static boolean checkCostOfEvent(float cost) {
        if (cost == 0 || cost < 0) {
            System.out.println("Cost of event must not be  zeros negative value ");
            return false;
        } else
            return true;
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
            EventRepository eventRepository = new EventRepository();

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


   //مهمممممممممممممممممممممم
  /*  public boolean reserveSlot(Reservation reservation){
        // validate.
    }*/


}
