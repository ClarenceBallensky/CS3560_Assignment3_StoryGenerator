module edu.cpp.cs3560_assignment3_storygenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires google.genai;
    requires com.google.common;
    requires io.github.bucket4j.core;

    opens model to javafx.graphics, javafx.fxml;
    exports model;
    exports controller;
    opens controller to javafx.fxml, javafx.graphics;
    exports main;
    opens main to javafx.fxml, javafx.graphics;

    //opens edu.cpp.gui to javafx.fxml;
}