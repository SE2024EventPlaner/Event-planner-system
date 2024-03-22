package repositories;

import special.event.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public static List<User> users = new ArrayList<>();
    public UserRepository(){
        users.add(new User("hello@gmail.com", "123123", "USER","ali","ahmad"));
        users.add(new User("hello2@gmail.com", "123456", "ADMIN","mohamad","nasser"));
        users.add(new User("hello3@gmail.com", "123789", "SERVICE_PROVIDER","saly","mohammad"));
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