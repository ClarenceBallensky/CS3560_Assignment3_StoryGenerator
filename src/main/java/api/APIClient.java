package api;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import io.github.bucket4j.*;
import java.time.Duration;

// Send request to Gemini
public class APIClient implements InterfaceAPI {

    // Singleton
    private static APIClient instance;
    private final String model;
    private final Client client;
    // Rate limiting
    private final Bucket bucket;



    private APIClient() {
        // Paste your API key in this line:
        client = Client.builder().apiKey("INSERT_YOUR_API_KEY_HERE").build();

        // Gemini model
        model = "gemini-2.5-flash";

        // Rate limiting
        bucket = Bucket.builder()
                .addLimit(Bandwidth.simple(1, Duration.ofSeconds(1))) // 1 request/sec
                .build();
;    }

    // Singleton accessor
    public static APIClient getInstance() {
        if (instance == null) {
            instance = new APIClient();
        }
        return instance;
    }

    // Calls Gemini to generate story
    public String generateText(String prompt) throws Exception {
        // Check if rate limit exceeded
        if (!bucket.tryConsume(1)) {
            throw new RuntimeException("Rate limit exceeded. Try again soon.");
        }

        // Otherwise get AI response
        GenerateContentResponse response =
                client.models.generateContent(
                        model,
                        prompt,
                        null);

        return response.text();
    }

}
