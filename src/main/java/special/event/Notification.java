package special.event;
import components.UserComponent;
import repositories.UserRepository;
import special.event.User;

import java.text.SimpleDateFormat;
import java.util.Date;
public class Notification {
    User sender;
    String message;
    String seviceMessage;
    String reservationDetails;
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
    public  void createReplyMessage(User sender, Boolean approved) {
        this.sender = sender;
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
    public void createReservationRequest(User sender, String reservationDetails) {
        this.sender = sender;
        this.message = "The USER " + sender.getFirstName() +
                " submitted a reservation request";
        this.reservationDetails = reservationDetails;
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
    public void sendAdminAnnouncement(User sender){
        if(type.equals(NotificationType.ADMINANNOUNCEMENT)){
            User reciever;
            for(int i = 0; i< UserRepository.users.size();i++){
                reciever = UserRepository.users.get(i);
                if(!sender.getEmail().equals(reciever.getEmail())){
                    reciever.addNotification(this);
                }
            }
            System.out.println("The message was sent successfully !");
        }
    }
    public String showNtificationDetails(){
        String details;
        if(type.equals(NotificationType.REPLYANNOUNCEMENT)){
            details="The "+sender.getType() +" "+ sender.getFirstName() +" " +state + " Your request to book a  event ";
            //need editing

        }else if(type.equals(NotificationType.RESERVATIONREQUEST)){
            details="The USER "+ sender.getFirstName() +" "+sender.getLastName()+
                    " submitted a reservation request with this description \n"
                    +reservationDetails+"\nDo you agree to the request?";
            //need editing
        }else{
            details="The USER "+ sender.getFirstName() +" "+sender.getLastName()+
                    " submitted a service porovider account creation request with this description "
                    +seviceMessage+"\nDo you agree to the request?";

        }
        return details;
    }
}

