package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;

public class Controller {

    ObservableList<String> readingLevelList = FXCollections.observableArrayList("Child", "Teen", "Adult");

    @FXML
    //this is where the user will enter the story title
    private TextField titleInputField;

    @FXML
    //this is where the user will enter the story description
    private TextArea descriptionInputField;

    @FXML
    //this is where the AI-generated story will go
    private TextArea outputArea;

    @FXML
    private ComboBox<String> readingLevel;

    @FXML
    //this is the button the user can click to generate the story
    private Button generateStory;

    @FXML
    private void initialize() {
        readingLevel.setItems(readingLevelList);
    }


    @FXML
    private void handleGenerateStory() {
        String title = titleInputField.getText();
        String description = descriptionInputField.getText();

        //call the API service
        StoryService storyService = new StoryService();
        Story story = storyService.generateStory(title, description);

        outputArea.setText(story.getFullText());
    }
}
