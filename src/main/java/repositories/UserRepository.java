package repositories;

import special.event.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserRepository {

    public static List<User> users = new ArrayList<>();

    public static final String FILE_NAME1 = "userfile.txt";
    private static final Logger logger3 = Logger.getLogger(EventRepository.class.getName());


    public UserRepository( ){


    }

    public  void readUsers(String fileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String userEmail = parts[0].trim();
                    String userPassword = parts[1].trim();
                    String userType = parts[2].trim();
                    String firstName = parts[3].trim();
                    String lastName = parts[4].trim();
                    users.add(new User(userEmail,userPassword,userType,firstName,lastName));
                } else {
                    logger3.info("Invalid format in line: " + line);
                }

            }
        } catch (IOException e) {
            logger3.info("Error reading file: " + e.getMessage());
        }


    }
    public static void addToUsers(User user1)
    {
        users.add(user1);
    }
    public static User getfromuser(int i)
    {
        return users.get(i);

    }

    public static List<User> reviw = new ArrayList<>();
    public static void addToReviw(User service)
    {
        reviw.add(service);
    }














}