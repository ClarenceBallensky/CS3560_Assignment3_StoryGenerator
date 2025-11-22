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
    Integer storyLength = null;
    String title = null;
    String description = null;
    Boolean hasErrors = false;

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
    private Label errorMessage;


    @FXML
    //for the readingLevel dropdown menu
    private void getReadingLevel() {
        selectedReadingLevel = readingLevel.getSelectionModel().getSelectedItem();
    }


    @FXML
    //when user clicks the "Generate Story" button, the user inputs are used to generate an AI story
    private void handleGenerateStory() {
        title = titleInputField.getText();
        description = descriptionInputField.getText();

        //reset the hasErrors flag each time the user clicks the "Generate Story" button
        hasErrors = false;
        //reset error messages every time the user clicks the "Generate Story" button
        errorMessage.setText("");

        try {
            storyLength = Integer.parseInt(lengthInputField.getText());
        } catch (NumberFormatException e) { //if the user does not enter an integer for story length
            errorMessage.setText(errorMessage.getText() + "Please enter a story length between 1 and 1000 words.\n");
            hasErrors = true;
        }
        if (storyLength > 1000) { //if the user requests a story length that is too many words long
            errorMessage.setText(errorMessage.getText() + "Please enter a story length between 1 and 1000 words.\n");
            hasErrors = true;
        }
        if (title.length() > 100) { //if the user enters a story title that is too many characters long
            errorMessage.setText(errorMessage.getText() + "Please enter a story title that is less than 100 characters.\n");
            hasErrors = true;
        }
        if (description.length() > 3000) {
            errorMessage.setText(errorMessage.getText() +  "Please enter a story description that is less than 3000 characters.\n");
            hasErrors = true;
        }

        if (hasErrors) {
            return;
        }


            //replace any former error messages with a confirmation message 
            errorMessage.setStyle("-fx-text-fill: black;");
            errorMessage.setText("Please wait up to one minute for your story to generate.");

            //call the API service
            StoryService storyService = new StoryService();
            Story story = storyService.generateStory(title, description, selectedReadingLevel, storyLength);

            outputArea.setText(story.getFullText());
    }
}
