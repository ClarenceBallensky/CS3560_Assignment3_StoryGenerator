package model;

import api.APIClient;

// Builds prompt and parses AI response
public class StoryService {

    // Gets singleton instance
    private final APIClient client = APIClient.getInstance();

    public Story generateStory(String title, String idea){
        Story story = new Story();
        story.setTitle(title);

        String prompt = "Write a short story based on this idea: " + idea;

        // Generates story
        try {
            String aiText = client.generateText(prompt);
            story.setFullText(aiText);
        } catch (Exception e) {
            story.setFullText("Error: " + e.getMessage());
        }

        return story;

    }
}
