import java.util.ArrayList;

public class UserService {
    private ArrayList<User> userList = new ArrayList<>();

    public boolean register(String username, String password) {
        for (User user : userList) {
            if (username.equals(user.getUsername())) {
                return false;
            }
        }
        userList.add(new User(username, password));
        return true;
    }

    public boolean login(String username, String password) {
        for (User user : userList) {
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
