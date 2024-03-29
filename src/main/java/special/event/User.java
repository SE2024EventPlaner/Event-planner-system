package special.event;
import java.util.ArrayList;
import java.util.List;
import repositories.EventRepository;
import java.util.ArrayList;
import java.util.List;

public class User {


    private String email;
    private String password;
    private String type;
    private String firstName;
    private String lastName;
    private String message;
    List<Notification> notifications = new ArrayList<>();
    public  ArrayList<Event>bookedEvent1 = new ArrayList<>();//to store an event that the service not agree yet .
    public  ArrayList<Event>bookedEvent2 = new ArrayList<>();////to store an event that the service agree.

    public User(String email, String password, String type,String firstName,String lastName) {
        this.email = email;
        this.password = password;
        this.type = type;
        this.firstName= firstName;
        this.lastName=lastName;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }

    public void setType(String type) {
        this.type = type;}

    public void addNotification(Notification n){
        notifications.add(n);
    }
    public List<Notification> getNotifications(){
        return notifications;
    }
    public Boolean isNotificationExit(Notification n) {
        for (int i = 0; i < notifications.size(); i++) {
            if (n.equals(notifications.get(i))) {
                return true;
            }
        }
        return false;
    }


    public  List<Event> getBookedEventsForUser(String Email) {
        List<Event> bookedEvents = new ArrayList<>();
        for (Event event : this.bookedEvent2) {
            if (event.getBookedUser() != null && event.getBookedUser().getEmail().equals(Email)) {
                bookedEvents.add(event);
            }
        }
        return bookedEvents;
    }
}
