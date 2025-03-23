package prolocktech.utils;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Utils {
    public static final Color TEXT_COLOR = Color.web("#F8F5F2");
    public static final Color BACKGROUND_COLOR = Color.web("#B69E92");
    public static final int W_Image = 588;
    public static final int H_Image = 359;
    public static final String FILE_PATH = "data/users.json";
    public static final String FILE_IMAGE = "data/images.json";
    public static final String FILE_CHAT = "data/messages.json";
    public static final Image[] EMOJIS = {
            new Image(Objects.requireNonNull(Utils.class.getResourceAsStream("/emojis/like.png"))),
            new Image(Objects.requireNonNull(Utils.class.getResourceAsStream("/emojis/heart.png"))),
            new Image(Objects.requireNonNull(Utils.class.getResourceAsStream("/emojis/fires.png"))),
            new Image(Objects.requireNonNull(Utils.class.getResourceAsStream("/emojis/angry.png"))),
            new Image(Objects.requireNonNull(Utils.class.getResourceAsStream("/emojis/cry.png"))),
            new Image(Objects.requireNonNull(Utils.class.getResourceAsStream("/emojis/laugh.png"))),
    };
    public static final String[] EMOJIS_URL = {
            "/emojis/like.png",
            "/emojis/heart.png",
            "/emojis/fires.png",
            "/emojis/angry.png",
            "/emojis/cry.png",
            "/emojis/laugh.png"
    };
}
