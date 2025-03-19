package prolocktech.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import prolocktech.models.Img;
import prolocktech.services.AuthService;
import prolocktech.services.ImageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import static prolocktech.utils.Utils.H_Image;
import static prolocktech.utils.Utils.W_Image;

public class UploadController {

    @FXML
    private ImageView image;

    @FXML
    private Button upload;

    @FXML
    private Button chooseFile;

    @FXML
    private Button backtohome;

    private AuthService authService = new AuthService();

    private Stage stage;

    private File fileImg;

    public void init(Stage stage) {
        this.stage = stage;
        chooseFile.setOnAction(e -> ChooseImage(stage));
        upload.setOnAction(e -> {
            try {
                UploadImage(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        backtohome.setOnAction(e -> {
            try {
                BackHome(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    // chon anh
    private File fileChooserEvent(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        return fileChooser.showOpenDialog(stage);
    }
    // Hien thi file anh
    public void ChooseImage(Stage stage) {
        File file = fileChooserEvent(stage);
        if (file == null) {
            return;
        }
        fileImg = file;
        Image imageView = new Image(file.toURI().toString(), W_Image, H_Image, false, true);
        image.setImage(imageView);
    }
    // upload anh len
    public void UploadImage(Stage stage) throws IOException {
        try {
            byte[] fileContent = Files.readAllBytes(fileImg.toPath());
            String base64Image = Base64.getEncoder().encodeToString(fileContent);
            ImageService.addImage(new Img(fileImg.getName(), base64Image, authService.getCurrentUser().getUsername(), getCurrentTime()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Tải ảnh lên thành công!");
            alert.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
    // chuyen ve man hinh chinh
    public void BackHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeController.class.getResource("/views/home-screen.fxml"));
        Parent root = fxmlLoader.load();
        HomeController controller = fxmlLoader.getController();
        controller.init(stage);
        stage.getScene().setRoot(root);
    }
}
