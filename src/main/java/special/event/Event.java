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
    private String nameOfEvent;
    private String idOfEvent;
    public static final String[] serviceOfEvent = {"DJ", "Restaurant", "Studio", "People to organize event", "Decorations"};

    private float costOfEvent;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    String statusOfEvent="unbook";
    LocalDateTime eventStartTime;
    LocalDateTime eventEndTime;
    Place placeOfEvent;
    User eventOwner;

    private User bookedUser;

    //   public ArrayList<Reservation> timeSlots;
    public Event() {

    }
    public Event(String nameOfEvent, String idOfEvent, float costOfEvent,
                 LocalDateTime eventStartTime, LocalDateTime eventEndTime,
                 String nameOfPlace, int capacityOfPlace,
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
    public String getEventLocation() {  return placeOfEvent.getLocationOfPlace(); }

    public String getstatusOfEvent() {
        return statusOfEvent;
    }

    public void setstatusOfEvent( String statusOfEvent) {
        this.statusOfEvent= statusOfEvent;
    }

    public User getBookedUser() {
        return bookedUser;
    }

    public void setBookedUser(User bookedUser) {
        this.bookedUser = bookedUser;
    }

    public void setEventStatus(String status) {
        this.statusOfEvent = status;
    }




    public Place getPlaceOfEvent() {
        return placeOfEvent;
    }
    public User getEventOwner(){return eventOwner;}





    public boolean bookEvent(User user) {
        if (this.bookedUser == null) {
            this.bookedUser = user;
            return true;
        } else {
            System.out.println("Event is already booked.");
            return false;
        }
    }
}