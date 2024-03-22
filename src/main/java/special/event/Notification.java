package special.event;
import special.event.User;

import java.util.Date;
public class Notification {
    User sender;
    String message;
    NotificationType type;
    boolean approved;
    Date sentDateTime;
    public enum NotificationType {
        ANNOUNCEMENT,
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
        this.message = "Admin " + sender.getFirstName() + " sent the following announcement: " + announcementMessage;
        this.approved = false;
        this.type = NotificationType.ANNOUNCEMENT;
        this.sentDateTime = new Date();
    }
    public  void createReplyMessage(User sender, Boolean approved) {
        this.sender = sender;
        String state = "rejected";
        if(approved.equals(true)){
            state = "accepted";
        }
        this.message = sender +" "+ sender.getFirstName() + " Your reservation request has been " +state;
        this.approved = approved;
        this.type = NotificationType.ANNOUNCEMENT;
        this.sentDateTime = new Date();
    }

    //create REQUEST notification
    public void createReservationRequest(User sender, String reservationDetails) {
        this.sender = sender;
        this.message = "User " + sender.getFirstName() +
                " submitted a reservation request with the following details: " + reservationDetails;

        this.approved = false;
        this.type = NotificationType.RESERVATIONREQUEST;

        this.sentDateTime = new Date();

    }
    public void createAccountCreationRequest(User sender, String SeviceMessage) {
        this.sender = sender;
        this.message = "Someone submitted an account creation request with the following name and email: "
                + sender.getFirstName() + " - " + sender.getEmail()
                + ".\n Account details: " + SeviceMessage;
        ;
        this.approved = false;
        this.type = NotificationType.ACCOUNTREQUEST;

        this.sentDateTime = new Date();

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
    public Date getSentDateTime() {
        return sentDateTime;
    }

    public void setSentDateTime(Date sentDateTime) {
        this.sentDateTime = sentDateTime;
    }
}

