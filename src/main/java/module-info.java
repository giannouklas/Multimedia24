module org.example.multimedia24 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.example.multimedia24 to javafx.fxml;
    exports org.example.multimedia24;
    exports org.example.multimedia24.Models;
    opens org.example.multimedia24.Models to javafx.fxml;
    exports org.example.multimedia24.DialogControllers to javafx.fxml;

    // Add this line to open your package to javafx.fxml module
    opens org.example.multimedia24.DialogControllers to javafx.fxml;
}