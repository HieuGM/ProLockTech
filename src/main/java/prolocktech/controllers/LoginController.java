package prolocktech.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import prolocktech.models.User;
import prolocktech.services.UserService;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label forgotpass;

    @FXML
    private Button login;

    @FXML
    private Label noti;

    @FXML
    private Label signup;

    @FXML
    private TextField username;

    @FXML
    private PasswordField userpassword;

    private Stage stage;

    private UserService userService = new UserService();

    // khoi tao screen
    public void init(Stage stage) {
        this.stage = stage;
        noti.setVisible(false);
        login.setOnAction(e -> {
            try {
                loginAction();
            } catch (Exception e1) {
                throw new RuntimeException();
            }
        });
        signup.setOnMouseClicked(e -> {
            try {
                signupAction();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    // login
    private void loginAction() throws IOException {
        String user = username.getText();
        String pass = userpassword.getText();
        User userA = userService.findUsername(user);
        if (userA != null && pass.equals(userA.getPassword())) {
            FXMLLoader fxmlLoader = new FXMLLoader(HomeController.class.getResource("/views/home-screen.fxml"));
            Parent root = fxmlLoader.load();
            HomeController controller = fxmlLoader.getController();
            controller.init(stage);
            stage.getScene().setRoot(root);
            return;
        }
        noti.setVisible(true);
    }
    // chuyen man hinh dang ky
    private void signupAction() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/views/signup-screen.fxml"));
        Parent root = fxmlLoader.load();
        SignupController controller = fxmlLoader.getController();
        controller.init(stage);
        stage.getScene().setRoot(root);
    }
}
