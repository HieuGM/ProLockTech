package prolocktech.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import prolocktech.models.Message;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static prolocktech.utils.Utils.FILE_CHAT;

public class ChatService {
    private static final Gson gson = new Gson();
    private static List<Message> messages;

    static {
        try {
            messages = loadMessages();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Message> loadMessages() throws IOException {
        File file = new File(FILE_CHAT);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileReader reader = new FileReader(FILE_CHAT)) {
            return new Gson().fromJson(reader, new TypeToken<List<Message>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private static void saveMessages(List<Message> messages) {
        try (FileWriter writer = new FileWriter(FILE_CHAT)) {
            new Gson().toJson(messages, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addMessage(Message message) {
        messages.add(message);
        saveMessages(messages);
    }

    public static List<Message> getMessages(String user1, String user2) {
        List<Message> historyChat = new ArrayList<>();
        for (Message message : messages) {
            if ((message.getSender().equals(user1) && message.getReceiver().equals(user2)) || (message.getSender().equals(user2) && message.getReceiver().equals(user1))) {
                historyChat.add(message);
            }
        }
        return historyChat;
    }
}
