package special.event;
import repositories.EventRepository;
import repositories.UserRepository;
import special.event.User;
import components.UserComponent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;
        String signuppass;
        String email;
        User user1;
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
                while (true) {
                    System.out.print("Username:");
                    username = scanner.next();
                    if (userComponent.existEmail(username)) {
                        break;
                    } else {
                        System.out.print("The email you entered does not exist. Please try again\n");
                    }
                }
                while (true) {
                    System.out.print("Password:");
                    password = scanner.next();
                    if (userComponent.existPassword(username, password)) {
                        break;
                    } else {
                        System.out.print("The Password is invalid. Please try again\n");
                    }
                }
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

                boolean existEmail = false;

                while (true) {
                    System.out.print("Enter email: ");
                    email = scanner.next().trim();

                    if (!userComponent.isValidEmail(email)) {
                        System.out.println("The email you entered is invalid. Please try again.");
                        continue;
                    }

                    for (User user : UserRepository.users) {
                        if (user.getEmail().equals(email)) {
                            existEmail = true;
                            break;
                        }
                    }

                    if (existEmail) {
                        System.out.println("The email you entered is already exist. Please enter another one.");
                        existEmail = false;
                    } else {
                        break;
                    }
                }


                while (true) {
                    System.out.print("Enter password: ");
                    signuppass = scanner.next().trim();
                    if (userComponent.isValidPassword(signuppass)) {
                        break;
                    } else {
                        System.out.println("The password should contain at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one of the following symbols: !@#$%^");
                    }
                }

                while (true) {
                    System.out.print("Confirm password:");

                    confirmPassword = scanner.next();
                    if (confirmPassword.equals(signuppass)) {
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

                boolean isValid = userComponent.validateSignup(firstName, lastName, email, signuppass, confirmPassword, userType);

                if (isValid) {
                    System.out.println("Creating an account successfully");
                    UserRepository.addToUsers(new User(email, signuppass, userType, firstName, lastName));
                    loggedInUser = userComponent.validateLogin(email, signuppass);
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
            if (type.equals("SERVICE_PROVIDER")) {
                System.out.println("\t**Hello in your profile **\t \n");
                System.out.println("Name" + loggedInUser.firstName + loggedInUser.getLastName() + "\n");
                System.out.println("Email" + loggedInUser.getEmail() + "\n");
                System.out.println("Password" + loggedInUser.getPassword() + "\n");
                System.out.println("\n\n");
                System.out.println("1-Edit your profile\n");
                System.out.println("3-Names of your events");
                System.out.println("3-The number of all your events \n");
                System.out.println("4-The number of events you have booked");
                System.out.println("3-Names of your events");
                System.out.println("");
            } else if (type.equals("ADMIN"))
                System.out.println("Logged in as an Admin.");


            else {
                System.out.println("Logged in as a regular user.");
                while (true) {
                    System.out.println("What would you like to do?");
                    System.out.println("1. View available events");
                    System.out.println("2. Book an event");
                    System.out.println("3. View booked events");
                    System.out.println("4. Cancel booked events");
                    System.out.println("5. Edit profile");
                    System.out.println("6. Logout");
                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println("Available Events:");
                            EventRepository eventRepository = new EventRepository();
                            List<Event> events = EventRepository.events;
                            for (Event event : events) {
                                System.out.println("Event ID: " + event.getIdOfEvent());
                                System.out.println("Event Name: " + event.getNameOfEvent());
                                System.out.println("Location: " + event.getPlaceOfEvent().getLocationOfPlace());
                                System.out.println("Start Time: " + event.getEventStartTime());
                                System.out.println("End Time: " + event.getEventEndTime());
                                System.out.println("Cost: " + event.getCostOfEvent());
                                System.out.println("Status: " + event.getstatusOfEvent());
                                System.out.println("------------------------");
                            }
                            break;

                        case 2:
                            System.out.println("Available Events:");
                            for (Event event : EventRepository.events) {
                                System.out.println("Event ID: " + event.getIdOfEvent());
                                System.out.println("Event Name: " + event.getNameOfEvent());
                                System.out.println("Location: " + event.getPlaceOfEvent().getLocationOfPlace());
                                System.out.println("Start Time: " + event.getEventStartTime());
                                System.out.println("End Time: " + event.getEventEndTime());
                                System.out.println("Cost: " + event.getCostOfEvent());
                                System.out.println("Status: " + event.getstatusOfEvent());
                                System.out.println("------------------------");
                            }
                            System.out.print("Enter your balance: ");
                            float userBalance = scanner.nextFloat();
                            System.out.print("Enter the ID of the event you want to book: ");
                            String eventId = scanner.next();
                            System.out.print("Enter booking date (yyyy-MM-dd'T'HH:mm:ss): ");
                            String bookingDateStr = scanner.next();
                            System.out.print("Enter the location of the event you want to book: ");
                            String location = scanner.next();

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                            LocalDateTime bookingDate;
                            try {
                                bookingDate = LocalDateTime.parse(bookingDateStr, formatter);
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd'T'HH:mm:ss.");
                                break;
                            }
                            boolean bookingSuccessful = BookingSystem.bookEvent(eventId, location, bookingDate, userBalance);
                            if (bookingSuccessful) {
                                System.out.println("Booking successful!");
                            } else {
                                System.out.println("Booking failed. Please try again.");
                            }
                            break;

                        case 3:

                            String userEmail = loggedInUser.getEmail();
                            List<Event> bookedEvents = BookingSystem.getBookedEventsForUser(userEmail);;
                            if (!bookedEvents.isEmpty()) {
                                System.out.println("Booked Events:");
                                for (Event event : bookedEvents) {
                                    System.out.println("Event Name: " + event.getNameOfEvent());
                                    System.out.println("Event ID: " + event.getIdOfEvent());
                                    System.out.println("Location: " + event.getPlaceOfEvent().getLocationOfPlace());
                                    System.out.println("Event Start Time: " + event.getEventStartTime());
                                    System.out.println("Event End Time: " + event.getEventEndTime());
                                    System.out.println("------------------------------------");
                                }
                            } else {
                                System.out.println("You have not booked any events.");
                            }
                            break;

                        case 4:
                            System.out.println("\nCancel booked events:");
                            boolean foundBookedEvents2 = false;
                            for (Event event : EventRepository.events) {
                                if (event.getBookedUser() != null && event.getBookedUser().getEmail().equals(loggedInUser.getEmail())) {
                                    // Display the booked event details
                                    System.out.println("Event ID: " + event.getIdOfEvent());
                                    System.out.println("Event Name: " + event.getNameOfEvent());
                                    System.out.println("Event Location: " + event.getPlaceOfEvent().getLocationOfPlace());
                                    System.out.println("Event Date: " + event.getEventStartTime());
                                    System.out.println();
                                    System.out.print("Do you want to cancel this event? (yes/no): ");
                                    String cancelChoice = scanner.next();
                                    if (cancelChoice.equalsIgnoreCase("yes")) {
                                        // Remove the booking
                                        event.setBookedUser(null);
                                        // Update event status
                                        event.setEventStatus("Available");
                                        System.out.println("Event has been cancelled successfully.");
                                    }

                                    foundBookedEvents2 = true; // flag to true as booked events
                                }
                            }
                            if (!foundBookedEvents2) {
                                System.out.println("No booked events found.");
                            }
                            break;






                            case 5:
                            // update user profile
                            System.out.println("\n Edit your profile:");
                            // Display current user profile information
                            System.out.println("Current Profile Information:");
                            System.out.println("First Name: " + loggedInUser.getFirstName());
                            System.out.println("Last Name: " + loggedInUser.getLastName());
                            System.out.println("Email: " + loggedInUser.getEmail());
                            scanner = new Scanner(System.in);
                            System.out.println("\nEnter new profile information (leave empty if no change):");
                            System.out.print("First Name: ");
                            String newFirstName = scanner.nextLine().trim();
                            if (!newFirstName.isEmpty()) {
                                loggedInUser.setFirstName(newFirstName);
                            }
                            System.out.print("Last Name: ");
                            String newLastName = scanner.nextLine().trim();
                            if (!newLastName.isEmpty()) {
                                loggedInUser.setLastName(newLastName);
                            }

                            System.out.print("Email: ");
                            String newEmail = scanner.nextLine().trim();
                            if (!newEmail.isEmpty()) {
                                // Add validation for new email if needed
                                loggedInUser.setEmail(newEmail);
                            }
                            System.out.print("Password: ");
                            String newPassword = scanner.nextLine().trim();
                            if (!newPassword.isEmpty()) {
                                // Add validation for new password if needed
                                loggedInUser.setPassword(newPassword);
                            }
                            System.out.println("User profile updated successfully.");
                            break;
                        case 6:
                            System.out.println("Logging out...");
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            }
        }
    }
}

