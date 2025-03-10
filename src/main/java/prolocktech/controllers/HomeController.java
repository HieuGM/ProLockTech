package prolocktech.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import prolocktech.models.Img;
import prolocktech.services.ImageService;

import java.io.IOException;
import java.util.List;

public class HomeController {

    @FXML
    private ImageView img;

    @FXML
    private ImageView message;

    @FXML
    private ImageView profile;

    @FXML
    private Button up;

    @FXML
    private Button down;

    @FXML
    private Button upload;

    private Stage stage;

    private List<Img> images = ImageService.loadImages();

    private int current_index = -1;

    public HomeController() throws IOException {

    }

    public void init(Stage stage) {
        this.stage = stage;
        if (!images.isEmpty()) {
            current_index = 0;
            updateImageView();
        }
        upload.setOnAction(e -> {
            try {
                UploadImage(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        up.setOnAction(e -> showPrevImage());
        down.setOnAction(e -> showNextImage());
    }

    // chuyen sang man hinh upload
    private void UploadImage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(UploadController.class.getResource("/views/upload-screen.fxml"));
        Parent root = loader.load();
        UploadController controller = loader.getController();
        controller.init(stage);
        stage.getScene().setRoot(root);
    }
    // update anh
    private void updateImageView() {
        if (current_index >= 0 && images.size() > current_index) {
            Image image = ImageService.decodeBase64ToImage(images.get(current_index).getBase64data());
            img.setImage(image);
        }
    }
    // show anh truoc do
    private void showPrevImage() {
        if (current_index > 0) {
            --current_index;
            updateImageView();
        }
    }
    // show anh sau
    private void showNextImage() {
        if (current_index < images.size() - 1) {
            ++current_index;
            updateImageView();
        }
    }
}
