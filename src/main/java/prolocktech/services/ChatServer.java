package prolocktech.services;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChatServer {
    private static final int PORT = 12345;
    private static Map<String, ClientHandler> clients = new HashMap<>();
    public static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        try  {
            if (serverSocket == null) {
                serverSocket = new ServerSocket(PORT);
                System.out.println("Server is running on port " + PORT);
            } else {}

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
//                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void broadcast(String message, String sender, String recipient) {
//        System.out.println("Broadcasting: " + message + " to " + clients.size() + " clients");
        ClientHandler receiver = clients.get(recipient);
        if (receiver != null) {
            receiver.sendMessage(sender + ": " + message);
        } else {
            System.out.println("Client " + recipient + " not found");
        }
    }

    public static void removeClient(ClientHandler client) {
        clients.remove(client);
    }

     static class ClientHandler implements Runnable {

        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                username = in.readLine();
                clients.put(username, this);
                String message;
                while ((message = in.readLine())!= null) {
                    String[] parts = message.split(":", 3);
                    if (parts.length == 3) {
                        String sender = parts[0].trim();
                        String recipient = parts[1].trim();
                        String content = parts[2].trim();
                        broadcast(content, sender, recipient);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeConnection();
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        private void closeConnection() {
            try {
                socket.close();
                ChatServer.removeClient(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
