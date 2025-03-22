module prolocktech {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens prolocktech.models to com.google.gson;
    opens prolocktech to javafx.graphics;
    opens prolocktech.controllers to javafx.fxml;
    exports prolocktech;
    exports prolocktech.controllers;
    opens prolocktech.client to com.google.gson;
}