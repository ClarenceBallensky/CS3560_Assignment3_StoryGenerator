package controller;

import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import model.Story;
import model.StoryPersistence;
import model.StoryService;
import model.BookService;
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
    //where the user will enter the book title
    private TextField titleInputField;

    @FXML
    //where the user will enter the description
    private TextArea descriptionInputField;

    @FXML
    //where the AI-generated story will go
    private TextArea outputArea;

    @FXML
    //dropdown menu for readingLevel selection
    private ComboBox<String> readingLevel;

    @FXML
    //where the user will enter their desired word count
    private TextField lengthInputField;

    //where error messages are printed as needed
    @FXML
    private Label errorMessage;

    @FXML
    //for the readingLevel dropdown menu
    private void getReadingLevel() {
        selectedReadingLevel = readingLevel.getSelectionModel().getSelectedItem();
    }

    //this indicates how close the story is to being generated 
    @FXML private ProgressIndicator loadingSpinner;

    //dropdown menu of all the names of saved books 
    @FXML
    private ComboBox<String> storyDropdown;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshBookNames();
        storyDropdown.setItems(bookNames);
    }

    //updates the dropdown menu of all the names of saved books 
    public void refreshBookNames() {
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
    //generation type refers to whether we are generating a new book, or adding a new chapter to a saved book 
    @FXML 
    private boolean validateUserInputs(String generationType) {
        title = titleInputField.getText();
        description = descriptionInputField.getText();

        //reset the hasErrors flag
        hasErrors = false;
        //reset error messages
        errorMessage.setText("");
        errorMessage.setStyle("-fx-text-fill: #a31621;");

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

        //show spinner
        loadingSpinner.setVisible(true);
        //replace any former error messages with a confirmation message 
        errorMessage.setStyle("-fx-text-fill: black;");
        errorMessage.setText("Please wait up to one minute for your story to generate.");

        /*//call the API service in the background so UI can update freely
        Task<Story> task = new Task<>() {
            @Override
            protected Story call() throws Exception {
                StoryService storyService = new StoryService();
                return storyService.generateStory(title, description, selectedReadingLevel, storyLength
                );
            }
        };*/

        //call the API service in the background so UI can update freely
        Task<Book> task = new Task<>() {
            @Override
            protected Book call() throws Exception {
                BookService storyService = new BookService();
                return storyService.generateBook(title, description, selectedReadingLevel, storyLength);
            }
        };

        /*//when finished (on UI thread)
        task.setOnSucceeded(event -> {
            Story firstChapter = task.getValue();

            book = new Book(title, selectedReadingLevel);
            book.addChapter(firstChapter.getFullText());
            outputArea.setText(book.getFullBookText());
            loadingSpinner.setVisible(false); // hide after finishing
            errorMessage.setText("Story generated!");
        });*/

        //when finished (on UI thread)
        task.setOnSucceeded(event -> {
            Book firstChapter = task.getValue();
            System.out.println(firstChapter.getFullBookText());

            book = new Book(title, selectedReadingLevel);
            book.addChapter(firstChapter.getBodyText());
            System.out.println(book.getFullBookText());

            outputArea.setText(book.getFullBookText());
            loadingSpinner.setVisible(false); // hide after finishing
            errorMessage.setText("Story generated!");
        });

        //if something goes wrong
        task.setOnFailed(event -> {
            Throwable error = task.getException();
            errorMessage.setStyle("-fx-text-fill: #a31621;");
            errorMessage.setText("Error generating story: " + error.getMessage());
            loadingSpinner.setVisible(false);
            errorMessage.setText("Something went wrong...");
        });

        //start background thread
        new Thread(task).start();
    }

    @FXML
    private void handleSaveStory() {
        try {
            StoryPersistence.saveBook(book);
            errorMessage.setStyle("-fx-text-fill: black;");
            errorMessage.setText("Story saved!");
        } catch (IOException e) {
            errorMessage.setStyle("-fx-text-fill: #a31621;");
            errorMessage.setText("Error saving the story.");
        }
        // Refresh book list
        refreshBookNames();
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
            errorMessage.setStyle("-fx-text-fill: #a31621;");
            errorMessage.setText("Please select a story from the Story Library to load.");
        }

    }

    @FXML
    private void handleAddChapter() {

        //String storyBody = outputArea.getText();
        handleLoadStory();

        if (book == null) {
            errorMessage.setStyle("-fx-text-fill: #a31621;");
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

        //show spinner and message
        loadingSpinner.setVisible(true);
        errorMessage.setStyle("-fx-text-fill: black;");
        errorMessage.setText("Please wait up to one minute for your chapter to generate.");

        //background task to generate chapter
        /*Task<Story> task = new Task<Story>() {
            @Override
            protected Story call() throws Exception {
                StoryService storyService = new StoryService();
                return storyService.generateAdditionalChapter(book.getFullBookText(), title, description,
                        selectedReadingLevel, storyLength
                );
            }
        };*/

        Task<Book> task = new Task<Book>() {
            @Override
            protected Book call() throws Exception {
                BookService storyService = new BookService();
                return storyService.generateAdditionalChapter(book.getFullBookText(), title, description, 
                                                               selectedReadingLevel, storyLength);
            }
        };

        /*//when the task succeeds, update UI
        task.setOnSucceeded(event -> {
            Story newChapter = task.getValue();
            book.addChapter(newChapter.getFullText());
            outputArea.setText(book.getFullBookText());

            handleSaveStory();

            loadingSpinner.setVisible(false);
            errorMessage.setText("Chapter added!");
        });*/

        //when the task succeeds, update UI
        task.setOnSucceeded(event -> {
            Book newChapter = task.getValue();
            book.addChapter(newChapter.getBodyText());
            outputArea.setText(book.getFullBookText());

            System.out.println(newChapter.getFullBookText());
            System.out.println(book.getFullBookText());

            handleSaveStory();

            loadingSpinner.setVisible(false);
            errorMessage.setText("Chapter added!");
        });
        

        //handle errors
        task.setOnFailed(event -> {
            loadingSpinner.setVisible(false);
            errorMessage.setStyle("-fx-text-fill: #a31621;");
            errorMessage.setText("Error generating chapter.");
        });

        //run task in background
        new Thread(task).start();
    }

    //clear all input fields
    @FXML
    private void handleClearFields() {
        //clear text inputs
        titleInputField.clear();
        descriptionInputField.clear();
        lengthInputField.clear();
        errorMessage.setText("");

        //reset dropdowns
        if (selectedReadingLevel != null) {
            readingLevel.getSelectionModel().clearSelection();
            selectedReadingLevel = null;
        }
    }

}
