package special.event;
import java.util.ArrayList;
import java.util.List;


public class User {


    private String email;
    private String password;
    private String type;
    private String firstName;
    private String lastName;
    private String message;
    private float accountBalance;
    List<Notification> notifications = new ArrayList<>();
    public List<Event>bookedEvent1 = new ArrayList<>();
    public List<Event>bookedEvent2 = new ArrayList<>();

    public User(String email, String password, String type,String firstName,String lastName) {
        this.email = email;
        this.password = password;
        this.type = type;
        this.firstName= firstName;
        this.lastName=lastName;
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



    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public float getAccountBalance() {
        return accountBalance;
    }
    public void setAccountBalance(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void addNotification(Notification n){
        notifications.add(n);
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