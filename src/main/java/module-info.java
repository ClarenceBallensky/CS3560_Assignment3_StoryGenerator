module edu.cpp.cs3560_assignment3_storygenerator {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.cpp.cs3560_assignment3_storygenerator to javafx.fxml;
    exports edu.cpp.cs3560_assignment3_storygenerator;
}