package special.event;

import com.sun.source.tree.WhileLoopTree;
import components.Checker;
import components.EventComponent;
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

    public static String readMultiLineStringFromUser() {
        Scanner scanner = new Scanner(System.in);
        StringBuilder inputBuilder = new StringBuilder();
        System.out.println("press Enter twice to finish:\n");
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            inputBuilder.append(line);
            inputBuilder.append("\n");
        }
        scanner.close();
        return inputBuilder.toString();
    }
    public static int readIntegerFromUser(Scanner scanner) {
        int choice;
        while (true) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                break;
            } else {
                System.out.println("Invalid input! Please enter an integer:");
                scanner.next();
            }
        }
        return choice;
    }
    public static float readPrice(Scanner scanner) {
        float price = 0.0f;

        while (true) {


            if (scanner.hasNextFloat()) {
                price = scanner.nextFloat();
                break;
            } else {
                System.out.println("Invalid input! Please enter a valid price:");
                scanner.next();
            }
        }

        return price;
    }
    public static void printEventDetails(List<Event> events) {
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
    }



    public static void main(String[] args) {
        EventComponent eventComponent = new EventComponent();

        Scanner scanner = new Scanner(System.in);
        String username;
        String password;
        String signuppass;
        String email;
        User user1;
        String userType = "";
        String confirmPassword;
        int y = 0;
        User user2;
        int x = 0;
        String type = "";
        UserComponent userComponent = new UserComponent();
        User loggedInUser = null;
        Boolean signupSurvice = Boolean.FALSE;
        System.out.println("\n\n****   Welcome to the Event Planner System   ****\n");

        do {

            System.out.print("\n\nPress 1 for login, 2 for Signup: ");
            while (true) {
                if (scanner.hasNextInt()) {
                    x = scanner.nextInt();
                    System.out.println("You entered: " + x);
                    break;
                } else {
                    System.out.println("Invalid input! \nPress 1 for login, 2 for Signup: ");
                    scanner.next();
                }
            }

                    if (x == 1) {
                        System.out.println("\n**         Login         **\n");
                        while (true) {
                            System.out.print("Email:");
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
                            // Exit the loop after successful login
                        } else {
                            System.out.println("Invalid credentials. Please try again.\n");
                        }

                    } else if (x == 2) {
                        System.out.println("*         Signup         *\n");

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
                            user2 = new User(email, signuppass, userType, firstName, lastName);

                            loggedInUser = userComponent.validateLogin(email, signuppass);

                            if (userType.equals("SERVICE_PROVIDER")) {
                                System.out.println("Enter the services you need to provide: ");

                                String SeviceMessage = scanner.next();

                                UserRepository.addToReviw(user2);
                                Notification accountRequestNotification = new Notification();
                                accountRequestNotification.createAccountCreationRequest(user2, SeviceMessage);
                                accountRequestNotification.sendCreationRequest();
                                signupSurvice = Boolean.TRUE;
                            }
                            if (userType.equals("USER")) {
                                UserRepository.addToUsers(user2);
                                System.out.println("The account is now complete and you are able to log in :) ");
                            }
                            // Add the user data to the UserRepository

                            // Exit the loop after successful signup
                        } else {
                            System.out.println("Account creation failed.");
                            // Print the error message returned from the validateSignup method

                        }

                    } else {
                        System.out.println("Invalid value\n");
                    }

//////











          ///////////      
                        else if (type.equals("ADMIN")) {
                            System.out.println("\t** Hello in your profile **\n");
                            System.out.println("Name: " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
                            System.out.println("Email: " + loggedInUser.getEmail() + "\n");

                            boolean continueLoop = true;
                            while (continueLoop) {
                                System.out.println("Select a number to view its contents:");
                                System.out.println("1- Edit your profile");
                                System.out.println("2- Statistics and analysis");
                                System.out.println("3- Your notifications");
                                System.out.println("4- Send an announcement");
                                System.out.println("5- Logout");
                                int choice = readIntegerFromUser(scanner);

                                switch (choice) {
                                    case 1: {
                                        //profile
                                        break;
                                    }
                                    case 2: {
                                        //reports
                                        break;
                                    }
                                    case 3: {
                                        Boolean continueLoop1 = true;
                                        while (continueLoop1) {
                                            System.out.println("Your Notifications:");
                                            System.out.println("Select a number to view more details:");
                                            int i = 1;

                                            for (Notification n : loggedInUser.notifications) {
                                                System.out.println(i + "- " + n.getMessage() + " at ( " + n.getSentDateTime() + " )");
                                                i++;
                                            }
                                            System.out.println(i + "- Back to home page");
                                            int choice1 = readIntegerFromUser(scanner);
                                            if (choice1 < i && choice1 >= 1) {

                                                Notification n = loggedInUser.notifications.get(choice1 - 1);
                                                System.out.println(n.showNtificationDetails());
                                                if (n.getType().equals(Notification.NotificationType.ACCOUNTREQUEST)) {
                                                    boolean continueLoop2 = true;
                                                    while (continueLoop2) {
                                                        System.out.println("Select a number:");
                                                        System.out.println("1- accept the request");
                                                        System.out.println("2- reject the request");
                                                        System.out.println("3- Back to notification page");
                                                        int choice2 = readIntegerFromUser(scanner);
                                                        switch (choice2) {
                                                            case 1: {
                                                                n.setApproved(true);
                                                                loggedInUser.notifications.remove(n);
                                                                UserRepository.addToUsers(n.sender);
                                                                System.out.println("A service provider account has been created");
                                                                UserRepository.reviw.remove(n.sender);
                                                                //email sent
                                                                continueLoop2 = false;
                                                                break;
                                                            }
                                                            case 2: {
                                                                n.setApproved(false);
                                                                loggedInUser.notifications.remove(n);

                                                                //email sent
                                                                continueLoop2 = false;
                                                                break;
                                                            }
                                                            case 3: {
                                                                continueLoop2 = false;
                                                                break;
                                                            }
                                                            default: {
                                                                System.out.println("Invalid choice");
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    boolean continueLoop2 = true;
                                                    while (continueLoop2) {
                                                        System.out.println("Enter 1 to back to notification page ");
                                                        int choice2 = readIntegerFromUser(scanner);
                                                        switch (choice2) {
                                                            case 1: {
                                                                continueLoop2 = false;
                                                                break;
                                                            }
                                                            default: {
                                                                System.out.println("Invalid choice");
                                                            }
                                                        }
                                                    }
                                                }

                                            } else if (choice1 == i) {
                                                continueLoop1 = false;
                                            } else {
                                                System.out.println("Invalid choice");
                                            }


                                        }

                                        break;
                                    }
                                    case 4: {
                                        Boolean continueLoop1 = true;
                                        while (continueLoop1) {
                                            System.out.println("Select a number :");
                                            System.out.println("1- Enter a new message to sent");
                                            System.out.println("2- Back to home page");
                                            int choice1 = readIntegerFromUser(scanner);

                                            switch (choice1) {
                                                case 1: {
                                                    System.out.println("*   Now..you can send an announcement !   *\n");
                                                    System.out.println("Enter the message you want to send to users:");
                                                    String message = scanner.next();
                                                    Notification notification = new Notification();
                                                    notification.createAnnouncement(loggedInUser, message);
                                                    notification.sendAdminAnnouncement();

                                                    break;
                                                }
                                                case 2: {
                                                    continueLoop1 = false;

                                                    break;
                                                }
                                                default:
                                                    System.out.println("Invalid choice");
                                            }
                                        }

                                        break;
                                    }
                                    case 5:

                                        continueLoop = false;

                                        break;
                                    default:
                                        System.out.println("Invalid choice");
                                }
                            }

                        }
                        else {
                            System.out.println("Logged in as a regular user.");
                            System.out.println("\t** Hello in your profile **\n");
                            System.out.println("Name: " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
                            System.out.println("Email: " + loggedInUser.getEmail() + "\n");

                            boolean continueLoop3 = true;
                            while (continueLoop3) {
                                System.out.println("What would you like to do?");
                                System.out.println("1. View available events");
                                System.out.println("2. Search");//aseeel.
                                System.out.println("3. Book an event");
                                System.out.println("4. View booked events");
                                System.out.println("5. Cancel booked events");
                                System.out.println("6. Edit profile"); // look shifaa
                                System.out.println("7. Notifications");
                                System.out.println("8. Logout");

                                int choice = readIntegerFromUser(scanner);
                                switch (choice) {
                                    case 1: {
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
                                    }
                                    case 2: {
                                        String eventName;
                                        String eventLocation;
                                        float minPrice;
                                        float maxPrice;
                                        List<Event> resultEvents ;
                                        boolean continueLoop = true;
                                        while (continueLoop) {
                                            System.out.println("1. Search by name");
                                            System.out.println("2. Search by name and location");//aseeel.
                                            System.out.println("3. Search by name and price");
                                            System.out.println("4. Search by name, place and price");
                                            System.out.println("5. Show all events");
                                            System.out.println("6. Back to home page");
                                            System.out.println("Select an option to search:");
                                            int choice1 = readIntegerFromUser(scanner);
                                            switch (choice1) {
                                                case 1: {
                                                    System.out.println("1. Enter name of event: ");
                                                    eventName = scanner.next();
                                                    resultEvents = Checker.checkNameOfEvent(eventName);
                                                    System.out.println("------------------------");
                                                    if (!resultEvents.equals(null)) {
                                                        printEventDetails(resultEvents);
                                                    } else {
                                                        System.out.println("No result :(");
                                                    }
                                                    boolean continueLoop2 = true;
                                                    while (continueLoop2) {
                                                        System.out.println("Enter 1 to back to search page ");
                                                        int choice2 = readIntegerFromUser(scanner);
                                                        switch (choice2) {
                                                            case 1: {
                                                                continueLoop2 = false;
                                                                break;
                                                            }
                                                            default: {
                                                                System.out.println("Invalid choice");
                                                            }
                                                        }
                                                    }


                                                }
                                                case 2: {
                                                    System.out.println("1. Enter name of event: ");
                                                    eventName = scanner.next();
                                                    System.out.println("1. Enter location of event: ");
                                                    eventLocation = scanner.next();
                                                    resultEvents = Checker.checkNameAndLocationOfEvent(eventName, eventLocation);
                                                    System.out.println("------------------------");
                                                    if (!resultEvents.equals(null)) {
                                                        printEventDetails(resultEvents);
                                                    } else {
                                                        System.out.println("No result :(");
                                                    }
                                                    boolean continueLoop2 = true;
                                                    while (continueLoop2) {
                                                        System.out.println("Enter 1 to back to search page ");
                                                        int choice2 = readIntegerFromUser(scanner);
                                                        switch (choice2) {
                                                            case 1: {
                                                                continueLoop2 = false;
                                                                break;
                                                            }
                                                            default: {
                                                                System.out.println("Invalid choice");
                                                            }
                                                        }
                                                    }

                                                }
                                                case 3: {
                                                    System.out.println("1. Enter name of event: ");
                                                    eventName = scanner.next();
                                                    System.out.println("1. Enter minimum price of event: ");
                                                    minPrice = readPrice(scanner);
                                                    System.out.println("1. Enter Maximum price of event: ");
                                                    maxPrice = readPrice(scanner);
                                                    resultEvents = Checker.checkNameAndPriceOfEvent(eventName, minPrice, maxPrice);
                                                    System.out.println("------------------------");
                                                    if (!resultEvents.equals(null)) {
                                                        printEventDetails(resultEvents);
                                                    } else {
                                                        System.out.println("No result :(");
                                                    }
                                                    boolean continueLoop2 = true;
                                                    while (continueLoop2) {
                                                        System.out.println("Enter 1 to back to search page ");
                                                        int choice2 = readIntegerFromUser(scanner);
                                                        switch (choice2) {
                                                            case 1: {
                                                                continueLoop2 = false;
                                                                break;
                                                            }
                                                            default: {
                                                                System.out.println("Invalid choice");
                                                            }
                                                        }
                                                    }

                                                }
                                                case 4: {
                                                    System.out.println("1. Enter name of event: ");
                                                    eventName = scanner.next();
                                                    System.out.println("1. Enter location of event: ");
                                                    eventLocation = scanner.next();
                                                    System.out.println("1. Enter minimum price of event: ");
                                                    minPrice = readPrice(scanner);
                                                    System.out.println("1. Enter Maximum price of event: ");
                                                    maxPrice = readPrice(scanner);
                                                    resultEvents = Checker.checkNameLocationAndPriceOfEvent(eventName, eventLocation, minPrice, maxPrice);
                                                    System.out.println("------------------------");
                                                    if (!resultEvents.equals(null)) {
                                                        printEventDetails(resultEvents);
                                                    } else {
                                                        System.out.println("No result :(");
                                                    }
                                                    boolean continueLoop2 = true;
                                                    while (continueLoop2) {
                                                        System.out.println("Enter 1 to back to search page ");
                                                        int choice2 = readIntegerFromUser(scanner);
                                                        switch (choice2) {
                                                            case 1: {
                                                                continueLoop2 = false;
                                                                break;
                                                            }
                                                            default: {
                                                                System.out.println("Invalid choice");
                                                            }
                                                        }
                                                    }

                                                }
                                                case 5: {
                                                    resultEvents = EventRepository.events;
                                                    if (!resultEvents.equals(null)) {
                                                        printEventDetails(resultEvents);
                                                    } else {
                                                        System.out.println("No result :(");
                                                    }
                                                    boolean continueLoop2 = true;
                                                    while (continueLoop2) {
                                                        System.out.println("Enter 1 to back to search page ");
                                                        int choice2 = readIntegerFromUser(scanner);
                                                        switch (choice2) {
                                                            case 1: {
                                                                continueLoop2 = false;
                                                                break;
                                                            }
                                                            default: {
                                                                System.out.println("Invalid choice");
                                                            }
                                                        }
                                                    }

                                                }
                                                case 6: {
                                                    continueLoop = false;
                                                    break;
                                                }
                                                default:{
                                                    System.out.println("Invalid choice");
                                                }
                                            }
                                        }


                                        break;
                                    }

                                    case 3:
                      /*      System.out.println("Available Events:");
                            for (Event event : EventRepository.events) {
                                System.out.println("Event ID: " + event.getIdOfEvent());
                                System.out.println("Event Name: " + event.getNameOfEvent());
                                System.out.println("Location: " + event.getPlaceOfEvent().getLocationOfPlace());
                                System.out.println("Start Time: " + event.getEventStartTime());
                                System.out.println("End Time: " + event.getEventEndTime());
                                System.out.println("Cost: " + event.getCostOfEvent());
                                System.out.println("Status: " + event.getstatusOfEvent());
                                System.out.println("------------------------");
                            }*/
                                    {
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
                                        boolean bookingSuccessful = BookingSystem.bookEvent(eventId, location, bookingDate, userBalance,loggedInUser);
                                        if (bookingSuccessful) {
                                            // userBalance -= event.costOfEvent;
                                            //payment process
                                            Event event = BookingSystem.findEventByIdAndLocation(eventId,location);




                                            ////////////////////////////////////////////

                                        } else {
                                            System.out.println("Booking failed. Please try again.");
                                        }
                                        break;
                                    }
                                    case 4: {
                                        String userEmail = loggedInUser.getEmail();
                                        List<Event> bookedEvents = loggedInUser.getBookedEventsForUser(userEmail);
                                        ;
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
                                    }
                                    case 5: {
                                        System.out.println("\nCancel booked events:");
                                        boolean foundBookedEvents2 = false;
                                        for (Event event : loggedInUser.bookedEvent2) {
                                            if (event.getBookedUser() != null && event.getBookedUser().getEmail().equals(loggedInUser.getEmail())) {
                                                // Display the booked event details
                                                System.out.println("Event ID: " + event.getIdOfEvent());
                                                System.out.println("Event Name: " + event.getNameOfEvent());
                                                System.out.println("Event Location: " + event.getPlaceOfEvent().getLocationOfPlace());
                                                System.out.println("Event Date: " + event.getEventStartTime());
                                                System.out.println();
                                                String IDC;
                                                while (true) {
                                                    System.out.print("Enter the ID of event you want to cancle it :");
                                                    IDC = scanner.next();
                                                    boolean flagID = eventComponent.checkIdOfEvent(IDC);
                                                    if (flagID == true) {
                                                        break;
                                                    } else {
                                                        System.out.println(" please try again....!");
                                                    }
                                                }
                                                System.out.print("Do you want to cancel this event? (yes/no): ");
                                                String cancelChoice = scanner.next();
                                                if (cancelChoice.equalsIgnoreCase("yes")) {
                                                    // Remove the booking
                                                    for (Event event2 : loggedInUser.bookedEvent2) {
                                                        if (event2.getIdOfEvent().equals(IDC)) {
                                                            loggedInUser.bookedEvent2.remove(event2);
                                                            event2.setEventStatus("unbook"); // Update event status
                                                        }
                                                    }
                                                    System.out.println("Event has been cancelled successfully.");
                                                }
                                                foundBookedEvents2 = true; // flag to true as booked events
                                            }
                                        }
                                        if (!foundBookedEvents2) {
                                            System.out.println("No booked events found.");
                                        }
                                        break;
                                    }

                                    case 6: {
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
                                    }

                                    case 7: {
                                        Boolean continueLoop1 = true;
                                        while (continueLoop1) {
                                            System.out.println("Your Notifications:");
                                            System.out.println("Select a number to view more details:");
                                            int i = 1;

                                            for (Notification n : loggedInUser.notifications) {
                                                System.out.println(i + "- " + n.getMessage() + " at ( " + n.getSentDateTime() + " )");
                                                i++;
                                            }
                                            System.out.println(i + "- Back to home page");
                                            int choice1 = readIntegerFromUser(scanner);
                                            if (choice1 < i && choice1 >= 1) {

                                                Notification n = loggedInUser.notifications.get(choice1 - 1);
                                                System.out.println(n.showNtificationDetails());

                                                boolean continueLoop2 = true;
                                                while (continueLoop2) {
                                                    System.out.println("Enter 1 to back to notification page ");
                                                    int choice2 = readIntegerFromUser(scanner);
                                                    switch (choice2) {
                                                        case 1: {
                                                            continueLoop2 = false;
                                                            break;
                                                        }
                                                        default: {
                                                            System.out.println("Invalid choice");
                                                        }
                                                    }
                                                }
                                            } else if (choice1 == i) {
                                                continueLoop1 = false;
                                            } else {
                                                System.out.println("Invalid choice");
                                            }
                                        }
                                        break;
                                    }

                                    case 8: {
                                        continueLoop3 = false;
                                        break;
                                    }
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                }
                            }
                        }
                    }


                }

                signupSurvice = false;


        } while (true) ; // Repeat until a valid input is provided




    }

}
