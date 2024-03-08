package special.event;

import repositories.UserRepository;
import special.event.User;
import components.UserComponent;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;
        String password1;
        String email;
        String userType = "";
        String confirmPassword;
        int y = 0;
        int x = 0;
        String type = "";
        UserComponent userComponent = new UserComponent();
        User loggedInUser = null;

        System.out.println("\n\n********   Welcome to the Event Planner System   ********\n");

        do {
            System.out.print("Press 1 for login, 2 for Signup: ");
            x = scanner.nextInt();

            if (x == 1) {
                System.out.println("\n***         Login         ***\n");
                System.out.print("Username:");
                username = scanner.next();
                System.out.print("Password:");
                password = scanner.next();

                // Validate login using UserRepository
                loggedInUser = userComponent.validateLogin(username, password);

                if (loggedInUser != null) {
                    System.out.println("Login successful!");
                    break; // Exit the loop after successful login
                } else {
                    System.out.println("Invalid credentials. Please try again.\n");
                }

            } else if (x == 2) {
                System.out.println("***         Signup         ***\n");

                System.out.print("Enter first name: ");
                String firstName = scanner.next();
                System.out.print("Enter last name: ");
                String lastName = scanner.next();
                while (true) {
                    System.out.print("Enter email: ");
                    email = scanner.next().trim();
                    if (userComponent.isValidEmail(email)) {
                        break;
                    } else {
                        System.out.println("The email you entered is invalid. Please try again.");
                    }
                }
                while (true) {
                    System.out.print("Enter password:");
                    password1 = scanner.next();
                    if (userComponent.isValidPassword(password1)) {
                        break;
                    } else {
                        System.out.println("The password should contain at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one of the following symbols: !@#$%^");
                    }
                }
                while (true) {
                    System.out.print("Confirm password:");

                    confirmPassword = scanner.next();
                    if (confirmPassword.equals(password1)) {
                        break;
                    } else {
                        System.out.println("The confirmed password does not match the original password.");
                    }
                }

                System.out.println("Enter 1 for SERVICE_PROVIDER \nOr 2 for USER");
                do {
                    y = scanner.nextInt();
                    if (y == 1 || y == 2) {
                        userType = (y == 1) ? "SERVICE_PROVIDER" : "USER";
                        break;
                    } else {
                        System.out.println("Invalid value. Please enter 1 for SERVICE_PROVIDER or 2 for USER");
                    }
                } while (true);

                boolean isValid = userComponent.validateSignup(firstName, lastName, email, password1, confirmPassword, userType);

                if (isValid) {
                    System.out.println("Creating an account successfully");
                    UserRepository.addToUsers(new User(email, password1, userType, firstName, lastName));
                    loggedInUser=userComponent.validateLogin(email,password1);
                    if (type.equals("SERVICE_PROVIDER")) {
                        // Store the application for service provider review
                        // Add more logic here for the service provider application
                        System.out.println("Your application as a service provider is stored for review.");
                    }

                    // Add the user data to the UserRepository

                    break; // Exit the loop after successful signup
                } else {
                    System.out.println("Account creation failed.");
                    // Print the error message returned from the validateSignup method

                }

            } else {
                System.out.println("Invalid value\n");
            }
        } while (true); // Repeat until a valid input is provided


        type = loggedInUser.getType();
        if (userComponent.validateUserType(loggedInUser, type)) {
            if (type.equals("SERVICE_PROVIDER"))
                System.out.println("Logged in as a service provider.");
            else if (type.equals("ADMIN"))
                System.out.println("Logged in as an Admin.");
            else {
                System.out.println("Logged in as a regular user.");
            }
        }
    }
}
