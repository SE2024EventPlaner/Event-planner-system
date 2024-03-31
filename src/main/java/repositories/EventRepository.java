package repositories;
import com.thoughtworks.qdox.model.expression.Constant;
import components.EventComponent;
import org.apache.maven.surefire.api.booter.Constants;
import special.event.Event;
import special.event.User;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Provider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class EventRepository {
    public static List<Event> events = new ArrayList<>();


 //   public static Path filePath = Paths.get("Event-planner-system/src/main/resources/EventFile.txt");
    
    public static String fileOfEvent = "eventfille.txt";// filePath.toAbsolutePath().toString();
    public EventRepository(){

    }
    public  void readEventFile(String fileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 11) {
                    String eventName = parts[0].trim();
                    String eventId = parts[1].trim();
                    float eventCost =Float.parseFloat( parts[2].trim());
                    String startTime1 = parts[3].trim();
                    String endTime1 = parts[4].trim();
                    String eventPlace= parts[5].trim();
                    int capacityPlace= Integer.parseInt( parts[6].trim());
                    String eventLocation= parts[7].trim();
                    String ownerEmail =parts[8].trim();
                    String ownerPassword =parts[9].trim();
                    float constructionCostOfEvent =Float.parseFloat( parts[10].trim());

                    LocalDateTime startTime2 = EventComponent.dateConverter(startTime1);// LocalDateTime.parse(startTime1, formatter);
                    LocalDateTime endTime2 = EventComponent.dateConverter(endTime1);//LocalDateTime.parse(endTime1, formatter);


                    events.add(new Event(eventName,eventId,eventCost,constructionCostOfEvent,startTime2,endTime2,eventPlace,capacityPlace,eventLocation,ownerEmail,ownerPassword));
                } else {
                    System.err.println("Invalid format in line: " + line);
                }

            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }


    }


    public static void appendEvent( String fileName,String eventName , String eventId ,float eventCost , LocalDateTime eventStartTime ,LocalDateTime eventEndTime ,String eventPlace,int capacityPlace ,String eventLocation ,String ownerEmail,String ownerPassword ) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String cost = String.valueOf(eventCost);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String date1 = eventStartTime.format(formatter);
            String date2 = eventEndTime.format(formatter);

            String capacity =String.valueOf(capacityPlace);


            writer.newLine();  // Add a newline after each entry
            writer.write(eventName);
            writer.write(",");
            writer.write(eventId);
            writer.write(",");
            writer.write(cost);
            writer.write(",");
            writer.write(date1);
            writer.write(",");
            writer.write(date2);
            writer.write(",");
            writer.write(eventPlace);
            writer.write(",");
            writer.write(capacity);
            writer.write(",");
            writer.write(eventLocation);
            writer.write(",");
            writer.write(ownerEmail);
            writer.write(",");
            writer.write(ownerPassword);
        } catch (IOException e) {
            System.err.println("Error: Could not append data to " + fileName);
            e.printStackTrace();
        }
    }


}
