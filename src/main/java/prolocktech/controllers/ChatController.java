package prolocktech.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import prolocktech.models.Message;
import prolocktech.models.User;
import prolocktech.services.AuthService;
import prolocktech.services.ChatService;
import prolocktech.services.UserService;

import java.io.IOException;
import java.util.List;

public class ChatController {

    private static ChatController instance;
    @FXML
    private Label chatwith;

    @FXML
    private VBox message;

    @FXML
    private Button send;

    @FXML
    private Stage stage;

    @FXML
    private ListView<String> list;

    @FXML
    private TextField input;

    @FXML
    private Button back;

    private UserService userService = new UserService();

    private AuthService authService = new AuthService();

    private String currentFriend;

    String user;

    public ChatController() {
        instance = this;
    }
    public void init(Stage stage) {
        this.user = authService.getCurrentUser() != null ? authService.getCurrentUser().getUsername() : "Unknown";
        loadFriends();
        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !list.getItems().isEmpty()) {
                openChatWith(newValue);
            }
        });

        send.setOnAction(e -> sendMessage());
        back.setOnAction(e -> {
            try {
                backToHome(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void loadFriends() {
        List<User> users = userService.getUsers();
        List<String> friends = users.stream().map(User::getUsername).toList();
        list.getItems().setAll(friends);
    }

    private void openChatWith(String friend) {
        currentFriend = friend;
        chatwith.setText("Chat With: " + friend);
        message.getChildren().clear();
        loadChatHistory();
    }

    private void sendMessage() {
        if (currentFriend != null && !input.getText().isEmpty()) {
            ChatService.addMessage(new Message(user, currentFriend, input.getText()));
            Label newMessage = new Label(user + ": " + input.getText());
            message.getChildren().add(newMessage);
            input.clear();
        }
    }

    private void loadChatHistory() {
        List<Message> chatHistory = ChatService.getMessages(currentFriend, user);
        message.getChildren().clear();
        for (Message m : chatHistory) {
            message.getChildren().add(new Label(m.getSender() + ": " + m.getContent()));
        }
    }
    public static void replyImage(String mes, String recipient) {
        if (instance != null) {
            instance.receiveReply(mes, recipient);
        }
    }

    private void receiveReply(String mes, String recipient) {
        currentFriend = recipient;
        chatwith.setText("Chat With: " + currentFriend);
        loadChatHistory();
        ChatService.addMessage(new Message(user, currentFriend, mes));
        Label newMessage = new Label(user + ": " + mes);
        message.getChildren().add(newMessage);
    }

    private void backToHome(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("/views/home-screen.fxml"));
        Parent root = loader.load();
        HomeController controller = loader.getController();
        controller.init(stage);
        stage.getScene().setRoot(root);
    }
}
