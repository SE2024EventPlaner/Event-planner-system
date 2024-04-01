package repositories;
import components.EventComponent;
import special.event.Event;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EventRepository {
    private static final Logger logger2 = Logger.getLogger(EventRepository.class.getName());

    public static List<Event> events = new ArrayList<>();


    public static final String FILE_NAME = "eventfille.txt";
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

                    LocalDateTime startTime2 = EventComponent.dateConverter(startTime1);
                    LocalDateTime endTime2 = EventComponent.dateConverter(endTime1);


                    events.add(new Event(eventName,eventId,eventCost,constructionCostOfEvent,startTime2,endTime2,eventPlace,capacityPlace,eventLocation,ownerEmail,ownerPassword));
                } else {
                    logger2.info("Invalid format in line: " + line);
                }

            }
        } catch (IOException e) {
            logger2.info("Error reading file: " + e.getMessage());
        }


    }


}