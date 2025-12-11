package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

public class StoryPersistence {
    // Allow user to save their story to a text file
    /*public static void saveStory(Story story) throws IOException {
        String filename = "stories/" + story.getTitle() + ".txt";

        File dir = new File("stories");
        if (!dir.exists()) {
            dir.mkdir();
        }

        try (FileWriter writer = new FileWriter(filename, false)) {
            writer.write("Title: " + story.getTitle() + "\n");
            writer.write("Reading Level: " + story.getReadingLevel() + "\n");
            writer.write("Word Count: " + story.getWordCount() + "\n\n");
            writer.write(story.getFullText());
            writer.write("\n\n-----------------------------------------------------------------------\n\n");
        }
    }*/

    //allows user to save their story to a text
    public static void saveStringsStory(String title, String fullStory) throws IOException {
        String filename = "stories/" + title + ".txt";

        File dir = new File("stories");
        if (!dir.exists()) {
            dir.mkdir();
        }

        try (FileWriter writer = new FileWriter(filename)) {
            //writer.write("Title: " + title + "\n\n");
            writer.write(fullStory.trim());
        }
    }

    public static String loadStory(String filename) throws IOException { 
        return Files.readString(Path.of("stories/" + filename + ".txt"));
    }

    public static void addChapter(Story story) {

    }
}
