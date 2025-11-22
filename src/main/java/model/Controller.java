package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;

public class Controller {

    String readingLevel;
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

    //for the old reading level drop down menu
    //@FXML
    //private ComboBox<String> readingLevel;

    @FXML
    //to select child reading level
    private RadioButton childOption;

    @FXML
    //to select teen reading level
    private RadioButton teenOption;

    @FXML
    //to select adult reading level
    private RadioButton adultOption;

    @FXML
    private void getReadingLevel() {
        if (childOption.isSelected()) {
            readingLevel = "Child";
        }
        else if (teenOption.isSelected()) {
            readingLevel = "Teen";
        }
        else if (adultOption.isSelected()) {
            readingLevel = "Adult";
        }
        else { //nothing selected
            readingLevel = null;
        }
    }

    @FXML
    //this is the button the user can click to generate the story
    private Button generateStory;

    //for the old reading level dropdown menu
    @FXML
    private void initialize() {
        //readingLevel.setItems(readingLevelList);
    }


    @FXML
    private void handleGenerateStory() {
        String title = titleInputField.getText();
        String description = descriptionInputField.getText();
        //this.readingLevel = readingLevel;

        //call the API service
        StoryService storyService = new StoryService();
        Story story = storyService.generateStory(title, description, readingLevel);

        outputArea.setText(story.getFullText());
    }
}
