package special.event;

import java.nio.file.Path;
import java.time.LocalDateTime;
import repositories.UserRepository;


public class Event {
    private String nameOfEvent;
    private String idOfEvent;
    private static final String[] SERVICE_OF_EVENT = {"DJ", "Restaurant", "Studio", "People to organize event", "Decorations"};

    private float costOfEvent;
    String statusOfEvent="unbook";
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private Place placeOfEvent;
    private User eventOwner;
    private Path imagePath;
    private float eventConstructionCost;
    private User bookedUser;


    public Event(String nameOfEvent, String idOfEvent, float costOfEvent,float eventConstructionCost,
                 LocalDateTime eventStartTime, LocalDateTime eventEndTime,
                 String nameOfPlace, int capacityOfPlace,
                 String locationOfPlace, String ownerEmail, String ownerPassword) {
        this.nameOfEvent = nameOfEvent;
        this.idOfEvent = idOfEvent;
        this.costOfEvent = costOfEvent;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventConstructionCost=eventConstructionCost;
        placeOfEvent = new Place(nameOfPlace, capacityOfPlace, locationOfPlace);

        for (User user: UserRepository.users){
            if(user.getEmail().equals(ownerEmail) && user.getPassword().equals(ownerPassword)){
                eventOwner =user;
            }
        }


    }


    public String getIdOfEvent() {
        return idOfEvent;
    }
    public void setpath(Path p) {
        this.imagePath = p;
    }

    public Path getpath() {
        return imagePath;
    }



    public String getNameOfEvent() {
        return nameOfEvent;
    }



    public float getCostOfEvent() {
        return costOfEvent;
    }



    public LocalDateTime getEventStartTime() {
        return eventStartTime;
    }



    public LocalDateTime getEventEndTime() {
        return eventEndTime;
    }


    public String getEventLocation() {  return placeOfEvent.getLocationOfPlace(); }

    public String getstatusOfEvent() {
        return statusOfEvent;
    }

    public void setstatusOfEvent( String statusOfEvent) {
        this.statusOfEvent= statusOfEvent;
    }



    public float getEventConstructionCost()
    {
        return eventConstructionCost;
    }


    public Place getPlaceOfEvent() {
        return placeOfEvent;
    }
    public User getEventOwner(){return eventOwner;}



}