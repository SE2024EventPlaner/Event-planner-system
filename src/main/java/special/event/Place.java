package special.event;

import java.util.logging.Logger;

public class Place {

    private String nameOfPlace;
    private int capacityOfPlace;
    private String locationOfPlace;

    private static final Logger logger3 = Logger.getLogger(Place.class.getName());


    public Place(String nameOfPlace,int capacityOfPlace,String locationOfPlace)
    {
        this.nameOfPlace =nameOfPlace;
        this.capacityOfPlace=capacityOfPlace;
        this.locationOfPlace=locationOfPlace;

    }

    public String getNameOfPlace() {return nameOfPlace;}
    public void setNameOfPlace(String nameOfPlace){this.nameOfPlace=nameOfPlace;}

    public int getCapacityOfPlace(){return capacityOfPlace;}
    public void setCapacityOfPlace(int capacityOfPlace){this.capacityOfPlace=capacityOfPlace;}

    public String getLocationOfPlace(){return locationOfPlace;}
    public void setLocationOfPlace(String locationOfPlace){this.locationOfPlace=locationOfPlace;}

    public static boolean checkCapacityOfPlace( int capacity) {
        if (capacity <= 0) {
            logger3.info( "capacity of place must not be  zeros or negative value" );
            return false;
        }
        else
            return true;
    }





}
