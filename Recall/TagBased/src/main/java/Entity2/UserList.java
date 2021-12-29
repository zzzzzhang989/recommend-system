package Entity2;

import java.util.HashMap;
import java.util.Map;

public class UserList {
    private static UserList instance = null;
    private final Map<Integer,  User> userList;

    private UserList() {
        userList = new HashMap<>();
    }

    public static UserList getInstance() {
        if (instance == null) {
            instance = new UserList();
        }
        return instance;
    }

    public Map<Integer,  User> getUserList() {
        return userList;
    }

    public void addUser(int id, Map<String, String> tagList){
        userList.put(id, new  User(id, tagList));
    }

    public void addUser(int id){
        userList.put(id, new User(id, new HashMap<>()));
    }

    public void addUser(User user){
        userList.put(user.getId(), user);
    }

    public User getUser(int id){
        return userList.get(id);
    }
}