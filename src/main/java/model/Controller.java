package model;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

public class Controller {

    @FXML
    private TextField userInputField; //title

    @FXML
    private TextArea descriptionField; //story description

    @FXML
    private TextArea outputArea; //story produced

    @FXML
    private Button generateStory;

    @FXML
    private void handleGenerateStory() {
        String title = userInputField.getText();
        String description = descriptionField.getText();

        //call the API service
        StoryService storyService = new StoryService();
        Story story = storyService.generateStory(title, description);

        outputArea.setText(story.getFullText());
    }
}
