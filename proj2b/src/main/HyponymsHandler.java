package main;

import browser.NgordnetQueryHandler;
import browser.NgordnetQuery;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wordNet; // Your WordNet parsing class

    public HyponymsHandler(String synsetsFile, String hyponymsFile) {
        // Initialize WordNetParser with the dataset files
        wordNet = new WordNet(synsetsFile, hyponymsFile);
    }

    @Override
    public String handle(NgordnetQuery ngordnetQuery) {
        if (ngordnetQuery.hasWords() && ngordnetQuery.getWords().size() == 1) {
            String word = ngordnetQuery.getWords().get(0);
            List<String> hyponyms = wordNetParser.findHyponyms(word);

            // Ensure the list of hyponyms is sorted and has no duplicates
            Set<String> hyponymSet = new TreeSet<>(hyponyms);

            // Convert the set of hyponyms to a string representation
            return hyponymSet.toString();
        } else {
            return "Invalid input. Please provide a single word.";
        }
    }
}
