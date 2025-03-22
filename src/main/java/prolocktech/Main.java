package prolocktech;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import prolocktech.controllers.LoginController;
import prolocktech.services.ChatServer;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;

public class Main extends Application {

    private static final int SERVER_PORT = 12345;

    @Override
    public void start(Stage stage) throws IOException {
        if (!isPortInUse(SERVER_PORT)) {
            new Thread(() -> {
                try {
                    ChatServer.main(new String[]{});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Font.loadFont(getClass().getResourceAsStream("/fonts/SF-Pro-Rounded.ttf"), 20);
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/views/login-screen.fxml"));
        Parent root = fxmlLoader.load();
        LoginController controller = fxmlLoader.getController();
        controller.init(stage);
        Scene scene = new Scene(root, 750, 500);
        stage.setScene(scene);
        stage.show();
    }
    private boolean isPortInUse(int port) {
        try (Socket socket = new Socket("localhost", port)) {
            return true; // Cổng đã được sử dụng (server đang chạy)
        } catch (IOException e) {
            return false; // Cổng chưa sử dụng -> Có thể khởi động server
        }
    }

    public static void main(String[] args) {
        launch();
    }
}