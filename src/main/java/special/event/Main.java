package special.event;

import components.*;
import repositories.EventRepository;
import repositories.UserRepository;
import java.time.LocalTime;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;


public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static String readMultiLineStringFromUser() {
        Scanner scanner = new Scanner(System.in);
        StringBuilder inputBuilder = new StringBuilder();
        logger.info("press Enter twice to finish:\n");
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
                logger.info("Invalid input! Please enter an integer:");
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
                logger.info("Invalid input! Please enter a valid price:");
                scanner.next();
            }
        }

        return price;
    }

    public static void printEventDetails(List<Event> events) {
        for (Event event : events) {
            logger.info("Event ID: " + event.getIdOfEvent());
            logger.info("Event Name: " + event.getNameOfEvent());
            logger.info("Location: " + event.getPlaceOfEvent().getLocationOfPlace());
            logger.info("Start Time: " + event.getEventStartTime());
            logger.info("End Time: " + event.getEventEndTime());
            logger.info("Cost: " + event.getCostOfEvent());
            logger.info("Status: " + event.getstatusOfEvent());
            logger.info("------------------------");
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
        o.readUsers(UserRepository.FILE_NAME1);
        kk.readEventFile(EventRepository.FILE_NAME);


        UserComponent userComponent = new UserComponent();
        User loggedInUser = null;
        Boolean signupSurvice = Boolean.FALSE;
        logger.info("\n\n****   Welcome to the Event Planner System   ****\n");

        do {

            System.out.print("\n\nPress 1 for login, 2 for Signup: ");
            while (true) {
                if (scanner.hasNextInt()) {
                    x = scanner.nextInt();
                    logger.info("You entered: " + x);
                    break;
                } else {
                    logger.info("Invalid input! \nPress 1 for login, 2 for Signup: ");
                    scanner.next();
                }
            }

            if (x == 1) {
                logger.info("\n**         Login         **\n");
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
                    logger.info("Login successful!");
                    // Exit the loop after successful login
                } else {
                    logger.info("Invalid credentials. Please try again.\n");
                }

            } else if (x == 2) {
                logger.info("**       Signup      **\n");

                System.out.print("Enter first name: ");
                String firstName = scanner.next();
                System.out.print("Enter last name: ");
                String lastName = scanner.next();

                boolean existEmail = false;

                while (true) {
                    System.out.print("Enter email: ");
                    email = scanner.next().trim();

                    if (!userComponent.isValidEmail(email)) {
                        logger.info("The email you entered is invalid. Please try again.");
                        continue;
                    }

                    for (User user : UserRepository.users) {
                        if (user.getEmail().equals(email)) {
                            existEmail = true;
                            break;
                        }
                    }

                    if (existEmail) {
                        logger.info("The email you entered is already exist. Please enter another one.");
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
                        logger.info("The password should contain at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one of the following symbols: !@#$%^");
                    }
                }

                while (true) {
                    System.out.print("Confirm password:");

                    confirmPassword = scanner.next();
                    if (confirmPassword.equals(signuppass)) {
                        break;
                    } else {
                        logger.info("The confirmed password does not match the original password.");
                    }
                }


                logger.info("Enter 1 for SERVICE_PROVIDER \nOr 2 for USER");
                do {
                    y = scanner.nextInt();

                    if (y == 1 || y == 2) {
                        userType = (y == 1) ? "SERVICE_PROVIDER" : "USER";
                        break;
                    } else {
                        logger.info("Invalid value. Please enter 1 for SERVICE_PROVIDER or 2 for USER");
                    }
                } while (true);


                boolean isValid = userComponent.validateSignup(firstName, lastName, email, signuppass, confirmPassword, userType);

                if (isValid) {
                    logger.info("Creating an account successfully");
                    user2 = new User(email, signuppass, userType, firstName, lastName);
                    if (userType.equals("USER")) {

                        UserRepository.addToUsers(user2);
                        logger.info("The account is now complete and you are able to log in :) ");
                    }
                    loggedInUser = userComponent.validateLogin(email, signuppass);

                    if (userType.equals("SERVICE_PROVIDER")) {
                        logger.info("Enter the services you need to provide: ");

                        String SeviceMessage = scanner.next();

                        UserRepository.addToReviw(user2);
                        Notification accountRequestNotification = new Notification();
                        accountRequestNotification.createAccountCreationRequest(user2, SeviceMessage);
                        accountRequestNotification.sendCreationRequest();
                        signupSurvice = Boolean.TRUE;
                    }


                } else {
                    logger.info("Account creation failed.");

                }

            } else {
                logger.info("Invalid value\n");

            }



            if (signupSurvice.equals(Boolean.FALSE)) {
                type = loggedInUser.getType();
                if (userComponent.validateUserType(loggedInUser, type)) {
                    if (type.equals("SERVICE_PROVIDER")) {
                        logger.info("\t** Hello in your profile **\n");
                        logger.info("Name: " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
                        logger.info("Email: " + loggedInUser.getEmail() + "\n");
                        boolean continueLoop = true;
                        while (continueLoop) {
                            logger.info("Select a number to view its contents:");
                            logger.info("1- Edit your profile:");
                            logger.info("2- Analyze the event that you have:");
                            logger.info("3- Calendar:");
                            logger.info("4- Event management:");
                            logger.info("5- Your notifications:");
                            logger.info("6- Logout");
                            int choice = readIntegerFromUser(scanner);

                            switch (choice) {
                                case 1: {

                                    logger.info("\t**\tNow you can Edit Your Profile\t**\t");
                                    logger.info("Choose one of the fields to modify:");
                                    logger.info("1-your First Name : ");
                                    logger.info("2-Your Last Nme :");
                                    logger.info("3-Your Email");
                                    logger.info("4-Your Password");
                                    int input5= scanner.nextInt();
                                    switch (input5) {
                                        case 1:
                                        {  logger.info("Your Current First Name: " + loggedInUser.getFirstName());
                                            String newFirstName;
                                            while (true){
                                                System.out.print("Enter New First Name: ");
                                                newFirstName = scanner.next().trim();
                                                if (newFirstName.isEmpty()||newFirstName.length()==1) {
                                                    logger.info("Invalid input! Please try again.");
                                                }
                                                else
                                                    loggedInUser.setFirstName(newFirstName);
                                                break;
                                            }
                                            logger.info("Updated successfully");
                                            logger.info(loggedInUser.getFirstName());


                                            break;}
                                        case 2:
                                        {
                                            logger.info("Your Current Last Name: " + loggedInUser.getLastName());
                                            while (true) {
                                                System.out.print("Enter New Last Name: ");
                                                String newLastName = scanner.next().trim();
                                                if (newLastName.isEmpty() || newLastName.length() == 1) {
                                                    logger.info(" Invalid input! Please try again.");
                                                } else
                                                    loggedInUser.setFirstName(newLastName);
                                                logger.info("Updated successfully");
                                                break;
                                            }
                                        }
                                        case 3:
                                        { logger.info("Your Current Email : " + loggedInUser.getEmail());
                                            boolean existEmail2=false;
                                            while (true) {
                                                System.out.print("Enter New email: ");
                                                String newEmail = scanner.next().trim();

                                                if (!userComponent.isValidEmail(newEmail)) {
                                                    logger.info("The email you entered is invalid. Please try again.");
                                                    continue;
                                                }

                                                for (User user : UserRepository.users) {
                                                    if (user.getEmail().equals(newEmail)) {
                                                        existEmail2 = true;
                                                        break;
                                                    }
                                                }
                                                if (existEmail2) {
                                                    logger.info("The email you entered is already exist. Please enter another one.");
                                                    existEmail2 = false;
                                                } else {

                                                    loggedInUser.setEmail(newEmail);
                                                    logger.info("Updated successfully");
                                                    break;
                                                }
                                            }
                                            break;}
                                        case 4:{
                                            System.out.print("Your Current Password: "+loggedInUser.getPassword());
                                            String newPassword ;
                                            while (true) {
                                                System.out.print("Enter new password: ");
                                                newPassword = scanner.next().trim();
                                                if (userComponent.isValidPassword(newPassword)) {
                                                    break;
                                                } else {
                                                    logger.info("The password should contain at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one of the following symbols: !@#$%^");
                                                }
                                            }

                                            while (true) {
                                                System.out.print("Enter Confirm new password:");
                                                String confirmNewPassword = scanner.next();
                                                if (confirmNewPassword.equals(newPassword)) {
                                                    loggedInUser.setPassword(newPassword);
                                                    logger.info("Updated successfully");
                                                    break;
                                                } else {
                                                    logger.info("The confirmed password does not match the original password.");
                                                }
                                            }
                                            break;}


                                    }
                                    break;
                                }
                                case 2: {
                                    int numberOfEvent = 0;
                                    logger.info("\t\tYOUR EVENTS :  \n");
                                    for (Event event : EventRepository.events) {
                                        if (event.getEventOwner().getEmail().equals(loggedInUser.getEmail())) {

                                            numberOfEvent++;
                                            logger.info("Name Of Event :" + event.getNameOfEvent() + "\tID Of Event :" + event.getIdOfEvent() + "\n");
                                        }
                                    }
                                    logger.info("The total number of your events = " + numberOfEvent + "\n");



                                    int numberOfBookedEvent = 0;
                                    float totalProfit = 0;
                                    float profit = 0;
                                    logger.info("\n\t\tYOUR BOOKED EVENTS :");
                                    for (Event event : EventRepository.events) {
                                        if (event.getstatusOfEvent().equalsIgnoreCase("booked")&&event.getEventOwner().getEmail().equals(loggedInUser.getEmail())) {
                                            profit = ( event.getCostOfEvent()-event.getEventConstructionCost() );
                                            totalProfit += ( event.getCostOfEvent()-event.getEventConstructionCost() );
                                            logger.info("Name Of Event :" + event.getNameOfEvent() + "\tID Of Event:" + event.getIdOfEvent());
                                            logger.info("The profit from this event :" +profit);

                                            numberOfBookedEvent++;

                                        }
                                    }
                                    logger.info("\nThe total number of your booked events = " + numberOfBookedEvent + "\n");
                                    logger.info("The total profit from booked events = " + profit + "\n");


                                    int numberOfUnbookedEvent = 0;
                                    logger.info("\n\t\tYOUR UnBOOKED EVENTS :");
                                    for (Event event : EventRepository.events) {
                                        if (event.getstatusOfEvent().equalsIgnoreCase("unbooked")&&event.getEventOwner().getEmail().equals(loggedInUser.getEmail())) {
                                            logger.info("Name Of Event :" + event.getNameOfEvent() + "\tID Of Event:" + event.getIdOfEvent());
                                            numberOfUnbookedEvent++;
                                        }
                                    }
                                    logger.info("\nThe total number of your Unbooked events = " + numberOfUnbookedEvent + "\n");

                                    /////
                                    break;
                                }
                                case 3: {


                                }

                                case 4: {
                                    logger.info("Select one:");
                                    logger.info("1- Add new event");
                                    logger.info("2- Edit an event");
                                    logger.info("3- Add an image for an event");
                                    logger.info("4- Delete an event");
                                    int eventChoice = scanner.nextInt();
                                    switch (eventChoice) {
                                        case 1: {

                                            String name, ID;
                                            float cost;
                                            String date2;
                                            LocalDateTime endDate;
                                            String capacity;
                                            logger.info("\t*   Now..you can add a new event!   *\n");


                                            while (true) {
                                                logger.info("Enter the name of the event:");
                                                name = scanner.next();
                                                if (name == null || name.length() <= 1)
                                                    logger.info("The name you entered is invalid, please try again....!");
                                                else
                                                    break;
                                            }


                                            while (true) {
                                                logger.info("Enter the ID of the event:");
                                                ID = scanner.next();
                                                if (!eventComponent.checkIdOfEvent(ID))
                                                    logger.info(" please try again....!");
                                                else
                                                    break;
                                            }


                                            while (true) {
                                                logger.info("Enter the cost of the event:");
                                                cost = scanner.nextFloat();
                                                if (!eventComponent.checkCostOfEvent(cost))
                                                    logger.info(" please try again....!");
                                                else
                                                    break;
                                            }
                                            while (true) {

                                                logger.info("Enter the capacity of the place:");
                                                capacity = scanner.next();
                                                if (!Place.checkCapacityOfPlace(Integer.parseInt(capacity))) {
                                                    logger.info("Please enter a valid capacity.");
                                                } else {
                                                    break;
                                                }

                                                scanner.next();
                                            }


                                            logger.info("Enter the event start time (use format yyyy-mm-ddThh:mm:ss.908732):");
                                            String date1 = scanner.next();
                                            LocalDateTime startDate = eventComponent.dateConverter(date1);

                                            while (true) {
                                                logger.info("Enter the event end time (use format yyyy-mm-ddThh:mm:ss.908732):");
                                                date2 = scanner.next();

                                                endDate = eventComponent.dateConverter(date2);
                                                if (startDate.isEqual(endDate)) {
                                                    logger.info("The start date cannot be the same as the end date. Please enter a valid date.\n");
                                                } else {
                                                    break;
                                                }
                                            }


                                            logger.info("Enter the location (city) of the event:");
                                            String city = scanner.next();

                                            logger.info("Enter the place (hall/hotel) of the event:");
                                            String place = scanner.next();

                                            logger.info("Enter the construcionCost of the event:");
                                            float cost1 = scanner.nextFloat();

                                            if (eventComponent.checkSimilarityEvent(place, startDate, endDate, city)) {
                                                logger.info("Sorry, this location is already booked at the same time.");
                                            } else {
                                                eventComponent.addEvent(name, ID, cost, cost1,startDate, endDate, city, Integer.parseInt(capacity), place, loggedInUser.getEmail(), loggedInUser.getPassword());
                                            }

                                            break;
                                        }
                                        case 2: {

                                            String name, ID;
                                            float cost;
                                            String date2;
                                            LocalDateTime endDate;
                                            String capacity;
                                            logger.info("\t*   Now..you can Edit your event!   *\n");

                                            while (true) {
                                                logger.info("Enter the new name of the event:");
                                                name = scanner.next();
                                                if (name == null || name.length() <= 1)
                                                    logger.info("The name you entered is invalid, please try again....!");
                                                else
                                                    break;
                                            }

                                            while (true) {
                                                logger.info("Enter the new ID of the event:");
                                                ID = scanner.next();
                                                if (!eventComponent.checkIdOfEvent(ID))
                                                    logger.info(" please try again....!");
                                                else
                                                    break;
                                            }

                                            while (true) {
                                                logger.info("Enter the new cost of the event:");
                                                cost = scanner.nextFloat();
                                                if (!eventComponent.checkCostOfEvent(cost))
                                                    logger.info(" please try again....!");
                                                else
                                                    break;
                                            }
                                            while (true) {

                                                logger.info("Enter the new capacity of the place:");
                                                capacity = scanner.next();
                                                if (!Place.checkCapacityOfPlace(Integer.parseInt(capacity))) {
                                                    logger.info("Please enter a valid capacity.");
                                                } else {
                                                    break;
                                                }

                                                scanner.next();
                                            }

                                            logger.info("Enter the event new start time (use format yyyy-mm-ddThh:mm:ss.908732):");
                                            String date1 = scanner.next();
                                            LocalDateTime startDate = eventComponent.dateConverter(date1);

                                            while (true) {
                                                logger.info("Enter the event new end time (use format yyyy-mm-ddThh:mm:ss.908732):");
                                                date2 = scanner.next();

                                                endDate = eventComponent.dateConverter(date2);
                                                if (startDate.isEqual(endDate)) {
                                                    logger.info("The start date cannot be the same as the end date. Please enter a valid date.\n");
                                                } else {
                                                    break;
                                                }
                                            }

                                            logger.info("Enter the new location (city) of the event:");
                                            String city = scanner.next();

                                            logger.info("Enter the new place (hall/hotel) of the event:");
                                            String place = scanner.next();

                                            logger.info("Enter the new construcionCost of the event:");
                                            float cost1 = scanner.nextFloat();

                                            if (eventComponent.checkSimilarityEvent(place, startDate, endDate, city)) {
                                                logger.info("Sorry, this location is already booked at the same time.");
                                            } else {
                                                eventComponent.addEvent(name, ID, cost, cost1,startDate, endDate, city, Integer.parseInt(capacity), place, loggedInUser.getEmail(), loggedInUser.getPassword());
                                            }

                                            break;
                                        }
                                        case 3: {
                                            logger.info("Enter the ID of the event that you want to add an image:");
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
                                                            logger.info(event.getpath().toString());
                                                            break;
                                                        }
                                                    } else {
                                                        logger.info("The event already has an image.");
                                                    }
                                                }
                                            }

                                            if (!eventFound) {
                                                logger.info("Event not found.");
                                            }

                                            break;
                                        }

                                        case 4: {
                                            logger.info("*   Now..you can delete an event !   *\n");
                                            logger.info("Enter the name of the event you want to delete :");
                                            String nameOfEvent = scanner.next();
                                            logger.info("Enter the ID of the event you want to delete");
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
                                        logger.info("Your Notifications:");
                                        logger.info("Select a number to view more details:");
                                        int i = 1;

                                        for (Notification n : loggedInUser.notifications) {
                                            logger.info(i + "- " + n.getMessage() + " at ( " + n.getSentDateTime() + " )");
                                            i++;
                                        }
                                        logger.info(i + "- Back to home page");
                                        int choice1 = readIntegerFromUser(scanner);
                                        if (choice1 < i && choice1 >= 1) {

                                            Notification n = loggedInUser.notifications.get(choice1 - 1);
                                            logger.info(n.showNtificationDetails());
                                            if (n.getType().equals(Notification.NotificationType.RESERVATIONREQUEST)) {
                                                boolean continueLoop2 = true;
                                                while (continueLoop2) {
                                                    logger.info("Select a number:");
                                                    logger.info("1- accept the reservation");
                                                    logger.info("2- reject the reservation");
                                                    logger.info("3- Back to notification page");
                                                    int choice2 = readIntegerFromUser(scanner);
                                                    switch (choice2) {
                                                        case 1: {
                                                            n.setApproved(true);
                                                            Notification replyNotification = new Notification();
                                                            replyNotification.createReplyMessage(loggedInUser, true, n.getEvent());
                                                            n.getEvent().setstatusOfEvent("Booked");
                                                            replyNotification.sendReplyMessage(n.sender);
                                                            loggedInUser.notifications.remove(n);
                                                            n.sender.bookedEvent1.remove(n.getEvent());
                                                            logger.info("Reservation successful!");

                                                            continueLoop2 = false;
                                                            break;
                                                        }
                                                        case 2: {
                                                            n.setApproved(false);
                                                            loggedInUser.notifications.remove(n);
                                                            Notification replyNotification = new Notification();
                                                            replyNotification.createReplyMessage(loggedInUser, false, n.getEvent());
                                                            replyNotification.sendReplyMessage(n.sender);

                                                            n.sender.bookedEvent1.remove(n.getEvent());
                                                            logger.info("The operation succeeded!");
                                                            continueLoop2 = false;
                                                            break;
                                                        }
                                                        case 3: {
                                                            continueLoop2 = false;
                                                            break;
                                                        }
                                                        default: {
                                                            logger.info("Invalid choice");
                                                        }
                                                    }
                                                }
                                            } else {
                                                boolean continueLoop2 = true;
                                                while (continueLoop2) {
                                                    logger.info("Enter 1 to back to notification page ");
                                                    int choice2 = readIntegerFromUser(scanner);
                                                    switch (choice2) {
                                                        case 1: {
                                                            continueLoop2 = false;
                                                            break;
                                                        }
                                                        default: {
                                                            logger.info("Invalid choice");
                                                        }
                                                    }
                                                }
                                            }

                                        } else if (choice1 == i) {
                                            continueLoop1 = false;
                                        } else {
                                            logger.info("Invalid choice");
                                        }


                                    }

                                    break;
                                }
                                case 6: {
                                    continueLoop = false;

                                    break;
                                }
                                default: {
                                    logger.info("Invalid choice");
                                }
                            }

                        }
                    }


                    else if (type.equals("ADMIN")) {
                        logger.info("\t** Hello in your profile **\n");
                        logger.info("Name: " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
                        logger.info("Email: " + loggedInUser.getEmail() + "\n");

                        boolean continueLoop = true;
                        while (continueLoop) {
                            logger.info("Select a number to view its contents:");
                            logger.info("1- Edit your profile");
                            logger.info("2- Statistics and analysis");
                            logger.info("3- Your notifications");
                            logger.info("4- Send an announcement");
                            logger.info("5- Logout");
                            int choice = readIntegerFromUser(scanner);

                            switch (choice) {
                                case 1: {


                                    logger.info("\t**\tNow you can Edit Your Profile\t**\t");
                                    logger.info("Choose one of the fields to modify:");
                                    logger.info("1-your First Name : ");
                                    logger.info("2-Your Last Nme :");
                                    logger.info("3-Your Email");
                                    logger.info("4-Your Password");
                                    int input5= scanner.nextInt();
                                    switch (input5) {
                                        case 1:
                                        {  logger.info("Your Current First Name: " + loggedInUser.getFirstName());
                                            String newFirstName;
                                            while (true){
                                                System.out.print("Enter New First Name: ");
                                                newFirstName = scanner.next().trim();
                                                if (newFirstName.isEmpty()||newFirstName.length()==1) {
                                                    logger.info("Invalid input! Please try again.");
                                                }
                                                else
                                                    loggedInUser.setFirstName(newFirstName);
                                                break;
                                            }
                                            logger.info("Updated successfully");
                                            logger.info(loggedInUser.getFirstName());


                                            break;}
                                        case 2:
                                        {
                                            logger.info("Your Current Last Name: " + loggedInUser.getLastName());
                                            while (true) {
                                                System.out.print("Enter New Last Name: ");
                                                String newLastName = scanner.next().trim();
                                                if (newLastName.isEmpty() || newLastName.length() == 1) {
                                                    logger.info(" Invalid input! Please try again.");
                                                } else
                                                    loggedInUser.setFirstName(newLastName);
                                                logger.info("Updated successfully");
                                                break;
                                            }
                                        }
                                        case 3:
                                        { logger.info("Your Current Email : " + loggedInUser.getEmail());
                                            boolean existEmail2=false;
                                            while (true) {
                                                System.out.print("Enter New email: ");
                                                String newEmail = scanner.next().trim();

                                                if (!userComponent.isValidEmail(newEmail)) {
                                                    logger.info("The email you entered is invalid. Please try again.");
                                                    continue;
                                                }

                                                for (User user : UserRepository.users) {
                                                    if (user.getEmail().equals(newEmail)) {
                                                        existEmail2 = true;
                                                        break;
                                                    }
                                                }
                                                if (existEmail2) {
                                                    logger.info("The email you entered is already exist. Please enter another one.");
                                                    existEmail2 = false;
                                                } else {

                                                    loggedInUser.setEmail(newEmail);
                                                    logger.info("Updated successfully");
                                                    break;
                                                }
                                            }
                                            break;}
                                        case 4:{
                                            System.out.print("Your Current Password: "+loggedInUser.getPassword());
                                            String newPassword ;
                                            while (true) {
                                                System.out.print("Enter new password: ");
                                                newPassword = scanner.next().trim();
                                                if (userComponent.isValidPassword(newPassword)) {
                                                    break;
                                                } else {
                                                    logger.info("The password should contain at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one of the following symbols: !@#$%^");
                                                }
                                            }

                                            while (true) {
                                                System.out.print("Enter Confirm new password:");
                                                String confirmNewPassword = scanner.next();
                                                if (confirmNewPassword.equals(newPassword)) {
                                                    loggedInUser.setPassword(newPassword);
                                                    logger.info("Updated successfully");
                                                    break;
                                                } else {
                                                    logger.info("The confirmed password does not match the original password.");
                                                }
                                            }
                                            break;}


                                    }
                                    break;
                                }
                                case 2: {
                                    UserRepository userRepository = new UserRepository();
                                    EventRepository eventRepository = new EventRepository();
                                    LocalDateTime now = LocalDateTime.now();
                                    int NumberOfUsers = 0;
                                    int NumberOfProviders = 0;
                                    int NumberOfEvents = eventRepository.events.size();
                                    float totalProfit = 0;


                                    logger.info("------------------------");
                                    logger.info("    System statistics   ");
                                    for(User user : userRepository.users){
                                        if(user.getType().equals("USER")){
                                            NumberOfUsers++;
                                        }
                                        if(user.getType().equals("SERVICE_PROVIDER")){
                                            NumberOfProviders++;
                                        }
                                        for(Event event : user.bookedEvent2){
                                            if(event.getEventEndTime().isBefore(now))
                                                totalProfit += (event.getCostOfEvent()-event.getEventConstructionCost());
                                        }

                                    }


                                    int maxCount = 0;
                                    Event mostRepeatedEvent = null ;
                                    LocalTime mostPopularTime = null;
                                    int maxCount1 = 0;
                                    float averageOfEvents = 0;

                                    for (Event event : EventRepository.events) {
                                        averageOfEvents +=  event.getCostOfEvent();

                                        int eventCount1 = 1;
                                        for (User innerUser : userRepository.users) {
                                            for (Event innerEvent : innerUser.bookedEvent2) {

                                                if (event.getEventStartTime().toLocalTime().equals(innerEvent.getEventStartTime().toLocalTime())) {
                                                    eventCount1++;
                                                }
                                            }
                                        }
                                        if (eventCount1 > maxCount1) {
                                            maxCount1 = eventCount1;
                                            mostPopularTime = event.getEventStartTime().toLocalTime();
                                        }

                                    }
                                    averageOfEvents /= EventRepository.events.size();

                                    logger.info("Number of users registered in the system :");
                                    System.out.println(NumberOfUsers);
                                    logger.info("The number of service providers registered in the system :");
                                    System.out.println(NumberOfProviders);
                                    logger.info("The number of events inside the system :");
                                    System.out.println(NumberOfEvents);
                                    logger.info("Average cost of an event :");
                                    System.out.println(averageOfEvents);
                                    logger.info("Total profit from Complete events :");
                                    System.out.println(totalProfit);
                                    if(!mostPopularTime.equals(null)) {
                                        logger.info("Most popular time to book :");
                                        System.out.println(mostPopularTime);
                                    }

                                    break;
                                }
                                case 3: {
                                    Boolean continueLoop1 = true;
                                    while (continueLoop1) {
                                        logger.info("Your Notifications:");
                                        logger.info("\tSelect a number to view more details:");
                                        int i = 1;

                                        for (Notification n : loggedInUser.notifications) {
                                            logger.info(i + "- " + n.getMessage() + " at ( " + n.getSentDateTime() + " )");
                                            i++;
                                        }
                                        logger.info(i + "- Back to home page");
                                        int choice1 = readIntegerFromUser(scanner);
                                        if (choice1 < i && choice1 >= 1) {

                                            Notification n = loggedInUser.notifications.get(choice1 - 1);
                                            logger.info(n.showNtificationDetails());
                                            if (n.getType().equals(Notification.NotificationType.ACCOUNTREQUEST)) {
                                                boolean continueLoop2 = true;
                                                while (continueLoop2) {
                                                    logger.info("Select a number:");
                                                    logger.info("1- accept the request");
                                                    logger.info("2- reject the request");
                                                    logger.info("3- Back to notification page");
                                                    int choice2 = readIntegerFromUser(scanner);
                                                    switch (choice2) {
                                                        case 1: {
                                                            n.setApproved(true);
                                                            loggedInUser.notifications.remove(n);
                                                            UserRepository.addToUsers(n.sender);
                                                            SendMail.getSendEmail("Accepted :) ", n.sender.getEmail());

                                                            logger.info("A service provider account has been created");
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
                                                            logger.info("Invalid choice");
                                                        }
                                                    }
                                                }
                                            } else {
                                                boolean continueLoop2 = true;
                                                while (continueLoop2) {
                                                    logger.info("Enter 1 to back to notification page ");
                                                    int choice2 = readIntegerFromUser(scanner);
                                                    switch (choice2) {
                                                        case 1: {
                                                            continueLoop2 = false;
                                                            break;
                                                        }
                                                        default: {
                                                            logger.info("Invalid choice");
                                                        }
                                                    }
                                                }
                                            }

                                        } else if (choice1 == i) {
                                            continueLoop1 = false;
                                        } else {
                                            logger.info("Invalid choice");
                                        }


                                    }

                                    break;
                                }
                                case 4: {
                                    Boolean continueLoop1 = true;
                                    while (continueLoop1) {
                                        logger.info("Select a number :");
                                        logger.info("1- Enter a new message to sent");
                                        logger.info("2- Back to home page");
                                        int choice1 = readIntegerFromUser(scanner);

                                        switch (choice1) {
                                            case 1: {
                                                logger.info("*   Now..you can send an announcement !   *\n");
                                                logger.info("Enter the message you want to send to users:");
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
                                                logger.info("Invalid choice");
                                        }
                                    }

                                    break;
                                }
                                case 5:

                                    continueLoop = false;

                                    break;
                                default:
                                    logger.info("Invalid choice");
                            }
                        }

                    }
                    else {
                        logger.info("Logged in as a regular user.");
                        logger.info("\t** Hello in your profile **\n");
                        logger.info("Name: " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
                        logger.info("Email: " + loggedInUser.getEmail() + "\n");
                        boolean continueLoop3 = true;
                        while (continueLoop3) {
                            logger.info("What would you like to do?");
                            logger.info("1. View available events");
                            logger.info("2. Search");//aseeel.
                            logger.info("3. Book an event");
                            logger.info("4. View booked events");
                            logger.info("5. Cancel booked events");
                            logger.info("6. Edit profile");
                            logger.info("7. Notifications");
                            logger.info("8. Show image of event by ID");
                            logger.info("9. Logout");

                            int choice = readIntegerFromUser(scanner);
                            switch (choice) {
                                case 1: {
                                    logger.info("Available Events:");
                                    EventRepository eventRepository = new EventRepository();
                                    List<Event> events = EventRepository.events;
                                    for (Event event : events) {
                                        logger.info("Event ID: " + event.getIdOfEvent());
                                        logger.info("Event Name: " + event.getNameOfEvent());
                                        logger.info("Location: " + event.getPlaceOfEvent().getLocationOfPlace());
                                        logger.info("Start Time: " + event.getEventStartTime());
                                        logger.info("End Time: " + event.getEventEndTime());
                                        logger.info("Cost: " + event.getCostOfEvent());
                                        logger.info("Status: " + event.getstatusOfEvent());
                                        logger.info("------------------------");
                                    }
                                    break;

                                }
                                case 2: {
                                    String eventName;
                                    String eventLocation;
                                    float minPrice;
                                    float maxPrice;
                                    List<Event> resultEvents;
                                    boolean continueLoop = true;
                                    while (continueLoop) {
                                        logger.info("1. Search by name");
                                        logger.info("2. Search by name and location");//aseeel.
                                        logger.info("3. Search by name and price");
                                        logger.info("4. Search by name, place and price");
                                        logger.info("5. Show all events");
                                        logger.info("6. Back to home page");
                                        logger.info("Select an option to search:");
                                        int choice1 = readIntegerFromUser(scanner);
                                        switch (choice1) {
                                            case 1: {
                                                logger.info("1. Enter name of event: ");
                                                eventName = scanner.next();
                                                resultEvents = Checker.checkNameOfEvent(eventName);
                                                logger.info("------------------------");
                                                if (!resultEvents.equals(null)) {
                                                    printEventDetails(resultEvents);
                                                } else {
                                                    logger.info("No result :(");
                                                }
                                                boolean continueLoop2 = true;
                                                while (continueLoop2) {
                                                    logger.info("Enter 1 to back to search page ");
                                                    int choice2 = readIntegerFromUser(scanner);
                                                    switch (choice2) {
                                                        case 1: {
                                                            continueLoop2 = false;
                                                            break;
                                                        }
                                                        default: {
                                                            logger.info("Invalid choice");
                                                        }
                                                    }
                                                }


                                            }
                                            case 2: {
                                                logger.info("1. Enter name of event: ");
                                                eventName = scanner.next();
                                                logger.info("1. Enter location of event: ");
                                                eventLocation = scanner.next();
                                                resultEvents = Checker.checkNameAndLocationOfEvent(eventName, eventLocation);
                                                logger.info("------------------------");
                                                if (!resultEvents.equals(null)) {
                                                    printEventDetails(resultEvents);
                                                } else {
                                                    logger.info("No result :(");
                                                }
                                                boolean continueLoop2 = true;
                                                while (continueLoop2) {
                                                    logger.info("Enter 1 to back to search page ");
                                                    int choice2 = readIntegerFromUser(scanner);
                                                    switch (choice2) {
                                                        case 1: {
                                                            continueLoop2 = false;
                                                            break;
                                                        }
                                                        default: {
                                                            logger.info("Invalid choice");
                                                        }
                                                    }
                                                }

                                            }
                                            case 3: {
                                                logger.info("1. Enter name of event: ");
                                                eventName = scanner.next();
                                                logger.info("1. Enter minimum price of event: ");
                                                minPrice = readPrice(scanner);
                                                logger.info("1. Enter Maximum price of event: ");
                                                maxPrice = readPrice(scanner);
                                                resultEvents = Checker.checkNameAndPriceOfEvent(eventName, minPrice, maxPrice);
                                                logger.info("------------------------");
                                                if (!resultEvents.equals(null)) {
                                                    printEventDetails(resultEvents);
                                                } else {
                                                    logger.info("No result :(");
                                                }
                                                boolean continueLoop2 = true;
                                                while (continueLoop2) {
                                                    logger.info("Enter 1 to back to search page ");
                                                    int choice2 = readIntegerFromUser(scanner);
                                                    switch (choice2) {
                                                        case 1: {
                                                            continueLoop2 = false;
                                                            break;
                                                        }
                                                        default: {
                                                            logger.info("Invalid choice");
                                                        }
                                                    }
                                                }

                                            }
                                            case 4: {
                                                logger.info("1. Enter name of event: ");
                                                eventName = scanner.next();
                                                logger.info("1. Enter location of event: ");
                                                eventLocation = scanner.next();
                                                logger.info("1. Enter minimum price of event: ");
                                                minPrice = readPrice(scanner);
                                                logger.info("1. Enter Maximum price of event: ");
                                                maxPrice = readPrice(scanner);
                                                resultEvents = Checker.checkNameLocationAndPriceOfEvent(eventName, eventLocation, minPrice, maxPrice);
                                                logger.info("------------------------");
                                                if (!resultEvents.equals(null)) {
                                                    printEventDetails(resultEvents);
                                                } else {
                                                    logger.info("No result :(");
                                                }
                                                boolean continueLoop2 = true;
                                                while (continueLoop2) {
                                                    logger.info("Enter 1 to back to search page ");
                                                    int choice2 = readIntegerFromUser(scanner);
                                                    switch (choice2) {
                                                        case 1: {
                                                            continueLoop2 = false;
                                                            break;
                                                        }
                                                        default: {
                                                            logger.info("Invalid choice");
                                                        }
                                                    }
                                                }

                                            }
                                            case 5: {
                                                resultEvents = EventRepository.events;
                                                if (!resultEvents.equals(null)) {
                                                    printEventDetails(resultEvents);
                                                } else {
                                                    logger.info("No result :(");
                                                }
                                                boolean continueLoop2 = true;
                                                while (continueLoop2) {
                                                    logger.info("Enter 1 to back to search page ");
                                                    int choice2 = readIntegerFromUser(scanner);
                                                    switch (choice2) {
                                                        case 1: {
                                                            continueLoop2 = false;
                                                            break;
                                                        }
                                                        default: {
                                                            logger.info("Invalid choice");
                                                        }
                                                    }
                                                }

                                            }
                                            case 6: {
                                                continueLoop = false;
                                                break;
                                            }
                                            default: {
                                                logger.info("Invalid choice");
                                            }
                                        }
                                    }


                                    break;
                                }

                                case 3: {
                                    String eventId1;
                                    String location1;
                                    Event event;
                                    logger.info("\t*Now you can book an event*\t");

                                    while (true) {
                                        System.out.print("Enter the ID of the event you want to book: ");
                                        eventId1 = scanner.next();
                                        System.out.print("Enter the location of the event you want to book: ");
                                        location1 = scanner.next();
                                        event = BookingSystem.findEventByIdAndLocation(eventId1,location1);
                                        if(event==null){
                                            logger.info("The Event not found.");
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
                                        logger.info("Invalid date format. Please enter the date in the format yyyy-MM-dd'T'HH:mm:ss.");
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
                                        logger.info("Booking failed. Please try again.");
                                    }
                                    break;
                                }
                                case 4: {
                                    String userEmail = loggedInUser.getEmail();
                                    if (!loggedInUser.bookedEvent2.isEmpty()) {
                                        logger.info("Booked Events:");
                                        for (Event event : loggedInUser.bookedEvent2) {
                                            logger.info("Event Name: " + event.getNameOfEvent());
                                            logger.info("Event ID: " + event.getIdOfEvent());
                                            logger.info("Location: " + event.getPlaceOfEvent().getLocationOfPlace());
                                            logger.info("Event Start Time: " + event.getEventStartTime());
                                            logger.info("Event End Time: " + event.getEventEndTime());
                                            logger.info("------------------------------------");
                                        }
                                    } else {
                                        logger.info("You have not booked any events.");
                                    }
                                    break;
                                }
                                case 5: {
                                    logger.info("\nCancel booked events:");
                                    String IDC;
                                    boolean flagID;
                                    boolean Loop = true;
                                    while (Loop) {
                                        flagID = false;
                                        logger.info("1: Display booked events");
                                        logger.info("2: Enter the ID of event you want to cancel it");
                                        logger.info("3: Enter to back");
                                        int choice22 = readIntegerFromUser(scanner);
                                        switch (choice22) {
                                            case 1: {
                                                if (!loggedInUser.bookedEvent2.isEmpty()) {
                                                    logger.info("Booked Events:");
                                                    for (Event event : loggedInUser.bookedEvent2) {
                                                        logger.info("Event Name: " + event.getNameOfEvent());
                                                        logger.info("Event ID: " + event.getIdOfEvent());
                                                        logger.info("Location: " + event.getPlaceOfEvent().getLocationOfPlace());
                                                        logger.info("Event Start Time: " + event.getEventStartTime());
                                                        logger.info("Event End Time: " + event.getEventEndTime());
                                                        logger.info("------------------------------------");
                                                    }
                                                } else {
                                                    logger.info("You have not booked any events.");
                                                }
                                                break;
                                            }
                                            case 2: {
                                                System.out.print("Enter the ID of event you want to cancel it: ");
                                                IDC = scanner.next();
                                                for (Event event : loggedInUser.bookedEvent2) {
                                                    if (event.getIdOfEvent().equals(IDC)) {
                                                        flagID = true;
                                                        break;
                                                    }
                                                }
                                                if (flagID) {
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
                                                                logger.info("Event has been cancelled successfully.");
                                                                break;
                                                            }
                                                        }
                                                    } else {
                                                        logger.info("Cancellation aborted.");
                                                    }
                                                } else {
                                                    logger.info("Event with ID " + IDC + " not found in your booked events.");
                                                }
                                                break;
                                            }
                                            case 3: {
                                                logger.info("Returning to the previous menu...");
                                                Loop = false; // Exit the loop to go back to the previous menu
                                                break;
                                            }
                                            default: {
                                                logger.info("Invalid choice");
                                            }
                                        }
                                        // If choice 3 is selected, break out of the loop
                                        if (choice22 == 3) {
                                            break;
                                        }
                                    }
                                    break;
                                }

                                case 6: {

                                    logger.info("\t**\tNow you can Edit Your Profile\t**\t");
                                    logger.info("Choose one of the fields to modify:");
                                    logger.info("1-your First Name : ");
                                    logger.info("2-Your Last Nme :");
                                    logger.info("3-Your Email");
                                    logger.info("4-Your Password");
                                    int input5= scanner.nextInt();
                                    switch (input5) {
                                        case 1:
                                        {  logger.info("Your Current First Name: " + loggedInUser.getFirstName());
                                            String newFirstName;
                                            while (true){
                                                System.out.print("Enter New First Name: ");
                                                newFirstName = scanner.next().trim();
                                                if (newFirstName.isEmpty()||newFirstName.length()==1) {
                                                    logger.info("Invalid input! Please try again.");
                                                }
                                                else
                                                    loggedInUser.setFirstName(newFirstName);
                                                break;
                                            }
                                            logger.info("Updated successfully");
                                            logger.info(loggedInUser.getFirstName());


                                            break;}
                                        case 2:
                                        {
                                            logger.info("Your Current Last Name: " + loggedInUser.getLastName());
                                            while (true) {
                                                System.out.print("Enter New Last Name: ");
                                                String newLastName = scanner.next().trim();
                                                if (newLastName.isEmpty() || newLastName.length() == 1) {
                                                    logger.info(" Invalid input! Please try again.");
                                                } else
                                                    loggedInUser.setFirstName(newLastName);
                                                logger.info("Updated successfully");
                                                break;
                                            }
                                        }
                                        case 3:
                                        { logger.info("Your Current Email : " + loggedInUser.getEmail());
                                            boolean existEmail2=false;
                                            while (true) {
                                                System.out.print("Enter New email: ");
                                                String newEmail = scanner.next().trim();

                                                if (!userComponent.isValidEmail(newEmail)) {
                                                    logger.info("The email you entered is invalid. Please try again.");
                                                    continue;
                                                }

                                                for (User user : UserRepository.users) {
                                                    if (user.getEmail().equals(newEmail)) {
                                                        existEmail2 = true;
                                                        break;
                                                    }
                                                }
                                                if (existEmail2) {
                                                    logger.info("The email you entered is already exist. Please enter another one.");
                                                    existEmail2 = false;
                                                } else {

                                                    loggedInUser.setEmail(newEmail);
                                                    logger.info("Updated successfully");
                                                    break;
                                                }
                                            }
                                            break;}
                                        case 4:{
                                            System.out.print("Your Current Password: "+loggedInUser.getPassword());
                                            String newPassword ;
                                            while (true) {
                                                System.out.print("Enter new password: ");
                                                newPassword = scanner.next().trim();
                                                if (userComponent.isValidPassword(newPassword)) {
                                                    break;
                                                } else {
                                                    logger.info("The password should contain at least 8 characters including at least one uppercase letter, one lowercase letter, one digit, and one of the following symbols: !@#$%^");
                                                }
                                            }

                                            while (true) {
                                                System.out.print("Enter Confirm new password:");
                                                String confirmNewPassword = scanner.next();
                                                if (confirmNewPassword.equals(newPassword)) {
                                                    loggedInUser.setPassword(newPassword);
                                                    logger.info("Updated successfully");
                                                    break;
                                                } else {
                                                    logger.info("The confirmed password does not match the original password.");
                                                }
                                            }
                                            break;}


                                    }
                                    break;
                                }

                                case 7: {
                                    Boolean continueLoop1 = true;
                                    while (continueLoop1) {
                                        logger.info("Your Notifications:");
                                        logger.info("Select a number to view more details:");
                                        int i = 1;

                                        for (Notification n : loggedInUser.notifications) {
                                            logger.info(i + "- " + n.getMessage() + " at ( " + n.getSentDateTime() + " )");
                                            i++;
                                        }
                                        logger.info(i + "- Back to home page");
                                        int choice1 = readIntegerFromUser(scanner);
                                        if (choice1 < i && choice1 >= 1) {
                                            Notification n = loggedInUser.notifications.get(choice1 - 1);
                                            logger.info(n.showNtificationDetails());
                                            if (n.isApproved()) {
                                                logger.info("Enter your credit card Number:");
                                                String CardNumber = scanner.next();
                                                boolean Successfulpayment = BookingSystem.processPayment(CardNumber, n.getEvent(), loggedInUser);
                                                if (Successfulpayment) {
                                                    logger.info("Payment Successful .");

                                                }
                                                else
                                                    logger.info("Payment failed.");
                                            }

                                            boolean continueLoop2 = true;
                                            while (continueLoop2) {
                                                logger.info("Enter 1 to back to notification page ");
                                                int choice2 = readIntegerFromUser(scanner);
                                                switch (choice2) {
                                                    case 1: {
                                                        continueLoop2 = false;
                                                        break;
                                                    }
                                                    default: {
                                                        logger.info("Invalid choice");
                                                    }
                                                }
                                            }
                                        }
                                        else if (choice1 == i) {
                                            continueLoop1 = false;
                                        } else {
                                            logger.info("Invalid choice");
                                        }
                                    }
                                    break;
                                }

                                case 8: {
                                    logger.info("Enter the ID of the event that you want to Show the image:");
                                    String eventId = scanner.next();
                                    boolean eventFound = false;

                                    for (Event event : EventRepository.events) {
                                        if (event.getIdOfEvent().equals(eventId)) {
                                            eventFound = true;
                                            if (event.getpath() == null) {
                                                logger.info("The event does'nt have any images.");
                                            } else {
                                                ImageUploader.openImage(event.getpath());
                                            }
                                        }
                                    }

                                    if (!eventFound) {
                                        logger.info("Event not found.");
                                    }

                                    break;
                                }
                                case 9:{
                                    continueLoop3 = false;
                                    break;
                                }


                                default:
                                    logger.info("Invalid choice. Please try again.");
                            }
                        }
                    }
                }


            }

            signupSurvice = false;


        } while (true);
    }
}
