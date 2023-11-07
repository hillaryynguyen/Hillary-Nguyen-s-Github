package main;

import java.util.*;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import wordnet.WordNet;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final WordNet wordNet;
    private final NGramMap nGramMap;
    private final Gson gson = new Gson();

    public HyponymsHandler(String synsetsFile, String hyponymsFile, NGramMap nGramMap) {
        this.wordNet = new WordNet(synsetsFile, hyponymsFile); // Now WordNet is populated
        this.nGramMap = nGramMap;
    }


    @Override
    public String handle(NgordnetQuery query) {
        List<String> words = query.words();
        int startYear = query.startYear();
        int endYear = query.endYear();
        int k = query.k();

        // Get common hyponyms for the list of words
        Set<String> hyponyms = wordNet.getCommonHyponyms(words);

        // If k > 0, find the k most common hyponyms using NGramMap
        if (k > 0) {
            // Create a map to hold the count for each hyponym
            Map<String, Long> hyponymCounts = new HashMap<>();

            for (String hyponym : hyponyms) {
                // Use NGramMap to get the count history for each hyponym
                TimeSeries countHistory = nGramMap.countHistory(hyponym, startYear, endYear);
                // Calculate the total count over the specified time range
                long totalCount = countHistory.values().stream().mapToLong(Number::longValue).sum();
                // Only add the hyponym if its count is greater than zero
                if (totalCount > 0) {
                    hyponymCounts.put(hyponym, totalCount);
                }
            }

            // Get the top k hyponyms sorted by count
            hyponyms = getTopKHyponyms(hyponymCounts, k);
        }

        // Convert the set of hyponyms to a sorted list
        List<String> sortedHyponyms = new ArrayList<>(hyponyms);
        Collections.sort(sortedHyponyms);
        String hyponymsString = sortedHyponyms.stream().map(Object::toString).collect(Collectors.joining(", "));

        return "[" + hyponymsString + "]";
    }

    // Helper method to get the top k hyponyms sorted by count
    private Set<String> getTopKHyponyms(Map<String, Long> hyponymCounts, int k) {
        return hyponymCounts.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(k)
                .map(Map.Entry::getKey).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    // In the HyponymsHandler class
    private long getTotalCountFromTimeSeries(TimeSeries history) {
        // Assuming TimeSeries provides a way to iterate over its entries
        long totalCount = 0;
        for (Double count : history.values()) {
            totalCount += count;
        }
        return totalCount;
    }
}
