package special.event;
import java.util.ArrayList;
import java.util.List;

public class User {

    String email;
    String password;
    String type;

    String firstName;
    String lastName;
    String message;

    List<Notification> notifications = new ArrayList<>();

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
}
