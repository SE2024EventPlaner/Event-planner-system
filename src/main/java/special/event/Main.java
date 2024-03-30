package special.event;

import components.*;
import repositories.EventRepository;
import repositories.UserRepository;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
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
        float price ;

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
        String userType ;
        String confirmPassword;
        int y ;
        User user2;
        int x ;
        String type ;
        UserRepository o=new UserRepository();
        EventRepository kk=new EventRepository();
        o.readUsers(UserRepository.fileOfUser);
        kk.readEventFile(EventRepository.fileOfEvent);


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
                System.out.println("**       Signup      **\n");

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
                    if (userType.equals("USER")) {

                        UserRepository.addToUsers(user2);
                        System.out.println("The account is now complete and you are able to log in :) ");
                    }
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


                    // Exit the loop after successful signup
                } else {
                    System.out.println("Account creation failed.");
                    // Print the error message returned from the validateSignup method

                }

            } else {
                System.out.println("Invalid value\n");

            }

//////

            if (signupSurvice.equals(Boolean.FALSE)) {
                type = loggedInUser.getType();
                if (userComponent.validateUserType(loggedInUser, type)) {
                    if (type.equals("SERVICE_PROVIDER")) {
                        System.out.println("\t** Hello in your profile **\n");
                        System.out.println("Name: " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
                        System.out.println("Email: " + loggedInUser.getEmail() + "\n");
                        boolean continueLoop = true;
                        while (continueLoop) {
                            System.out.println("Select a number to view its contents:");
                            System.out.println("1- Edit your profile:");
                            System.out.println("2- Analyze the event that you have:");
                            System.out.println("3- Calendar:");
                            System.out.println("4- Event management:");
                            System.out.println("5- Your notifications:");
                            System.out.println("6- Logout");
                            int choice = readIntegerFromUser(scanner);

                            switch (choice) {
                                case 1: {//////

                                    System.out.println("\t**\tNow you can Edit Your Profile\t**\t");
                                    System.out.println("Choose one of the fields to modify:");
                                    System.out.println("1-your First Name : ");
                                    System.out.println("2-Your Last Nme :");
                                    System.out.println("3-Your Email");
                                    System.out.println("4-Your Password");
                                    int input5= scanner.nextInt();
                                    switch (input5) {
                                        case 1: //edit first Name
                                        {  System.out.println("Your Current First Name: " + loggedInUser.getFirstName());
                                            String newFirstName;
                                            while (true){
                                                System.out.print("Enter New First Name: ");
                                                newFirstName = scanner.next().trim();
                                                if (newFirstName.isEmpty()||newFirstName.length()==1) {
                                                    System.out.println("Invalid input! Please try again.");
                                                }
                                                else
                                                    loggedInUser.setFirstName(newFirstName);
                                                break;
                                            }
                                            System.out.println("Updated successfully");
                                            System.out.println(loggedInUser.getFirstName());


                                            break;}
                                        case 2://edit last name
                                        {
                                            System.out.println("Your Current Last Name: " + loggedInUser.getLastName());
                                            while (true) {
                                                System.out.print("Enter New Last Name: ");
                                                String newLastName = scanner.next().trim();
                                                if (newLastName.isEmpty() || newLastName.length() == 1) {
                                                    System.out.println(" Invalid input! Please try again.");
                                                } else
                                                    loggedInUser.setFirstName(newLastName);
                                                System.out.println("Updated successfully");
                                                break;
                                            }
                                        }
                                        case 3://edit Email
                                        { System.out.println("Your Current Email : " + loggedInUser.getEmail());
                                            boolean existEmail2=false;
                                            while (true) {
                                                System.out.print("Enter New email: ");
                                                String newEmail = scanner.next().trim();

                                                if (!userComponent.isValidEmail(newEmail)) {
                                                    System.out.println("The email you entered is invalid. Please try again.");
                                                    continue;
                                                }

                                                for (User user : UserRepository.users) {
                                                    if (user.getEmail().equals(newEmail)) {
                                                        existEmail2 = true;
                                                        break;
                                                    }
                                                }
                                                if (existEmail2) {
                                                    System.out.println("The email you entered is already exist. Please enter another one.");
                                                    existEmail2 = false;
                                                } else {

                                                    loggedInUser.setEmail(newEmail);
                                                    System.out.println("Updated successfully");
                                                    break;
                                                }
                                            }
                                            break;}
                                        case 4:{ //edit Password
                                            System.out.print("Your Current Password: "+loggedInUser.getPassword());
                                            String newPassword ;
                                            while (true) {
                                                System.out.print("Enter new password: ");
                                                newPassword = scanner.next().trim();
                                                if (userComponent.isValidPassword(newPassword)) {
                                                    break;
                                                } else {
                                                    System.out.println("The password should contain at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one of the following symbols: !@#$%^");
                                                }
                                            }

                                            while (true) {
                                                System.out.print("Enter Confirm new password:");
                                                String confirmNewPassword = scanner.next();
                                                if (confirmNewPassword.equals(newPassword)) {
                                                    loggedInUser.setPassword(newPassword);
                                                    System.out.println("Updated successfully");
                                                    break;
                                                } else {
                                                    System.out.println("The confirmed password does not match the original password.");
                                                }
                                            }
                                            break;}


                                    }
                                    break;
                                }
                                case 2: {
                                    System.out.println("\t\tYOUR EVENTS :  \n");
                                    for (Event event : EventRepository.events) {
                                        if (event.eventOwner.getEmail().equals(loggedInUser.getEmail()))
                                            System.out.println("Name Of Event :" + event.getNameOfEvent() + "\tID Of Event :" + event.getIdOfEvent() + "\n");
                                    }
                                    System.out.println("The total number of your events = " + EventRepository.events.size() + "\n");
                                    int numberOfBookedEvent = 0;
                                    System.out.println("\n\t\tYOUR BOOKED EVENTS :");
                                    for (Event event : EventRepository.events) {
                                        if (event.getstatusOfEvent().equalsIgnoreCase("booked")) {
                                            System.out.println("Name Of Event :" + event.getNameOfEvent() + "\tID Of Event:" + event.getIdOfEvent());
                                            numberOfBookedEvent++;
                                        }
                                    }
                                    System.out.println("\nThe total number of your booked events = " + numberOfBookedEvent + "\n");
                                    int numberOfUnbookedEvent = 0;
                                    System.out.println("\n\t\tYOUR UnBOOKED EVENTS :");
                                    for (Event event : EventRepository.events) {
                                        if (event.getstatusOfEvent().equalsIgnoreCase("unbooked")) {
                                            System.out.println("Name Of Event :" + event.getNameOfEvent() + "\tID Of Event:" + event.getIdOfEvent());
                                            numberOfUnbookedEvent++;
                                        }
                                    }
                                    //الربح من خلال سعر الايفنت وسعر التكلفة
                                    System.out.println("\nThe total number of your Unbooked events = " + numberOfUnbookedEvent + "\n");

                                    /////
                                    break;
                                }
                                case 3: {

                                  //طباعة الايفت القادمة فقط
                                }

                                case 4: {
                                    System.out.println("Select one:");
                                    System.out.println("1- Add new event");
                                    System.out.println("2- Edit an event");
                                    System.out.println("3- Add an image for an event");
                                    System.out.println("4- Delete an event");
                                    int eventChoice = scanner.nextInt();
                                    switch (eventChoice) {
                                        case 1: {
                                            // Add a new event
                                            // Prompt the user to input event details
                                            String name, ID;
                                            float cost;
                                            String date2;
                                            LocalDateTime endDate;
                                            String capacity;
                                            System.out.println("\t*   Now..you can add a new event!   *\n");

                                            // Validate and input event name
                                            while (true) {
                                                System.out.println("Enter the name of the event:");
                                                name = scanner.next();
                                                if (name == null || name.length() <= 1)
                                                    System.out.println("The name you entered is invalid, please try again....!");
                                                else
                                                    break;
                                            }

                                            // Validate and input event ID
                                            while (true) {
                                                System.out.println("Enter the ID of the event:");
                                                ID = scanner.next();
                                                if (!eventComponent.checkIdOfEvent(ID))
                                                    System.out.println(" please try again....!");
                                                else
                                                    break;
                                            }

                                            // Validate and input event cost
                                            while (true) {
                                                System.out.println("Enter the cost of the event:");
                                                cost = scanner.nextFloat();
                                                if (!eventComponent.checkCostOfEvent(cost))
                                                    System.out.println(" please try again....!");
                                                else
                                                    break;
                                            }
                                            while (true) {

                                                System.out.println("Enter the capacity of the place:");
                                                capacity = scanner.next();
                                                if (!Place.checkCapacityOfPlace(Integer.parseInt(capacity))) {
                                                    System.out.println("Please enter a valid capacity.");
                                                } else {
                                                    break;
                                                }

                                                scanner.next();
                                            }

                                            // Input event start time
                                            System.out.println("Enter the event start time (use format yyyy-mm-ddThh:mm:ss.908732):");
                                            String date1 = scanner.next();
                                            LocalDateTime startDate = eventComponent.dateConverter(date1);

                                            // Input event end time
                                            while (true) {
                                                System.out.println("Enter the event end time (use format yyyy-mm-ddThh:mm:ss.908732):");
                                                date2 = scanner.next();

                                                 endDate = eventComponent.dateConverter(date2);
                                                if (startDate.isEqual(endDate)) {
                                                    System.out.println("The start date cannot be the same as the end date. Please enter a valid date.\n");
                                                } else {
                                                    break;
                                                }
                                            }

                                            // Input event location and capacity
                                            System.out.println("Enter the location (city) of the event:");
                                            String city = scanner.next();

                                            System.out.println("Enter the place (hall/hotel) of the event:");
                                            String place = scanner.next();

                                            // Validate and input event capacity
                                            System.out.println("Enter the construcionCost of the event:");
                                             float cost1 = scanner.nextFloat();

                                            // Check if the location is already booked at the same time
                                            if (eventComponent.checkSimilarityEvent(place, startDate, endDate, city)) {
                                                System.out.println("Sorry, this location is already booked at the same time.");
                                                    // Exit the switch case
                                            } else {
                                                eventComponent.addEvent(name, ID, cost, cost1,startDate, endDate, city, Integer.parseInt(capacity), place, loggedInUser.getEmail(), loggedInUser.getPassword());
                                            }
                                            // Add the event

                                            break; // Exit the switch case
                                        }
                                        case 2: {
                                            // Add a new event
                                            // Prompt the user to input event details
                                            String name, ID;
                                            float cost;
                                            String date2;
                                            LocalDateTime endDate;
                                            String capacity;
                                            System.out.println("\t*   Now..you can Edit your event!   *\n");

                                            // Validate and input event name
                                            while (true) {
                                                System.out.println("Enter the new name of the event:");
                                                name = scanner.next();
                                                if (name == null || name.length() <= 1)
                                                    System.out.println("The name you entered is invalid, please try again....!");
                                                else
                                                    break;
                                            }

                                            // Validate and input event ID
                                            while (true) {
                                                System.out.println("Enter the new ID of the event:");
                                                ID = scanner.next();
                                                if (!eventComponent.checkIdOfEvent(ID))
                                                    System.out.println(" please try again....!");
                                                else
                                                    break;
                                            }

                                            // Validate and input event cost
                                            while (true) {
                                                System.out.println("Enter the new cost of the event:");
                                                cost = scanner.nextFloat();
                                                if (!eventComponent.checkCostOfEvent(cost))
                                                    System.out.println(" please try again....!");
                                                else
                                                    break;
                                            }
                                            while (true) {

                                                System.out.println("Enter the new capacity of the place:");
                                                capacity = scanner.next();
                                                if (!Place.checkCapacityOfPlace(Integer.parseInt(capacity))) {
                                                    System.out.println("Please enter a valid capacity.");
                                                } else {
                                                    break;
                                                }

                                                scanner.next();
                                            }

                                            // Input event start time
                                            System.out.println("Enter the event new start time (use format yyyy-mm-ddThh:mm:ss.908732):");
                                            String date1 = scanner.next();
                                            LocalDateTime startDate = eventComponent.dateConverter(date1);

                                            // Input event end time
                                            while (true) {
                                                System.out.println("Enter the event new end time (use format yyyy-mm-ddThh:mm:ss.908732):");
                                                date2 = scanner.next();

                                                endDate = eventComponent.dateConverter(date2);
                                                if (startDate.isEqual(endDate)) {
                                                    System.out.println("The start date cannot be the same as the end date. Please enter a valid date.\n");
                                                } else {
                                                    break;
                                                }
                                            }

                                            // Input event location and capacity
                                            System.out.println("Enter the new location (city) of the event:");
                                            String city = scanner.next();

                                            System.out.println("Enter the new place (hall/hotel) of the event:");
                                            String place = scanner.next();

                                            // Validate and input event capacity
                                            System.out.println("Enter the new construcionCost of the event:");
                                            float cost1 = scanner.nextFloat();

                                            // Check if the location is already booked at the same time
                                            if (eventComponent.checkSimilarityEvent(place, startDate, endDate, city)) {
                                                System.out.println("Sorry, this location is already booked at the same time.");
                                                // Exit the switch case
                                            } else {
                                                eventComponent.addEvent(name, ID, cost, cost1,startDate, endDate, city, Integer.parseInt(capacity), place, loggedInUser.getEmail(), loggedInUser.getPassword());
                                            }
                                            // Add the event

                                            break; // Exit the switch case
                                        }
                                        case 3: {
                                            System.out.println("Enter the ID of the event that you want to add an image:");
                                            String eventId = scanner.next();
                                            boolean eventFound = false;

                                            for (Event event : EventRepository.events) {
                                                if (event.getIdOfEvent().equals(eventId)) {
                                                    eventFound = true;
                                                    if (event.getpath() == null) {
                                                        ImageUploader uploader = new ImageUploader();
                                                        uploader.setVisible(true);
                                                        uploader.event = event; // تهيئة الكائن event
                                                        Path imagePath = uploader.getimagepath();
                                                        if (imagePath != null) {
                                                            event.setpath(imagePath);
                                                            System.out.println(event.getpath());
                                                            break;
                                                        }
                                                    } else {
                                                        System.out.println("The event already has an image.");
                                                    }
                                                }
                                            }

                                            if (!eventFound) {
                                                System.out.println("Event not found.");
                                            }

                                            break;
                                        }

                                        case 4: {
                                            System.out.println("*   Now..you can delete an event !   *\n");
                                            System.out.println("Enter the name of the event you want to delete :");
                                            String nameOfEvent = scanner.next();
                                            System.out.println("Enter the ID of the event you want to delete");
                                            String idOfEvent = scanner.next();
                                            eventComponent.deleteEvent(nameOfEvent, idOfEvent);
                                            break;

                                        }


                                    }
                                    break;
                                }
                                case 5: {
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
                                            if (n.getType().equals(Notification.NotificationType.RESERVATIONREQUEST)) {
                                                boolean continueLoop2 = true;
                                                while (continueLoop2) {
                                                    System.out.println("Select a number:");
                                                    System.out.println("1- accept the reservation");
                                                    System.out.println("2- reject the reservation");
                                                    System.out.println("3- Back to notification page");
                                                    int choice2 = readIntegerFromUser(scanner);
                                                    switch (choice2) {
                                                        case 1: {
                                                            n.setApproved(true);
                                                            Notification replyNotification = new Notification();
                                                            replyNotification.createReplyMessage(loggedInUser, true, n.getEvent());
                                                            n.getEvent().setstatusOfEvent("Booked");
                                                            replyNotification.sendReplyMessage(n.sender);
                                                            loggedInUser.notifications.remove(n);
                                                            //new reservation   SendMail.getSendEmail(messageContent, recipientEmail);
                                                          //  n.sender.bookedEvent2.add(n.getEvent());
                                                            n.sender.bookedEvent1.remove(n.getEvent());
                                                            System.out.println("Reservation successful!");

                                                            continueLoop2 = false;
                                                            break;
                                                        }
                                                        case 2: {
                                                            n.setApproved(false);
                                                            loggedInUser.notifications.remove(n);
                                                            Notification replyNotification = new Notification();
                                                            replyNotification.createReplyMessage(loggedInUser, false, n.getEvent());
                                                            replyNotification.sendReplyMessage(n.sender);

                                                            //no reservation
                                                            n.sender.bookedEvent1.remove(n.getEvent());
                                                            System.out.println("The operation succeeded!");
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
                                case 6: {
                                    continueLoop = false;

                                    break;
                                }
                                default: {
                                    System.out.println("Invalid choice");
                                }
                            }

                        }
                    }


                    ///////////**********admain***********************************************************************************
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
                                        System.out.println("\tSelect a number to view more details:");
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
                                                            SendMail.getSendEmail("Accepted :) ", n.sender.getEmail());

                                                            System.out.println("A service provider account has been created");
                                                            UserRepository.reviw.remove(n.sender);
                                                            //email sent
                                                            continueLoop2 = false;
                                                            break;
                                                        }
                                                        case 2: {
                                                            n.setApproved(false);
                                                            loggedInUser.notifications.remove(n);
                                                            SendMail.getSendEmail("Rejected :( ", n.sender.getEmail());

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
                    ///////////*******************************user*****************************************
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
                            System.out.println("8. Show image of event by ID");
                            System.out.println("9. Logout");

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
                                    /////

                                    /////
                                }
                                case 2: {
                                    String eventName;
                                    String eventLocation;
                                    float minPrice;
                                    float maxPrice;
                                    List<Event> resultEvents;
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
                                            default: {
                                                System.out.println("Invalid choice");
                                            }
                                        }
                                    }


                                    break;
                                }

                                case 3: {
                                    String eventId1;
                                    String location1;
                                    Event event;
                                    System.out.println("\t*Now you can book an event*\t");

                                    while (true) {
                                        System.out.print("Enter the ID of the event you want to book: ");
                                        eventId1 = scanner.next();
                                        System.out.print("Enter the location of the event you want to book: ");
                                        location1 = scanner.next();
                                        event = BookingSystem.findEventByIdAndLocation(eventId1,location1);
                                        if(event==null){
                                            System.out.println("The Event not found.");
                                        }
                                        else{

                                        }
                                        break;
                                    }
                                    System.out.print("Enter booking date (yyyy-MM-dd'T'HH:mm:ss): ");
                                    String bookingDateStr = scanner.next();

                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                                    LocalDateTime bookingDate;
                                    try {
                                        bookingDate = LocalDateTime.parse(bookingDateStr, formatter);
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd'T'HH:mm:ss.");
                                        break;
                                    }
                                    float userBalance;
                                    if (loggedInUser.bookedEvent2.size()==0){
                                        System.out.print("Enter your balance: ");
                                         userBalance = scanner.nextFloat();
                                    }
                                    else
                                        userBalance=loggedInUser.getAccountBalance();

                                    boolean bookingSuccessful = BookingSystem.bookEvent(eventId1, location1, bookingDate, userBalance, loggedInUser);
                                    if (!bookingSuccessful) {
                                        System.out.println("Booking failed. Please try again.");
                                    }
                                    break;
                                }
                                case 4: {
                                    String userEmail = loggedInUser.getEmail();
                                    //List<Event> bookedEvents = loggedInUser.getBookedEventsForUser(userEmail);
                                    if (!loggedInUser.bookedEvent2.isEmpty()) {
                                        System.out.println("Booked Events:");
                                        for (Event event : loggedInUser.bookedEvent2) {
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
                                    if (!loggedInUser.bookedEvent2.isEmpty()) {
                                        System.out.println("Booked Events:");
                                        for (Event event : loggedInUser.bookedEvent2) {
                                            System.out.println("Event Name: " + event.getNameOfEvent());
                                            System.out.println("Event ID: " + event.getIdOfEvent());
                                            System.out.println("Location: " + event.getPlaceOfEvent().getLocationOfPlace());
                                            System.out.println("Event Start Time: " + event.getEventStartTime());
                                            System.out.println("Event End Time: " + event.getEventEndTime());
                                            System.out.println("------------------------------------");
                                        }
                                    } else {
                                        System.out.println("You have not booked any events.");
                                        break;
                                    }

                                    String IDC;
                                    boolean flagID = false;
                                    while (true) {
                                        System.out.print("Enter the ID of event you want to cancel it: ");
                                        IDC = scanner.next();
                                        for (Event event : loggedInUser.bookedEvent2) {
                                            if (event.getIdOfEvent().equals(IDC)) {
                                                flagID = true;
                                                break;
                                            }
                                        }
                                        if (flagID) {
                                            break;
                                        } else {
                                            System.out.println("Please try again....!");
                                        }
                                    }

                                    System.out.print("Do you want to cancel this event? (yes/no): ");
                                    String cancelChoice = scanner.next();
                                    if (cancelChoice.equalsIgnoreCase("yes")) {
                                        // Remove the booking
                                        Iterator<Event> iterator = loggedInUser.bookedEvent2.iterator();
                                        while (iterator.hasNext()) {
                                            Event event2 = iterator.next();
                                            if (event2.getIdOfEvent().equals(IDC)) {
                                                iterator.remove();
                                                event2.setEventStatus("unbook"); // Update event status
                                                flagID = true; // Set flag to true as event is found
                                            }
                                        }
                                        if (flagID) {
                                            System.out.println("Event has been cancelled successfully.");
                                        } else {
                                            System.out.println("Event with ID " + IDC + " not found in your booked events.");
                                        }
                                    } else {
                                        System.out.println("Cancellation aborted.");
                                    }
                                    break;
                                }


                                case 6: {//////

                                    System.out.println("\t**\tNow you can Edit Your Profile\t**\t");
                                    System.out.println("Choose one of the fields to modify:");
                                    System.out.println("1-your First Name : ");
                                    System.out.println("2-Your Last Nme :");
                                    System.out.println("3-Your Email");
                                    System.out.println("4-Your Password");
                                    int input5= scanner.nextInt();
                                    switch (input5) {
                                        case 1: //edit first Name
                                        {  System.out.println("Your Current First Name: " + loggedInUser.getFirstName());
                                            String newFirstName;
                                            while (true){
                                                System.out.print("Enter New First Name: ");
                                                newFirstName = scanner.next().trim();
                                                if (newFirstName.isEmpty()||newFirstName.length()==1) {
                                                    System.out.println("Invalid input! Please try again.");
                                                }
                                                else
                                                    loggedInUser.setFirstName(newFirstName);
                                                break;
                                            }
                                            System.out.println("Updated successfully");
                                            System.out.println(loggedInUser.getFirstName());


                                            break;}
                                        case 2://edit last name
                                        {
                                            System.out.println("Your Current Last Name: " + loggedInUser.getLastName());
                                            while (true) {
                                                System.out.print("Enter New Last Name: ");
                                                String newLastName = scanner.next().trim();
                                                if (newLastName.isEmpty() || newLastName.length() == 1) {
                                                    System.out.println(" Invalid input! Please try again.");
                                                } else
                                                    loggedInUser.setFirstName(newLastName);
                                                System.out.println("Updated successfully");
                                                break;
                                            }
                                        }
                                        case 3://edit Email
                                        { System.out.println("Your Current Email : " + loggedInUser.getEmail());
                                            boolean existEmail2=false;
                                            while (true) {
                                                System.out.print("Enter New email: ");
                                                String newEmail = scanner.next().trim();

                                                if (!userComponent.isValidEmail(newEmail)) {
                                                    System.out.println("The email you entered is invalid. Please try again.");
                                                    continue;
                                                }

                                                for (User user : UserRepository.users) {
                                                    if (user.getEmail().equals(newEmail)) {
                                                        existEmail2 = true;
                                                        break;
                                                    }
                                                }
                                                if (existEmail2) {
                                                    System.out.println("The email you entered is already exist. Please enter another one.");
                                                    existEmail2 = false;
                                                } else {

                                                    loggedInUser.setEmail(newEmail);
                                                    System.out.println("Updated successfully");
                                                    break;
                                                }
                                            }
                                            break;}
                                        case 4:{ //edit Password
                                            System.out.print("Your Current Password: "+loggedInUser.getPassword());
                                            String newPassword ;
                                            while (true) {
                                                System.out.print("Enter new password: ");
                                                newPassword = scanner.next().trim();
                                                if (userComponent.isValidPassword(newPassword)) {
                                                    break;
                                                } else {
                                                    System.out.println("The password should contain at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one of the following symbols: !@#$%^");
                                                }
                                            }

                                            while (true) {
                                                System.out.print("Enter Confirm new password:");
                                                String confirmNewPassword = scanner.next();
                                                if (confirmNewPassword.equals(newPassword)) {
                                                    loggedInUser.setPassword(newPassword);
                                                    System.out.println("Updated successfully");
                                                    break;
                                                } else {
                                                    System.out.println("The confirmed password does not match the original password.");
                                                }
                                            }
                                            break;}


                                    }
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
                                            if (n.isApproved()) {
                                                System.out.println("Enter your credit card Number:");
                                                String CardNumber = scanner.next();
                                                boolean Successfulpayment = BookingSystem.processPayment(CardNumber, n.getEvent(), loggedInUser);
                                                if (Successfulpayment) {
                                                    System.out.println("Payment Successful .");

                                                }
                                                else
                                                    System.out.println("Payment failed.");
                                            }

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
                                        else if (choice1 == i) {
                                            continueLoop1 = false;
                                        } else {
                                            System.out.println("Invalid choice");
                                        }
                                    }
                                    break;
                                }

                                case 8: {
                                    System.out.println("Enter the ID of the event that you want to Show the image:");
                                    String eventId = scanner.next();
                                    boolean eventFound = false;

                                    for (Event event : EventRepository.events) {
                                        if (event.getIdOfEvent().equals(eventId)) {
                                            eventFound = true;
                                            if (event.getpath() == null) {
                                                System.out.println("The event does'nt have any images.");
                                            } else {
                                                ImageUploader.openImage(event.getpath());
                                            }
                                        }
                                    }

                                    if (!eventFound) {
                                        System.out.println("Event not found.");
                                    }

                                    break;
                                }
                                case 9:{
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


        } while (true); // Repeat until a valid input is provided


    }
}

