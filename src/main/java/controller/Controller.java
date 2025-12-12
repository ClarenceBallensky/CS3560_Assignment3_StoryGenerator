package controller;

import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import model.Story;
import model.StoryPersistence;
import model.StoryService;
import model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.fxml.Initializable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class Controller implements Initializable {

    //initially set selectedReadingLevel to null
    String selectedReadingLevel = null;
    Integer storyLength = null;
    String title = null;
    String description = null;
    Boolean hasErrors = false;

    Book book;

    ObservableList<String> bookNames = FXCollections.observableArrayList();


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
    private Button saveStory;
    @FXML
    private Button loadStory;
    @FXML
    private Button addChapter;


    @FXML
    //for the readingLevel dropdown menu
    private void getReadingLevel() {
        selectedReadingLevel = readingLevel.getSelectionModel().getSelectedItem();
    }

    //when the user wants to load a saved story, this is where they type the name of the story they want to load
    @FXML
    private TextField loadInputField;

    @FXML
    private ComboBox<String> storyDropdown;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshStoryNames();
        storyDropdown.setItems(bookNames);
    }

    public void refreshStoryNames() {
        //bookNames.clear(); //clear the current booknames list

        File folder = new File("stories");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files != null) {
            for (File f : files) {
                String title = f.getName().replace(".txt", "");
                if (!bookNames.contains(title))
                {
                    bookNames.add(title);
                }
            }
        }
    }


    //retrieves and validates user inputs
    //generation type refers to whether we are generating a new story, or adding a new chapter to a saved story 
    @FXML 
    private boolean validateUserInputs(String generationType) {
        title = titleInputField.getText();
        description = descriptionInputField.getText();

        //reset the hasErrors flag
        hasErrors = false;
        //reset error messages
        errorMessage.setText("");
        errorMessage.setStyle("-fx-text-fill: red;");

        try {
            storyLength = Integer.parseInt(lengthInputField.getText());
        } catch (NumberFormatException e) { //if the user does not enter an integer for story length
            errorMessage.setText(errorMessage.getText() + "Please enter a " + generationType + " length between 1 and 1000 words.\n");
            hasErrors = true;
        }
        if (storyLength > 1000) { //if the user requests a story length that is too many words long
            errorMessage.setText(errorMessage.getText() + "Please enter a " + generationType + " length between 1 and 1000 words.\n");
            hasErrors = true;
        }
        if (title.length() > 100) { //if the user enters a story title that is too many characters long
            errorMessage.setText(errorMessage.getText() + "Please enter a " + generationType + " title that is less than 100 characters.\n");
            hasErrors = true;
        }
        if (description.length() > 3000) {
            errorMessage.setText(errorMessage.getText() +  "Please enter a " + generationType + " that is less than 3000 characters.\n");
            hasErrors = true;
        }

        return hasErrors;
    }

    @FXML
    //when user clicks the "Generate Story" button, the user inputs are used to generate an AI story
    private void handleGenerateStory() {
        
        //if user input has errors, return the handleGenerateStory method without generating the story
        if (validateUserInputs("story")) { 
            return;  
        }

        //replace any former error messages with a confirmation message 
        errorMessage.setStyle("-fx-text-fill: black;");
        errorMessage.setText("Please wait up to one minute for your story to generate.");

        //call the API service
        StoryService storyService = new StoryService();
        Story firstChapter = storyService.generateStory(title, description, selectedReadingLevel, storyLength);

        book = new Book(title, selectedReadingLevel);
        book.addChapter(firstChapter.getFullText());

        outputArea.setText(book.getFullBookText());
    }

    @FXML
    private void handleSaveStory() {
        try {
            StoryPersistence.saveBook(book);
            errorMessage.setStyle("-fx-text-fill: black;");
            errorMessage.setText("Story saved!");
        } catch (IOException e) {
            errorMessage.setStyle("-fx-text-fill: red;");
            errorMessage.setText("Error saving the story.");
        }
        // Refresh book list
        refreshStoryNames();
    }

    @FXML
    private void handleLoadStory() {
        //reset error message every time user clicks the "Load Story" button
        errorMessage.setText("");

        try {
            //get the title of the story we want to load
            String filename = storyDropdown.getSelectionModel().getSelectedItem();

            book = StoryPersistence.loadBook(filename);
            outputArea.setText(book.getFullBookText());

        } catch (IOException e) {
            errorMessage.setStyle("-fx-text-fill: red;");
            errorMessage.setText("Error reading file: " + e.getMessage());
        }

    }

    @FXML
    private void handleAddChapter() {

        //String storyBody = outputArea.getText();
        handleLoadStory();

        if (book == null) {
            errorMessage.setStyle("-fx-text-fill: red;");
            errorMessage.setText("Load or generate a story before adding a chapter.");
            return;
        }

        //if user input has errors, return handleAddChapter without generating the new chapter
        if (validateUserInputs("chapter")) {
            return;
        }

        //replace any former error messages with a confirmation message 
        errorMessage.setStyle("-fx-text-fill: black;");
        errorMessage.setText("Please wait up to one minute for your story to generate.");

        //call the API service
        StoryService storyService = new StoryService();
        Story newChapter = storyService.generateAdditionalChapter(book.getFullBookText(), title, description, selectedReadingLevel, storyLength);

        book.addChapter(newChapter.getFullText());

        outputArea.setText(book.getFullBookText());

        handleSaveStory();
    }

}
