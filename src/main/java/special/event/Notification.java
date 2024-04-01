package special.event;
import repositories.UserRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class Notification {
    User sender;
    String message;
    String seviceMessage;
    Event event;
    NotificationType type;
    boolean approved;
    String state = "reject";
    Date sentDateTime;
    static final String THE_USER = "The USER ";
    public enum NotificationType {
        ADMINANNOUNCEMENT,
        REPLYANNOUNCEMENT,
        RESERVATIONREQUEST,
        ACCOUNTREQUEST
    }
    private static final Logger logger = Logger.getLogger(Notification.class.getName());



    public Notification() {

    }


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
        if(approved.equals(true)){
            this.state = "accept";
        }
        this.message = "The "+sender.getType() +" "+ sender.getFirstName() +" "
                +state + " Your reservation request";

        this.approved = approved;
        this.type = NotificationType.REPLYANNOUNCEMENT;
        this.sentDateTime = new Date();
    }

    public void createReservationRequest(User sender, Event event) {
        this.sender = sender;
        this.message = THE_USER+" " + sender.getFirstName() +
                " submitted a reservation request";
        this.event = event;
        this.approved = false;
        this.type = NotificationType.RESERVATIONREQUEST;

        this.sentDateTime = new Date();

    }
    public void createAccountCreationRequest(User sender, String seviceMessage) {
        this.sender = sender;
        this.message = "Someone submitted an account creation request with the following name and email: "
                + sender.getFirstName() + " - " + sender.getEmail();
        this.seviceMessage = seviceMessage;
        this.approved = false;
        this.type = NotificationType.ACCOUNTREQUEST;

        this.sentDateTime = new Date();

    }


    public Event getEvent() {
        return event;
    }



    public String getMessage() {
        return message;
    }



    public NotificationType getType() {
        return type;
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

    public void sendAdminAnnouncement(){
        if(type.equals(NotificationType.ADMINANNOUNCEMENT)){
            User reciever;
            for(int i = 0; i< UserRepository.users.size();i++){
                reciever = UserRepository.users.get(i);
                if(!this.sender.getEmail().equals(reciever.getEmail())){
                    reciever.addNotification(this);
                }
            }
            logger.info("The message was sent successfully !");
        }
    }
    public void sendReservationRequest(){
        if(type.equals(NotificationType.RESERVATIONREQUEST)){
            User provider =this.event.getEventOwner() ;
            provider.addNotification(this);
            logger.info("Your request was sent successfully !");
        }
    }

    public void sendCreationRequest(){
        if(type.equals(NotificationType.ACCOUNTREQUEST)){
            for (User admin : UserRepository.users) {
                if (admin.getType().equals("ADMIN")) {
                    admin.addNotification(this);
                }
            }
            logger.info("Wait for the admin's approval to access your account ! ");
        }

    }
    public void sendReplyMessage(User reciever){
        if(type.equals(NotificationType.REPLYANNOUNCEMENT)){
            for (User user : UserRepository.users) {
                if (user.equals(reciever)) {
                    user.addNotification(this);
                }
            }
            logger.info("Your reply was sent successfully !");
        }

    }






    public String showNtificationDetails(){
        String details;
        if(type.equals(NotificationType.REPLYANNOUNCEMENT)){
            details="The "+sender.getType() +" "+ sender.getFirstName() +" " +state + " Your request to book "+event.getNameOfEvent()+" event";


        }else if(type.equals(NotificationType.RESERVATIONREQUEST)){

            details= THE_USER+" " + sender.getFirstName() +" "+sender.getLastName()+" with this email: "+sender.getEmail() +"\nsubmitted a reservation request with this description: \n"
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
            details= THE_USER+" " + sender.getFirstName() +" "+sender.getLastName()+
                    " submitted a service porovider account creation request with this description: \n"
                    +this.seviceMessage
                    +"\n------------------------"
                    +"\nDo you agree to the request?";

        }
        return details;
    }
}