package prolocktech.services;

import prolocktech.models.User;

public class AuthService {
    private static User currentUser;

    public void setCurrentUser(User user) {
        currentUser = user;
    }
    public User getCurrentUser() {
        return currentUser;
    }
}
