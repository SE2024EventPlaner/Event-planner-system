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
        User user2;
        int x = 0;
        String type = "";
        UserComponent userComponent = new UserComponent();
        User loggedInUser = null;
        System.out.println("\n\n****   Welcome to the Event Planner System   ****\n");

        do {
            System.out.print("Press 1 for login, 2 for Signup: ");
            x = scanner.nextInt();

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
                    if (userComponent.existPassword(username,password)) {
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
                    user2=new User(email, signuppass, userType, firstName, lastName);
                    UserRepository.addToUsers(user2);
                    loggedInUser=userComponent.validateLogin(email,signuppass);
                    if (userType.equals("SERVICE_PROVIDER")) {
                        System.out.println("Enter the services you need to provide: ");
                        String message = scanner.next();
                        user2.setMessage(message);
                        UserRepository.addToReviw(user2);
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
                System.out.println("\t** Hello in your profile **\n");
                System.out.println("Name: " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
                System.out.println("Email: " + loggedInUser.getEmail() + "\n");

                boolean continueLoop = true;
                while (continueLoop) {
                    System.out.println("Select a number to view its contents:");
                    System.out.println("1- Edit your profile");
                    System.out.println("2- Names of your events and their total number");
                    System.out.println("3- Names of your booked events and their total number");
                    System.out.println("4- Names of your unbooked events and their total number");
                    System.out.println("5- Event management");
                    System.out.println("6- Your notifications");
                    System.out.println("7- Logout");
                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 1: {

                            break;
                        }
                        case 2: {
                            System.out.println("\t\tYOUR EVENTS :  \n");
                            for (Event event : EventRepository.events) {
                                if (event.eventOwner.getEmail().equals(loggedInUser.getEmail()))
                                    System.out.println("Name Of Event :" + event.getNameOfEvent() + "\tID Of Event :" + event.getIdOfEvent() + "\n");
                            }
                            System.out.println("The total number of your events = " + EventRepository.events.size() + "\n");
                            break;
                        }
                        case 3: {
                            int numberOfBookedEvent = 0;
                            System.out.println("\t\tYOUR BOOKED EVENTS :");
                            for (Event event : EventRepository.events) {
                                if (event.getstatusOfEvent().equalsIgnoreCase("booked")) {
                                    System.out.println("Name Of Event :" + event.getNameOfEvent() + "\tID Of Event:" + event.getIdOfEvent());
                                    numberOfBookedEvent++;
                                }
                            }
                            System.out.println("\nThe total number of your booked events = " + numberOfBookedEvent + "\n");
                            break;
                        }
                        case 4: {
                            int numberOfUnbookedEvent = 0;
                            System.out.println("\t\tYOUR UnBOOKED EVENTS :");
                            for (Event event : EventRepository.events) {
                                if (event.getstatusOfEvent().equalsIgnoreCase("unbooked")) {
                                    System.out.println("Name Of Event :" + event.getNameOfEvent() + "\tID Of Event:" + event.getIdOfEvent());
                                    numberOfUnbookedEvent++;
                                }
                            }
                            System.out.println("\nThe total number of your Unbooked events = " + numberOfUnbookedEvent + "\n");
                            break;
                        }
                        case 5: {
                            System.out.println("Select one:");
                            System.out.println("1- Add new event");
                            System.out.println("2- Edit an event");
                            System.out.println("3- Delete an event");
                            int eventChoice = scanner.nextInt();
                            switch (eventChoice) {
                                case 1: {
                                    String name, ID;
                                    float cost;
                                    int capacity;
                                    System.out.println("\t*   Now..you can add new event !   *\n");
                                    while (true) {
                                        System.out.println("Enter the name of event :");
                                        name = scanner.next();
                                        if (name == null || name.length() <= 1)
                                            System.out.println("The name you entered is invalid, please try again....!");
                                        else
                                            break;
                                    }


                                    while (true) {
                                        System.out.println("Enter the ID of event :");
                                        ID = scanner.next();
                                        if (!Event.checkIdOfEvent(ID))
                                            System.out.println(" please try again....!");
                                        else
                                            break;
                                    }

                                    while (true) {
                                        System.out.println("Enter the Cost of event :");
                                        cost = scanner.nextFloat();
                                        if (!Event.checkCostOfEvent(cost))
                                            System.out.println(" please try again....!");
                                        else
                                            break;
                                    }


                                    System.out.println("Enter the event start time \n NOTE: use this format:yyyy-mm-ddThh:mm:ss.908732\n Enter:");
                                    String date1 = scanner.next();
                                    LocalDateTime startDate = Event.dateConverter(date1);


                                    System.out.println("Enter the event end time \n NOTE: use this format:yyyy-mm-ddThh:mm:ss.908732\n Enter:");
                                    String date2 = scanner.next();
                                    LocalDateTime endDate = Event.dateConverter(date2);


                                    System.out.println("Enter the location -city- of event : ");
                                    String city = scanner.next();

                                    System.out.println("Enter the place-hall/hotel- of event :");
                                    String place = scanner.next();


                                    while (true) {
                                        System.out.println("Enter the capacity of Place :");
                                        capacity = scanner.nextInt();
                                        if (!Place.checkCapacityOfPlace(capacity))
                                            System.out.println(" please try again....!");
                                        else
                                            break;
                                    }

                                    Event.addEvent(name, ID, cost, startDate, endDate, city, capacity, place, loggedInUser.getEmail(), loggedInUser.getPassword());

                                    break;
                                }
                                case 2: {
                                    while (true) {

                                        System.out.println("Enter the new name of event :");
                                        String name1 = scanner.next();
                                        System.out.println("Enter the new ID of event :");
                                        String ID1 = scanner.next();
                                        if (Event.theEventExists(name1, ID1) == null)
                                            System.out.println("The information you entered does not match any existing event\n  Pleas try again ...! ");
                                        else
                                            break;
                                    }

                                    String name2, ID2;
                                    float cost2;
                                    int capacity2;

                                    while (true) {
                                        System.out.println("Enter the name of event :");
                                        name2 = scanner.next();
                                        if (name2 == null || name2.length() <= 1)
                                            System.out.println("The name you entered is invalid, please try again....!");
                                        else
                                            break;
                                    }

                                    while (true) {
                                        System.out.println("Enter the ID of event :");
                                        ID2 = scanner.next();
                                        if (!Event.checkIdOfEvent(ID2))
                                            System.out.println(" please try again....!");
                                        else
                                            break;
                                    }

                                    while (true) {
                                        System.out.println("Enter the Cost of event :");
                                        cost2 = scanner.nextFloat();
                                        if (!Event.checkCostOfEvent(cost2))
                                            System.out.println(" please try again....!");
                                        else
                                            break;
                                    }


                                    System.out.println("Enter the event start time \n NOTE: use this format:yyyy-mm-ddThh:mm:ss.908732\n Enter:");
                                    String date3 = scanner.next();
                                    LocalDateTime startDate2 = Event.dateConverter(date3);


                                    System.out.println("Enter the event end time \n NOTE: use this format:yyyy-mm-ddThh:mm:ss.908732\n Enter:");
                                    String date4 = scanner.next();
                                    LocalDateTime endDate2 = Event.dateConverter(date4);


                                    System.out.println("Enter the location -city- of event : ");
                                    String city2 = scanner.next();

                                    System.out.println("Enter the place-hall/hotel- of event :");
                                    String place2 = scanner.next();


                                    while (true) {
                                        System.out.println("Enter the capacity of Place :");
                                        capacity2 = scanner.nextInt();
                                        if (!Place.checkCapacityOfPlace(capacity2))
                                            System.out.println(" please try again....!");
                                        else
                                            break;
                                    }

                                    Event.addEvent(name2, ID2, cost2, startDate2, endDate2, city2, capacity2, place2, loggedInUser.getEmail(), loggedInUser.getPassword());
                                    break;
                                }
                                case 3: {
                                    System.out.println("*   Now..you can delete an event !   *\n");
                                    System.out.println("Enter the name of the event you want to delete :");
                                    String nameOfEvent = scanner.next();
                                    System.out.println("Enter the ID of the event you want to delete");
                                    String idOfEvent = scanner.next();
                                    Event.deleteEvent(nameOfEvent, idOfEvent);
                                    break;

                                }
                            }
                        }
                        case 6: {
                            System.out.println("Your Notifications:");
                            // notification
                            break;
                        }
                        case 7: {
                            continueLoop = false;

                            break;
                        }
                        default: {
                            System.out.println("Invalid choice");
                        }
                    }
                }
            }
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
                    int choice = scanner.nextInt();

                    switch(choice) {
                        case 1: {
                            //profile
                            break;
                        }
                        case 2: {
                            //reports
                            break;
                        }
                        case 3: {
                            Boolean continueLoop1 =true;
                            while (continueLoop1) {
                                System.out.println("Your Notifications:");
                                System.out.println("Select a number to view more details:");
                                int i = 1;

                                for (Notification n : loggedInUser.notifications) {
                                    System.out.println(i + "- " + n.getMessage() + " at ( " + n.getSentDateTime() + " )");
                                    i++;
                                }
                                System.out.println(i+"- Back to home page");
                                int choice1 = scanner.nextInt();
                                if(choice1<i && choice1>1){

                                    Notification n = loggedInUser.notifications.get(choice1-1);
                                    System.out.println(n.showNtificationDetails());
                                    if(n.getType().equals(Notification.NotificationType.ACCOUNTREQUEST)){
                                        boolean continueLoop2 = true;
                                        while (continueLoop2) {
                                            System.out.println("Select a number:");
                                            System.out.println("1- accept the request");
                                            System.out.println("2- reject the request");
                                            System.out.println("3- Back to notification page");
                                            int choice2 = scanner.nextInt();
                                            switch (choice2) {
                                                case 1: {
                                                    n.setApproved(true);
                                                    n.sender.notifications.remove(n);
                                                    //email sent
                                                    //add user from revie to user reposetory
                                                    continueLoop2 = false;
                                                    break;
                                                }
                                                case 2: {
                                                    n.setApproved(false);
                                                    n.sender.notifications.remove(n);
                                                    //email sent
                                                    continueLoop2 = false;
                                                    break;
                                                }
                                                case 3: {
                                                    continueLoop2 = false;
                                                    break;
                                                }
                                                default:{
                                                    System.out.println("Invalid choice");
                                                }
                                            }
                                        }
                                    }

                                    else{
                                        boolean continueLoop2 = true;
                                        while (continueLoop2) {
                                            System.out.println("Enter 1 to back to notification page ");
                                            int choice2 = scanner.nextInt();
                                            switch (choice2) {
                                                case 1:{
                                                    continueLoop2 = false;
                                                    break;
                                                }
                                                default:{
                                                    System.out.println("Invalid choice");
                                                }
                                            }
                                        }
                                    }

                                }
                                else if(choice1==1){
                                    continueLoop1 = false;
                                }
                                else{
                                    System.out.println("Invalid choice");
                                }


                            }

                            break;
                        }
                        case 4:{
                            Boolean continueLoop1 =true;
                            while (continueLoop1) {
                                System.out.println("Select a number :");
                                System.out.println("1- Enter a new message to sent");
                                System.out.println("2- Back to home page");
                                int choice1 = scanner.nextInt();
                                switch (choice1) {
                                    case 1: {
                                        System.out.println("*   Now..you can send an announcement !   *\n");
                                        System.out.println("Enter the message you want to send to users:");
                                        String message = scanner.next();
                                        Notification notification = new Notification();
                                        notification.createAnnouncement(loggedInUser,message);
                                        notification.sendAdminAnnouncement(loggedInUser);

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
                        default: System.out.println("Invalid choice");
                    }
                }

            }





            else {
                System.out.println("Logged in as a regular user.");
                while (true) {
                    System.out.println("What would you like to do?");
                    System.out.println("1. View available events");
                    System.out.println("2. Search");//aseeel
                    System.out.println("3. Book an event");
                    System.out.println("4. View booked events");
                    System.out.println("5. Cancel booked events");
                    System.out.println("6. Edit profile"); // look shifaa
                    System.out.println("7. Notifications");
                    System.out.println("8. Logout");
                    int choice = scanner.nextInt();
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
//search
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
                                {  System.out.print("Enter your balance: ");
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
                                System.out.println("Booking successful!");
                                // userBalance -= event.costOfEvent;
                                //payment process
                                } else {
                                System.out.println("Booking failed. Please try again.");
                            }
                            break;
                        }
                        case 4: {
                            String userEmail = loggedInUser.getEmail();
                            List<Event> bookedEvents = User.getBookedEventsForUser(userEmail);
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
                            for (Event event : User.bookedEvent2) {
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
                                        boolean flagID = Event.checkIdOfEvent(IDC);
                                        if (flagID == true) {
                                        break;
                                        }
                                        else {
                                        System.out.println(" please try again....!");
                                        }
                                    }
                                    System.out.print("Do you want to cancel this event? (yes/no): ");
                                    String cancelChoice = scanner.next();
                                    if (cancelChoice.equalsIgnoreCase("yes")) {
                                        // Remove the booking
                                        for (Event event2 : User.bookedEvent2) {
                                            if (event2.getIdOfEvent().equals(IDC)) {
                                                User.bookedEvent2.remove(event2);
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
                                int choice1 = scanner.nextInt();
                                if (choice1 < i && choice1 > 1) {

                                    Notification n = loggedInUser.notifications.get(choice1 - 1);
                                    System.out.println(n.showNtificationDetails());

                                    boolean continueLoop2 = true;
                                    while (continueLoop2) {
                                        System.out.println("Enter 1 to back to notification page ");
                                        int choice2 = scanner.nextInt();
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
                        case 8:{
                            System.out.println("Logging out...");}
                        return;
                        default:{
                            System.out.println("Invalid choice. Please try again.");
                }
                    }
                }
            }
        }
    }
}

