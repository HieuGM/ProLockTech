package prolocktech.models;

public class Img {
    private String fileName;
    private String base64data;
    String uploadBy;
    String uploadDate;

    public Img(String fileName, String base64data, String uploadBy, String uploadDate) {
        this.fileName = fileName;
        this.base64data = base64data;
        this.uploadBy = uploadBy;
        this.uploadDate = uploadDate;
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
