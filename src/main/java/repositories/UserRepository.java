package repositories;

import special.event.User;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public static List<User> users = new ArrayList<>();

    static Path filePath = Paths.get("C:/Users/Dell/software_project/EventPlaner9911/src/main/resources/UserFile.txt");
    public static String fileOfUser = filePath.toAbsolutePath().toString();

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
                    System.err.println("Invalid format in line: " + line);
                }

            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
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


    public static void appendUser(String fileName, String Email ,String password , String Type ,String firstName ,String lastName ) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.newLine();  // Add a newline after each entry
            writer.write(Email);
            writer.write(",");
            writer.write(password);
            writer.write(",");
            writer.write(Type);
            writer.write(",");
            writer.write(firstName);
            writer.write(",");
            writer.write(lastName);
        } catch (IOException e) {
            System.err.println("Error: Could not append data to " + fileName);
            e.printStackTrace();
        }
    }











}