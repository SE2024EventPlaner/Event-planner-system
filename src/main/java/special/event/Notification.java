package special.event;
import components.UserComponent;
import repositories.EventRepository;
import repositories.UserRepository;
import special.event.User;

import java.text.SimpleDateFormat;
import java.util.Date;
public class Notification {
    User sender;
    String message;
    String seviceMessage;
    Event event;
    NotificationType type;
    boolean approved;
    String state = "reject";
    Date sentDateTime;
    public enum NotificationType {
        ADMINANNOUNCEMENT,
        REPLYANNOUNCEMENT,
        RESERVATIONREQUEST,
        ACCOUNTREQUEST
    }




    public Notification() {

    }
    //create notification with its type
    public Notification(User  sender, String message, NotificationType type, boolean approved) {
        this.sender = sender;
        this.message = message;
        this.type = type;
        this.approved = approved;
        this.sentDateTime = new Date();
    }

    //create ANNOUNCEMENT notification
    public  void createAnnouncement(User sender, String announcementMessage) {
        this.sender = sender;
        this.message = "ADMIN " + sender.getFirstName() + " sent the following announcement: "
                + announcementMessage;
        this.approved = false;
        this.type = NotificationType.ADMINANNOUNCEMENT;
        this.sentDateTime = new Date();
    }
    public  void createReplyMessage(User sender, Boolean approved,Event event) {
        this.sender = sender;
        this.event = event;
        String state = "reject";
        if(approved.equals(true)){
            state = "accept";
        }
        this.message = "The "+sender.getType() +" "+ sender.getFirstName() +" "
                +state + " Your reservation request";

        this.approved = approved;
        this.type = NotificationType.REPLYANNOUNCEMENT;
        this.sentDateTime = new Date();
    }

    //create REQUEST notification
    public void createReservationRequest(User sender, Event event) {
        this.sender = sender;
        this.message = "The USER " + sender.getFirstName() +
                " submitted a reservation request";
        this.event = event;
        this.approved = false;
        this.type = NotificationType.RESERVATIONREQUEST;

        this.sentDateTime = new Date();

    }
    public void createAccountCreationRequest(User sender, String SeviceMessage) {
        this.sender = sender;
        this.message = "Someone submitted an account creation request with the following name and email: "
                + sender.getFirstName() + " - " + sender.getEmail();
        this.seviceMessage = seviceMessage;
        this.approved = false;
        this.type = NotificationType.ACCOUNTREQUEST;

        this.sentDateTime = new Date();

    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public User getSender() {
        return sender;
    }

    public void setSenderId(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    public String getSentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(sentDateTime);
    }
    public void setSentDateTime(Date sentDateTime) {
        this.sentDateTime = sentDateTime;
    }
    public void setSeviceMessage(String SeviceMessage){
        this.seviceMessage = SeviceMessage;
    }
    public String getSeviceMessage(){
        return seviceMessage;
    }
    public void sendAdminAnnouncement(){
        if(type.equals(NotificationType.ADMINANNOUNCEMENT)){
            User reciever;
            for(int i = 0; i< UserRepository.users.size();i++){
                reciever = UserRepository.users.get(i);
                if(!this.sender.getEmail().equals(reciever.getEmail())){
                    reciever.addNotification(this);
                }
            }
            System.out.println("The message was sent successfully !");
        }
    }
    public void sendReservationRequest(){
        if(type.equals(NotificationType.RESERVATIONREQUEST)){
            User provider = null;
            Event event1;
            for(int i = 0; i< EventRepository.events.size(); i++){
                event1 = EventRepository.events.get(i);
                if(event1.equals(this.event)){
                    provider =  event1.getEventOwner();
                    break;
                }
            }
            if(!provider.equals(null))
            provider.addNotification(this);
            System.out.println("Your request was sent successfully !");
        }
    }

    public void sendCreationRequest(){
        if(type.equals(NotificationType.ACCOUNTREQUEST)){
        for (User admin : UserRepository.users) {
            if (admin.getType().equals("ADMIN")) {
                admin.addNotification(this);
            }
        }
        System.out.println("Wait for the admin's approval to access your account ! ");
        }

    }
    public void sendReplyMessage(User reciever){
        if(type.equals(NotificationType.REPLYANNOUNCEMENT)){
            for (User user : UserRepository.users) {
                if (user.equals(reciever)) {
                    user.addNotification(this);
                }
            }
            System.out.println("Your reply was sent successfully !");
        }

    }






    public String showNtificationDetails(){
        String details;
        if(type.equals(NotificationType.REPLYANNOUNCEMENT)){
            details="The "+sender.getType() +" "+ sender.getFirstName() +" " +state + " Your request to book "+event.getNameOfEvent()+" event";


        }else if(type.equals(NotificationType.RESERVATIONREQUEST)){
            details="The USER "+ sender.getFirstName() +" "+sender.getLastName()+" with this email: "+sender.getEmail() +"\nsubmitted a reservation request with this description: \n"
           +"\nEvent ID: " + event.getIdOfEvent()
           +"\nEvent Name: " + event.getNameOfEvent()
           +"\nLocation: " + event.getPlaceOfEvent().getLocationOfPlace()
           +"\nStart Time: " + event.getEventStartTime()
           +"\nEnd Time: " + event.getEventEndTime()
           +"\nCost: " + event.getCostOfEvent()
           +"\nStatus: " + event.getstatusOfEvent()
           +"\n------------------------"
            +"\nDo you agree to the request?";

        }else if(type.equals(NotificationType.ADMINANNOUNCEMENT)){
        details="No details";

        }else{
            details="The USER "+ sender.getFirstName() +" "+sender.getLastName()+
                    " submitted a service porovider account creation request with this description: \n"
                    +seviceMessage
                    +"\n------------------------"
                    +"\nDo you agree to the request?";

        }
        return details;
    }
}

