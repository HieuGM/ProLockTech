package prolocktech.services;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import prolocktech.models.User;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static prolocktech.utils.Utils.FILE_PATH;

public class UserService {

    private static List<User> users;

    static {
        try {
            users = loadUsers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getUsers() {
        return users;
    }
    // Luu danh sach nguoi dung
    private static void saveUsers(List<User> users) throws IOException {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            new Gson().toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Doc danh sach nguoi dung tu file Json
    private static List<User> loadUsers() throws IOException {
        File file = new File(FILE_PATH);

        if (!file.exists() || file.length() == 0) {
            List<User> users = new ArrayList<>();
            users.add(new User("admin", "1"));
            saveUsers(users);
            return users;
        }

        try (FileReader reader = new FileReader(file)) {
            return new Gson().fromJson(reader, new TypeToken<List<User>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public static void addUser(User user) throws IOException {
        users.add(user);
        saveUsers(users);
    }

    public User findUsername(String username) throws IOException {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

}
