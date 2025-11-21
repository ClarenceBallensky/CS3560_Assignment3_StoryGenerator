module edu.cpp.cs3560_assignment3_storygenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires google.genai;

    opens model to javafx.graphics, javafx.fxml;
    exports model;

    //opens edu.cpp.view to javafx.fxml;
}