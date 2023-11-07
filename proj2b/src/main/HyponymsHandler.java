package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import com.google.gson.Gson;
import wordnet.WordNet;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNetQueryHandler wordNetQueryHandler;
    private NGramMap nGramMap;
    private Gson gson;

    public HyponymsHandler(String synsetsFile, String hyponymsFile, NGramMap nGramMap) throws IOException {
        this.wordNetQueryHandler = new WordNetQueryHandler(synsetsFile, hyponymsFile);
        this.nGramMap = nGramMap;
        this.gson = new Gson();
    }

    @Override
    public String handle(NgordnetQuery query) {
        Set<String> hyponyms = wordNetQueryHandler.handleQuery(query.words());
        if (query.k() > 0) {
            Map<String, Long> hyponymCounts = new HashMap<>();
            for (String hyponym : hyponyms) {
                TimeSeries history = nGramMap.countHistory(hyponym, query.startYear(), query.endYear());
                long totalCount = history.values().stream().mapToLong(Number::longValue).sum();
                if (totalCount > 0) {
                    hyponymCounts.put(hyponym, totalCount);
                }
            }
            hyponyms = getTopKHyponyms(hyponymCounts, query.k());
        }
        List<String> sortedHyponyms = new ArrayList<>(hyponyms);
        Collections.sort(sortedHyponyms);
        String hyponymsString = sortedHyponyms.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        return "[" + hyponymsString + "]";
    }

    private Set<String> getTopKHyponyms(Map<String, Long> hyponymCounts, int k) {
        return hyponymCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}