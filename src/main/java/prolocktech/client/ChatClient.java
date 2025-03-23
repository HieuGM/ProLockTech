package prolocktech.client;

import javafx.application.Platform;
import prolocktech.controllers.ChatController;

import java.io.*;
import java.net.Socket;

public class ChatClient {
    private Socket socket;
    private BufferedReader input;
    private BufferedWriter output;
    private String username;
    private ChatController chatController;

    public ChatClient(String serverAddress, int port, String username, ChatController chatController) throws IOException {
        this.socket = new Socket(serverAddress, port);
        System.out.println("Connected to server at " + serverAddress + ":" + port);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.username = username;
        this.chatController = chatController;

        // Gui user den server
        output.write(username);
        output.newLine();
        output.flush();

        // Nhan tin nhan tu server
        new Thread(this::receiveMessages).start();

//        sendMessage("CONNECT: " + username);
    }

    public ChatClient() {

    }

    private void receiveMessages() {
        String message;
        try {
            while ((message = input.readLine()) != null) {
                System.out.println("Received: " + message);
                String finalMessage = message;
                Platform.runLater(() -> chatController.receiveMessage(finalMessage));
            }
        } catch (IOException e) {
            closeConnection();
        }
    }

    public void sendMessage(String recipient, String message) throws IOException {
        try {
            output.write(username + ":" + recipient + ":" + message);
            output.newLine();
            output.flush();
        } catch (IOException e) {
            closeConnection();
        }
    }

    private void closeConnection() {
        try {
//            sendMessage(("DISCONNECT: " + username));
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
