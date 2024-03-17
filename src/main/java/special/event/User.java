package special.event;

public class User {

    String email;
    String password;
    String type;

    String firstName;
    String lastName;

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


    public void setType(String type) {
        this.type = type;}
}