package prolocktech.models;

import java.util.ArrayList;
import java.util.List;

public class Img {
    private String fileName;
    private String base64data;
    String uploadBy;
    String uploadDate;
    private List<String> emojis = new ArrayList<String>();

    public Img(String fileName, String base64data, String uploadBy, String uploadDate) {
        this.fileName = fileName;
        this.base64data = base64data;
        this.uploadBy = uploadBy;
        this.uploadDate = uploadDate;
    }

    public void addEmoji(String emoji) {
        emojis.add(emoji);
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
