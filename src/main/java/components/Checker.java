package components;

import repositories.EventRepository;
import special.event.Event;

import java.util.ArrayList;
import java.util.List;

public class Checker {
    public static List<Event> resultEvents = new ArrayList<>();


    public static List<Event> checkNameOfEvent(String searchName) {
        resultEvents.clear();
        String nameOfEvent;
        for (Event event : EventRepository.events) {
            nameOfEvent = event.getNameOfEvent();
            if (nameOfEvent.contains(searchName)) {
                resultEvents.add(event);
            }
        }

        if(!resultEvents.isEmpty())
            return resultEvents;
        else
            return null;

    }
    public static List<Event> checkNameAndLocationOfEvent(String searchName,String searchLocation) {
        resultEvents.clear();
        String nameOfEvent;
        String locationOfEvent;
        for (Event event : EventRepository.events) {
            nameOfEvent = event.getNameOfEvent();
            locationOfEvent = event.getEventLocation();

            if ((nameOfEvent.contains(searchName))
                    &&(locationOfEvent.contains(searchLocation))) {

                resultEvents.add(event);
            }
        }
        if(!resultEvents.isEmpty())
            return resultEvents;
        else
            return null;

    }
    public static List<Event> checkNameAndPriceOfEvent(String searchName,String minPrice,String maxPrice) {
        resultEvents.clear();
        String nameOfEvent;
        float costOfEvent;
        float minPriceOfEvent = Float.parseFloat(minPrice);
        float maxPriceOfEvent = Float.parseFloat(maxPrice);

        for (Event event : EventRepository.events) {
            nameOfEvent = event.getNameOfEvent();
            costOfEvent = event.getCostOfEvent();

            if ( (nameOfEvent.contains(searchName) )
                    &&(minPriceOfEvent <= costOfEvent)
                      &&(costOfEvent <= maxPriceOfEvent) ) {

                resultEvents.add(event);
            }
        }
        if(!resultEvents.isEmpty())
            return resultEvents;
        else
            return null;

    }
    public static List<Event> checkNameLocationAndPriceOfEvent(String searchName,String searchLocation,String minPrice,String maxPrice) {
        resultEvents.clear();
        String nameOfEvent;
        String locationOfEvent;
        float costOfEvent;
        float minPriceOfEvent = Float.parseFloat(minPrice);
        float maxPriceOfEvent = Float.parseFloat(maxPrice);

        for (Event event : EventRepository.events) {
            nameOfEvent = event.getNameOfEvent();
            costOfEvent = event.getCostOfEvent();
            locationOfEvent = event.getEventLocation();
            if ( (nameOfEvent.contains(searchName) )
                    &&(minPriceOfEvent <= costOfEvent)
                    &&(costOfEvent <= maxPriceOfEvent)
                    &&(locationOfEvent.contains(searchLocation))) {

                resultEvents.add(event);
            }
        }
        if(!resultEvents.isEmpty())
            return resultEvents;
        else
            return null;

    }

}
