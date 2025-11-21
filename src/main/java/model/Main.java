package model;

import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Read user prompt and print story to console
public class Main extends Application {

    @Override
    public void start(Stage stage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/cpp/cs3560_assignment3_storygenerator/hello-view.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        launch(args);

        /*
        // Get title of story
        System.out.println("Please enter the title of the story:");
        Scanner sc = new Scanner(System.in);
        String title = sc.nextLine();

        // Get story description
        System.out.println("Please enter the description of the story, including how long you want it to be:");
        String description = sc.nextLine();

        // Send call to API
        StoryService storyService = new StoryService();
        Story story = storyService.generateStory(title, description, readingLevel);

        // Print story
        System.out.println("Here is your story:");
        System.out.println("Title: \"" + story.getTitle()+"\"");
        System.out.println(story.getFullText());
         */
    }
}
