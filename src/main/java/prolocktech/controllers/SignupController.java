package prolocktech.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import prolocktech.models.User;
import prolocktech.services.UserService;

import java.io.IOException;
import java.util.List;

public class SignupController {

    @FXML
    private Button create;

    @FXML
    private TextField email;

    @FXML
    private Label login;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private MenuButton language;

    private Stage stage;

    public void init(Stage stage) {
        this.stage = stage;
        create.setOnAction(event -> {
            try {
                createAccount();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        login.setOnMouseClicked(event -> {
            try {
                backToLogin(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void createAccount() throws IOException {
        String user = username.getText();
        String pass = password.getText();
        User userA = new User(user, pass);
        UserService.addUser(userA);
        backToLogin(stage);
    }

    public void backToLogin(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/views/login-screen.fxml"));
        Parent root = fxmlLoader.load();
        LoginController controller = fxmlLoader.getController();
        controller.init(stage);
        stage.getScene().setRoot(root);
    }
}
