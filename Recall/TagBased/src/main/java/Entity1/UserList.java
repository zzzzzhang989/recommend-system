package Entity1;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private static UserList instance = null;
    private final List<User> userList;

    private UserList() {
        userList = new ArrayList<>();
    }

    public static UserList getInstance() {
        if (instance == null) {
            instance = new UserList();
        }
        return instance;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void addUser(int id, List<String> tagList){
        userList.add(new User(id, tagList));
    }
}