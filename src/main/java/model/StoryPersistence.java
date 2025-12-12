package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StoryPersistence {

    public static void saveBook(Book book) throws IOException {
        File dir = new File("stories");
        if (!dir.exists()) {
            dir.mkdir();
        }

        String filename = "stories/" + book.getTitle() + ".txt";

        try (FileWriter writer = new FileWriter(filename, false)) {

            writer.write("Title: " + book.getTitle() + "\n");
            writer.write("Reading Level: " + book.getReadingLevel() + "\n");
         
            int i = 1;
            for (String chapter : book.getChapters()) {
                writer.write("\n\n=== Chapter " + i + " ===\n");
                writer.write(chapter);
                i++;
            }
        }
    }

    public static Book loadBook(String filename) throws IOException {
        //get full text of a book saved to the stories directory 
        String raw = Files.readString(Path.of("stories/" + filename + ".txt"));
        System.out.println("RAW FILE:\n" + raw);


        //parse data
        String title = raw.split("\n")[0].replace("Title: ", "").trim();
        String readingLevel = raw.split("\n")[1].replace("Reading Level: ", "").trim();

        //Create new book object
        Book book = new Book(title, readingLevel);

        //get just the chapter body without the "=== Chapter # ===\n" heading
        String[] parts = raw.split("\\R+=== Chapter ");

        //if no chapter markers found —> treat everything after header as a single chapter
        if (parts.length == 1) {
            String[] lines = raw.split("\\R", 3); //split into Title, Reading Level, full text
            if (lines.length >= 3) {
                String chapterBody = lines[2].trim();
                book.addChapter(chapterBody);
            }
            return book;
        }

        for (int i = 1; i < parts.length; i++) {
            int firstNewline = parts[i].indexOf('\n');
            if (firstNewline == -1) continue;

            String chapterBody = parts[i].substring(firstNewline + 1).trim();
            book.addChapter(chapterBody);
        }

        return book;
    }
}
