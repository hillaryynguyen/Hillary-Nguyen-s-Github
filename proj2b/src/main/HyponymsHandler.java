package main;

import browser.NgordnetQueryHandler;
import browser.NgordnetQuery;
import ngrams.NGramMap;
import java.util.List;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.*;


public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNetGraph wordNetGraph;

    public HyponymsHandler(WordNetGraph wordNetGraph) {
        this.wordNetGraph = wordNetGraph;

    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        // 1. Find hyponyms based on the words
        Set<String> hyponyms = wordNetGraph.findHyponymsForWords(words);

        // 2. Apply filtering based on startYear and endYear if needed
        // (You would need additional data or logic to filter by time frame)

        // 3. Limit the results to the top k hyponyms if k > 0
        if (k > 0 && hyponyms.size() > k) {
            List<String> topHyponyms = new ArrayList<>(hyponyms).subList(0, k);
            return formatResults(topHyponyms); // Format results as needed
        } else {
            return formatResults(new ArrayList<>(hyponyms)); // Format results as needed
        }
    }

    private String formatResults(List<String> results) {
        // Format the results as needed. For example, convert to JSON or plain text.
        // You can use a JSON library (e.g., Gson) for JSON formatting.
        return new Gson().toJson(results); // Format as JSON

    }
}
