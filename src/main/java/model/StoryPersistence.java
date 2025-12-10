package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StoryPersistence {
    // Allow user to save their story to a text file
    public static void saveStory(Story story) throws IOException {
        String filename = "stories/" + story.getTitle() + ".txt";

        File dir = new File("stories");
        if (!dir.exists()) {
            dir.mkdir();
        }

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Title: " + story.getTitle() + "\n");
            writer.write("Reading Level: " + story.getReadingLevel() + "\n");
            writer.write("Word Count: " + story.getWordCount() + "\n\n");
            writer.write(story.getFullText());
        }
    }
}
