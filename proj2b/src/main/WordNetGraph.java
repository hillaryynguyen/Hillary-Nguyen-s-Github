package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class WordNetGraph {
    private Map<String, Set<String>> wordToSynsets;
    private Map<String, Set<String>> synsetToWords;

    public WordNetGraph(String synsetsFile, String hyponymsFile) {
        wordToSynsets = new HashMap<>();
        synsetToWords = new HashMap();
        // Implement parsing of synsets and hyponyms data to populate wordToSynsets and synsetToWords
        // You need to read and process data from synsetsFile and hyponymsFile.
    }

    public Set<String> getHyponymsForWords(List<String> words) {
        Set<String> commonHyponyms = new HashSet<>();
        if (words.size() < 2) {
            return commonHyponyms;
        }

        commonHyponyms.addAll(getHyponymsForWord(words.get(0)));

        for (int i = 1; i < words.size(); i++) {
            Set<String> hyponymsForWord = getHyponymsForWord(words.get(i));
            commonHyponyms.retainAll(hyponymsForWord);
        }

        return commonHyponyms;
    }

    private Set<String> getHyponymsForWord(String word) {
        Set<String> hyponyms = new HashSet<>();
        if (wordToSynsets.containsKey(word)) {
            for (String synset : wordToSynsets.get(word)) {
                if (synsetToWords.containsKey(synset)) {
                    hyponyms.addAll(synsetToWords.get(synset));
                }
            }
        }
        return hyponyms;
    }
}


