package model;

// Story class, includes the text and title of story
public class Story {

    private String fullText;
    private String title;
    private String readingLvl;
    private int wordCount;

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReadingLevel(String readingLvl) {
        this.readingLvl = readingLvl;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public String getFullText() {
        return fullText;
    }

    public String getTitle() {
        return title;
    }

    public String getReadingLevel() {
        return readingLvl;
    }

    public int getWordCount() {
        return wordCount;
    }
}
