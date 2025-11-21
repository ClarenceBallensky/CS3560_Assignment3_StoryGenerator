package api;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

// Send request to Gemini
public class APIClient {

    // Singleton
    private static APIClient instance;
    private final String model;
    private final Client client;

    private APIClient() {
        // Paste your API key in this line:
        client = Client.builder().apiKey("YOUR_API_KEY_HERE").build();

        // Gemini model
        model = "gemini-2.5-flash";
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
        GenerateContentResponse response =
                client.models.generateContent(
                        model,
                        prompt,
                        null);

        return response.text();
    }

}
