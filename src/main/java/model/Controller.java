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

    //initially set selectedReadingLevel to null
    String selectedReadingLevel = null;

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
    //dropdown menu for readingLevel selection
    private ComboBox<String> readingLevel;

    @FXML
    //this is where the user will enter their desired word count
    private TextField lengthInputField;

    @FXML
    //this is the button the user can click to generate the story
    private Button generateStory;


    @FXML
    //for the readingLevel dropdown menu
    private void getReadingLevel() {
        selectedReadingLevel = readingLevel.getSelectionModel().getSelectedItem();
    }


    @FXML
    //when user clicks the "Generate Story" button, the user inputs are used to generate an AI story
    private void handleGenerateStory() {
        String title = titleInputField.getText();
        String description = descriptionInputField.getText();
        Integer length =  Integer.parseInt(lengthInputField.getText());

        //call the API service
        StoryService storyService = new StoryService();
        Story story = storyService.generateStory(title, description, selectedReadingLevel, length);

        outputArea.setText(story.getFullText());
    }
}
