package prolocktech.models;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Img {
    private String fileName;
    private String base64data;
    String uploadBy;
    String uploadDate;
    private List<String> emojis;

    public Img(String fileName, String base64data, String uploadBy, String uploadDate, List<String> emojis) {
        this.fileName = fileName;
        this.base64data = base64data;
        this.uploadBy = uploadBy;
        this.uploadDate = uploadDate;
        this.emojis = emojis != null ? emojis : new ArrayList<>();
    }

    public void addEmoji(String emoji) {
        emojis.add(emoji);
    }
    public List<Image> getEmojisImage() {
        List<Image> images = new ArrayList<>();
        for (String url : emojis) {
            URL resource = getClass().getResource(url);
            if (resource != null) {
                images.add(new Image(resource.toString()));
            } else {
                System.out.println("Hãy tải emoji: " + url);
            }
        }
        return images;
    }

    public List<String> getEmojis() {
        return emojis;
    }

    public String getUploadBy() {
        return uploadBy;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public String getBase64data() {
        return base64data;
    }
}
