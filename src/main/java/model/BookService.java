package model;

import api.APIClient;
import api.InterfaceAPI;

public class BookService {

    private final InterfaceAPI client;

    // Constructor for normal use: gets singleton instance
    public BookService() {
        client = APIClient.getInstance();
    }

    // Constructor for testing
    public BookService(InterfaceAPI client) {
        this.client = client;
    }

    public Book generateBook(String title, String idea, String readingLevel, Integer textLength){
        Book story = new Book();
        story.setTitle(title);
        story.setReadingLevel(readingLevel);
        story.setWordCount(textLength);

        String prompt = "Write a short story based on this idea: " + idea +
                ". Make it a reading level appropriate for this age group: " + story.getReadingLevel() +
                ". Make it this many words long: " + story.getWordCount() + ".";

        // Generates story
        try {
            String aiText = client.generateText(prompt);
            story.addChapter(aiText + "\n\n----------------------------------------------------------------------------------------------------\n");
        } catch (Exception e) {
            story.addChapter("Error: " + e.getMessage());
        }

        return story;

    }

    public Book generateAdditionalChapter(String currentStory, String title, String idea, String readingLevel, Integer textLength){
        Book story = new Book();
        story.setTitle(title);
        story.setReadingLevel(readingLevel);
        story.setWordCount(textLength);

        String prompt = "Write one more chapter for the following story: \"" + currentStory + "\" Include this idea: " + idea +
                ". Make it a reading level appropriate for this age group: " + story.getReadingLevel() +
                ". Make it this many words long: " + story.getWordCount() + ". Do not begin the story with a chapter heading." +
                " Just jump into the story.";

        // Generates story
        try {
            String aiText = client.generateText(prompt);
            story.addChapter(aiText + "\n\n----------------------------------------------------------------------------------------------------\n");
        } catch (Exception e) {
            story.addChapter("Error: " + e.getMessage());
        }

        return story;

    }

    
}
