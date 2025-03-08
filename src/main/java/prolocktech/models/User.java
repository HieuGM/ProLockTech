package prolocktech.models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static prolocktech.utils.Utils.FILE_PATH;

public class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
