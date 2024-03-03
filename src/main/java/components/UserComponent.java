package components;

import org.apache.maven.surefire.shared.lang3.ObjectUtils;
import repositories.UserRepository;
import special.event.User;
import java.util.regex.*;


public class UserComponent {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public User validateLogin(String username, String password) {
        for (User user : UserRepository.users) {
            if (user.getEmail().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }


    public boolean validateUserType(User user, String type) {

        return user != null && user.getType().equals(type);
    }

    public boolean validateSignup(String firstName, String finalName, String email, String password, String confirmPassword, String type) {
        if (firstName != null && finalName != null && isValidEmail(email) && isValidPassword(password) && confirmPassword.equals(password) && type != null)
            return true;
        else
            return false;
    }

    public boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPassword(String password) {
        if (password.length() < 8 || password.length() > 20) {
            return false;
        }

        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$";
        return password.matches(pattern);
    }


}