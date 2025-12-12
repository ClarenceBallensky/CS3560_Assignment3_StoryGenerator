package logic;

import api.InterfaceAPI;
import model.Book;
import model.BookService;
import model.StoryPersistence;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LogicTesting {
    // Fake API Client for testing
    static class FakeClient implements InterfaceAPI {
        @Override
        public String generateText(String prompt) {
            return "FAKE_RESPONSE";
        }
    }

    // Tests that story builds correct prompt and saves AI output
    @Test
    void testBookGenerationUsesClient() {
        BookService service = new BookService(new FakeClient());

        Book story = service.generateBook(
                "My Title",
                "A wizard in the forest",
                "5th grader",
                200
        );

        story.addChapter("FAKE_RESPONSE");

        assertEquals("My Title", story.getTitle());
        assertEquals("5th grader", story.getReadingLevel());
        assertEquals("FAKE_RESPONSE\n" +
                "\n" +
                "----------------------------------------------------------------------------------------------------\n" +
                "FAKE_RESPONSE", story.getBodyText());
        assertEquals(200, story.getWordCount());
    }


    // Tests that BookService handles exceptions
    @Test
    void testBookServiceHandlesApiError() {
        InterfaceAPI brokenClient = prompt -> { throw new Exception("API Failed"); };

        BookService service = new BookService(brokenClient);

        Book story = service.generateBook("Test", "idea", "adult", 20);

        assertTrue(story.getFullBookText().contains("Error"));
    }

    // Tests that null inputs don't crash service
    @Test
    void testNullInputsDoNotCrash() {
        BookService service = new BookService(prompt -> "OUTPUT");

        Book story = service.generateBook(null, null, null, 10);

        assertNotNull(story);
        assertEquals("OUTPUT\n" +
                "\n----------------------------------------------------------------------------------------------------\n", story.getBodyText());
    }

    // Tests that story is never null
    @Test
    void testBookIsNeverNull() {
        BookService service = new BookService(prompt -> "DATA");

        Book story = service.generateBook("X", "Y", "Z", 50);

        assertNotNull(story);
    }

    // Tests that stories get saved and loaded correctly
    @Test
    void testStoriesGetSaved() throws IOException {
        BookService service = new BookService(new FakeClient());
        Book story = service.generateBook("X", "Y", "Z", 50);

        StoryPersistence.saveBook(story);
        Book loadedBook = StoryPersistence.loadBook(story.getTitle());

        // Trim unnecessary newline
        assertEquals(story.getBodyText().trim(), loadedBook.getBodyText());
    }
}