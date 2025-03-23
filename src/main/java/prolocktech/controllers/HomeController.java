package prolocktech.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import prolocktech.models.Img;
import prolocktech.models.User;
import prolocktech.services.AuthService;
import prolocktech.services.ImageService;
import prolocktech.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static prolocktech.utils.Utils.EMOJIS;
import static prolocktech.utils.Utils.EMOJIS_URL;

public class HomeController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private ImageView img;

    @FXML
    private ImageView message;

    @FXML
    private Button send;

    @FXML
    private ImageView profile;

    @FXML
    private TextField rep;

    @FXML
    private Button up;

    @FXML
    private Button down;

    @FXML
    private Button upload;

    @FXML
    private Label author;

    @FXML
    private Label date;

    @FXML
    private Button signout;

    @FXML
    private Label emojiLabel;

    @FXML
    private VBox emojiContainer;

    private Stage stage;

    private List<Img> images = ImageService.loadImages();

    private AuthService authService = new AuthService();

    private int current_index = -1;

    public HomeController() throws IOException {

    }

    public void init(Stage stage) {
        this.stage = stage;

        img.toFront();
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
        message.setOnMouseClicked(e -> {
            try {
                showChatScreen();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        send.setOnAction(e -> {
            try {
                reply();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        signout.setOnAction(e -> {
            try {
                signOut();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        img.setPickOnBounds(true);
        img.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                try {
                    showEmojiMenu();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
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
            Img current_img = images.get(current_index);
            Image image = ImageService.decodeBase64ToImage(images.get(current_index).getBase64data());
            img.setImage(image);
            author.setText("Author: " + current_img.getUploadBy());
            date.setText("Time: " + current_img.getUploadDate());
            emojiContainer.getChildren().clear();
            for (Image emoji : current_img.getEmojisImage()) {
                ImageView emojiView = new ImageView(emoji);
                emojiView.setFitHeight(20);
                emojiView.setFitWidth(20);
                emojiContainer.getChildren().add(emojiView);
            }

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

    private void showChatScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(ChatController.class.getResource("/views/chat-screen.fxml"));
        Parent root = loader.load();
        ChatController controller = loader.getController();
        controller.init(stage);
        stage.getScene().setRoot(root);
    }
    private void reply() throws IOException {
        String text = rep.getText();
        Img current_img = images.get(current_index);
        replyImage(text, current_img.getUploadBy());
    }
    private void replyImage(String mes, String recipient) throws IOException {
        showChatScreen();
        ChatController.replyImage(mes, recipient);
    }

    private void signOut() throws IOException {
        authService.signOut();
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/login-screen.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.init(stage);
        stage.getScene().setRoot(root);
    }

    private void showEmojiMenu() throws IOException {
        ContextMenu emojiMenu = new ContextMenu();
        for (String emojiURL : EMOJIS_URL) {
            MenuItem item = new MenuItem();
            Image emojiImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(emojiURL)));
            ImageView emojiView = new ImageView(emojiImage);
            emojiView.setFitWidth(20);
            emojiView.setFitHeight(20);
            item.setGraphic(emojiView);
            item.setOnAction(e -> {
                try {
                    addEmojiToImage(emojiURL);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            emojiMenu.getItems().add(item);
        }
        emojiMenu.show(img, Side.BOTTOM, 0, 0);
    }

    private void addEmojiToImage(String emoji) throws IOException {
        if (current_index >= 0 && current_index < images.size()) {
            Img current_img = images.get(current_index);
            current_img.addEmoji(emoji);
            ImageService.saveImage(images);
            updateImageView();
        }
    }
}
