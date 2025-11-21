package model;

import java.util.Scanner;

// Read user prompt and print story to console
public class Main {
    public static void main(String[] args) {
        // Get title of story
        System.out.println("Please enter the title of the story:");
        Scanner sc = new Scanner(System.in);
        String title = sc.nextLine();

        // Get story description
        System.out.println("Please enter the description of the story, including how long you want it to be:");
        String description = sc.nextLine();

        // Send call to API
        StoryService storyService = new StoryService();
        Story story = storyService.generateStory(title, description);

        // Print story
        System.out.println("Here is your story:");
        System.out.println("Title: \"" + story.getTitle()+"\"");
        System.out.println(story.getFullText());
    }
}
