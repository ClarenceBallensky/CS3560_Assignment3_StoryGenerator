package model;

import java.util.List;
import java.util.ArrayList;

public class Book {

    private String title;
    private String readingLevel;
    private List<String> chapters = new ArrayList<>();

    public Book(String title, String readingLevel) {
        this.title = title;
        this.readingLevel = readingLevel;
    }
    
    public void addChapter(String chapterText) {
        chapters.add(chapterText);
    }

    public List<String> getChapters() {
        return chapters;
    }

    public String getTitle() {
        return title;
    }

    public String getReadingLevel() {
        return readingLevel;
    }

    public String getFullBookText() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < chapters.size(); i++) {
            sb.append("Chapter ").append(i + 1).append("\n")
              .append(chapters.get(i)).append("\n");
        }

        return sb.toString();
    }
}
