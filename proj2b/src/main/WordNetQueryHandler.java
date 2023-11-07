package main;

import java.util.*;
import wordnet.WordNet;

public class WordNetQueryHandler {
    private final WordNet wordNet;

    public WordNetQueryHandler(String synsetsFile, String hyponymsFile) {
        this.wordNet = new WordNet(synsetsFile, hyponymsFile);
    }

    public Set<String> handleQuery(List<String> words) {
        return wordNet.getCommonHyponyms(words);
    }
}
