package special.event;

public class Place {

    String nameOfPlace;
    int capacityOfPlace;
    String locationOfPlace;


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
        if (capacity == 0 || capacity < 0) {
            System.out.println( "capacity of place must not be  zeros or negative value" );
            return false;
        }
        else
            return true;
    }





}