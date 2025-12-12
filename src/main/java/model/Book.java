package model;

import java.util.List;
import java.util.ArrayList;

public class Book {

    private String title;
    private String readingLevel;
    private int wordCount;
    private List<String> chapters = new ArrayList<>();

    public Book() {
        title = "";
        readingLevel = "";
    }

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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReadingLevel() {
        return readingLevel;
    }

    public void setReadingLevel(String readingLevel) {
        this.readingLevel = readingLevel;
    }

     public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }
   
    public String getFullBookText() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < chapters.size(); i++) {
            sb.append("=== Chapter ").append(i + 1).append(" ===\n")
              .append(chapters.get(i)).append("\n");
        }

        return sb.toString();
    }

    public String getBodyText() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < chapters.size(); i++) {
           sb.append(chapters.get(i)).append("\n");
        }
        return sb.toString();
    } 
}
