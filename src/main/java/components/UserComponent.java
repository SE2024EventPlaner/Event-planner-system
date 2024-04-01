package components;

import repositories.UserRepository;
import special.event.User;
import java.util.regex.*;

public class UserComponent {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    public UserRepository repo=new UserRepository();
    public User validateLogin(String username, String password){

        for (User user: UserRepository.users){
            if(user.getEmail().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }
    public boolean existEmail(String email) {
        for (User user : UserRepository.users) {
            if (user.getEmail().equals(email) ) {
                return true;
            }
        }
        return false;
    }
    public boolean existPassword(String username,String password){
        for (User user: UserRepository.users){
            if(user.getEmail().equals(username) ){
                return user.getPassword().equals(password);
            }
        }
        return false;
    }
    public User validateLoginAsServiceProvider(String username, String password){
        UserRepository userRepository = new UserRepository();
        for (User user: UserRepository.users){
            if(user.getEmail().equals(username) && user.getPassword().equals(password)&&user.getType().equals("SERVICE_PROVIDER")){
                return user;
            }
        }
        return null;
    }
    public boolean validateUserType(User user, String type){
        return user != null && user.getType().equals(type);
    }

    public boolean validateSignup(String firstName, String finalName, String email, String password, String confirmPassword, String type) {
        if (firstName != null && finalName != null && isValidEmail(email) && isValidPassword(password) && confirmPassword.equals(password) && type != null)
            return true;
        else
            return false;
    }
    public boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
    public boolean isValidPassword(String password) {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\p{Punct}])[A-Za-z\\d\\p{Punct}]+$";
        return password.length() >= 8 && password.matches(pattern);
    }
}
