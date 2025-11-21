package model;

// Story class, includes the text and title of story
public class Story {

    private String fullText;
    private String title;

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullText() {
        return fullText;
    }

    public String getTitle() {
        return title;
    }
}
