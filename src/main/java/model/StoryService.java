package model;

import api.APIClient;
import api.InterfaceAPI;

// Builds prompt and parses AI response
public class StoryService {

    private final InterfaceAPI client;

    // Constructor for normal use: gets singleton instance
    public StoryService() {
        client = APIClient.getInstance();
    }

    // Constructor for testing
    public StoryService(InterfaceAPI client) {
        this.client = client;
    }

    public Story generateStory(String title, String idea, String readingLevel, Integer textLength){
        Story story = new Story();
        story.setTitle(title);
        story.setReadingLevel(readingLevel);
        story.setWordCount(textLength);

        String prompt = "Write a short story based on this idea: " + idea +
                ". Make it a reading level appropriate for this age group: " + story.getReadingLevel() +
                ". Make it this many words long: " + story.getWordCount() + ".";

        // Generates story
        try {
            String aiText = client.generateText(prompt);
            if(story.getTitle() == null){
                story.setFullText(aiText);
            }
            else{
                story.setFullText("Title: "+story.getTitle() + "\n" + aiText);
            }
        } catch (Exception e) {
            story.setFullText("Error: " + e.getMessage());
        }

        return story;

    }
}
