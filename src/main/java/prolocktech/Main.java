package prolocktech;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import prolocktech.controllers.LoginController;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/views/login-screen.fxml"));
        Parent root = fxmlLoader.load();
        LoginController controller = fxmlLoader.getController();
        controller.init(stage);
        Scene scene = new Scene(root, 750, 500);
        stage.setScene(scene);
        stage.show();
//        URL fxmlUrl = getClass().getResource("main.fxml");
////        System.out.println("FXML URL: " + fxmlUrl);
////        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/login-screen.fxml"));
////        System.out.println("FXML URL: " + getClass().getResource("/views/login-screen.fxml"));
////        Parent root = fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}