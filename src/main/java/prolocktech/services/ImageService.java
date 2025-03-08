package prolocktech.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.image.Image;
import prolocktech.models.Img;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static prolocktech.utils.Utils.*;

public class ImageService {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static List<Img> images;

    static {
        try {
            images = loadImages();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Img> getImages() {
        return images;
    }

    public static void saveImage(List<Img> images) {
        try (FileWriter writer = new FileWriter(FILE_IMAGE)) {
            gson.toJson(images, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Img> loadImages() throws IOException {
        try (Reader reader = new FileReader(FILE_IMAGE)) {
            Img[] images = gson.fromJson(reader, Img[].class);
            return (images != null) ? new ArrayList<>(Arrays.asList(images)) : new ArrayList<>();
        }
        catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void addImage(Img image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(image);
        saveImage(images);
    }

    public static Image decodeBase64ToImage(String base64) {
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        return new Image(new ByteArrayInputStream(imageBytes));
    }
}
