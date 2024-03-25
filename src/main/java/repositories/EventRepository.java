package repositories;
import com.thoughtworks.qdox.model.expression.Constant;
import org.apache.maven.surefire.api.booter.Constants;
import special.event.Event;
import special.event.User;

import java.security.Provider;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class EventRepository {
    public static List<Event> events = new ArrayList<>();


    public EventRepository(){


        events.add(new Event( "Birthday","101010",1000, LocalDateTime.of(2024,3,29,6,00,00),LocalDateTime.of(2024,3,29,8,00,00),"Farah hall for events",120,"Nablus","samyahamed22@gmail.com","8900&sm80" ));
        events.add(new Event( "Marriage","202020",2900,LocalDateTime.of(2024,3,20,12,00,00),LocalDateTime.of(2024,3,20,14,00,00),"AL-masa hall for events",200,"Jenin","samyahamed22@gmail.com","8900&sm80"));
        events.add(new Event("Graduation","303030",1800,LocalDateTime.of(2024,4,5,10,00,00),LocalDateTime.of(2024,4,5,12,00,00),"five stars hall for events",130,"Nablus","samyahamed22@gmail.com","8900&sm80"));
        events.add(new Event("gender determination","404040",1000,LocalDateTime.of(2024,5,9,13,00,00),LocalDateTime.of(2024,5,9,15,00,00),"Golden Palace Hall",130,"Tulkarm","samyahamed22@gmail.com","8900&sm80"));
        events.add(new Event("Official","505050",1600,LocalDateTime.of(2024,5,5,15,00,00),LocalDateTime.of(2024,5,5,17,00,00),"Grand Park Hotel",130,"Ramallah","samyahamed22@gmail.com","8900&sm80"));
    }
}