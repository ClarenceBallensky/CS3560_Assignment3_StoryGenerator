package logic;

import api.InterfaceAPI;
import model.Story;
import model.StoryService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
    void testStoryGenerationUsesFakeClient() {
        StoryService service = new StoryService(new FakeClient());

        Story story = service.generateStory(
                "My Title",
                "A wizard in the forest",
                "5th grader",
                200
        );

        assertEquals("My Title", story.getTitle());
        assertEquals("5th grader", story.getReadingLevel());
        assertEquals("Title: My Title\n"+"FAKE_RESPONSE", story.getFullText());
        assertEquals(200, story.getWordCount());
    }

    // Tests that StoryService handles exceptions
    @Test
    void testStoryServiceHandlesApiError() {
        InterfaceAPI brokenClient = prompt -> { throw new Exception("API Failed"); };

        StoryService service = new StoryService(brokenClient);

        Story story = service.generateStory("Test", "idea", "adult", 20);

        assertTrue(story.getFullText().contains("Error"));
    }

    // Fake API client to record what StoryService passed to it
    static class SpyClient implements InterfaceAPI {
        String lastPrompt;
        @Override
        public String generateText(String prompt) {
            lastPrompt = prompt;
            return "ok";
        }
    }

    // Tests that prompt is formatted correctly
    @Test
    void testPromptIsFormattedCorrectly() throws Exception {
        SpyClient spy = new SpyClient();
        StoryService service = new StoryService(spy);

        service.generateStory("T", "robots take over", "teen", 10);

        assertTrue(spy.lastPrompt.contains("Make it a reading level appropriate for this age group: teen"));
        assertTrue(spy.lastPrompt.contains("idea: robots take over"));
        assertTrue(spy.lastPrompt.contains("many words long: 10"));
    }

    // Tests that title, reading level, and idea propagate properly
    @Test
    void testStoryFieldsAreAssigned() {
        StoryService service = new StoryService(prompt -> "X");

        Story s = service.generateStory("Hello", "Cats", "child", 20);

        assertEquals("Hello", s.getTitle());
        assertEquals("child", s.getReadingLevel());
        assertEquals("Title: Hello\nX", s.getFullText());
        assertEquals(20, s.getWordCount());
    }


    // Tests that null inputs don't crash service
    @Test
    void testNullInputsDoNotCrash() {
        StoryService service = new StoryService(prompt -> "OUTPUT");

        Story story = service.generateStory(null, null, null, 10);

        assertNotNull(story);
        assertEquals("OUTPUT", story.getFullText());
    }

    @Test
    void testStoryIsNeverNull() {
        StoryService service = new StoryService(prompt -> "DATA");

        Story story = service.generateStory("X", "Y", "Z", 50);

        assertNotNull(story);
    }
}