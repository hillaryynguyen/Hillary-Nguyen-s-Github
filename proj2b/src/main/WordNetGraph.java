package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import java.io.FileNotFoundException;


public class WordNetGraph {
    private Map<String, Set<String>> wordToSynsets;
    private Map<String, Set<String>> synsetToWords;
    private Map<String, Integer> wordFrequencies;


    public WordNetGraph(String synsetsFile, String hyponymsFile, String wordFrequenciesFile) {
        // Initialize the graph

        wordToSynsets = new HashMap<>();
        synsetToWords = new HashMap<>();
        wordFrequencies = new HashMap<>();

        parseSynsets(synsetsFile);
        parseHyponyms(hyponymsFile);
        parseWordFrequencies(wordFrequenciesFile);
    }
    private void parseWordFrequencies(String wordFrequenciesFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(wordFrequenciesFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String word = parts[0];
                    int frequency = Integer.parseInt(parts[1]);
                    wordFrequencies.put(word, frequency);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Handle FileNotFoundException
        } catch (IOException e) {
            e.printStackTrace(); // Handle IOException
        }
    }

    public void addEdge(String synsetID, String hyponymID) {
        wordToSynsets.computeIfAbsent(synsetID, k -> new HashSet<>()).add(hyponymID);
    }

    public void addHyponymEdge(String parentSynset, String childSynset) {
        synsetToWords.computeIfAbsent(parentSynset, k -> new HashSet<>()).add(childSynset);
    }


    private void parseSynsets(String synsetsFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(synsetsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String synsetID = parts[0];
                    String[] words = parts[1].split(" ");

                    // Populate wordToSynsets map
                    for (String word : words) {
                        wordToSynsets.computeIfAbsent(word, k -> new HashSet<>()).add(synsetID);
                    }

                    // Populate synsetToWords map
                    synsetToWords.put(synsetID, new HashSet<>(Arrays.asList(words)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();// Handle IOException
        }
    }

    private void parseHyponyms(String hyponymsFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(hyponymsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String synsetID = parts[0];
                    for (int i = 1; i < parts.length; i++) {
                        String hyponymID = parts[i];

                        // Add an edge from synsetID to hyponymID
                        addEdge(synsetID, hyponymID);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();// Handle IOException
        }
    }

    private Set<String> getHyponymsForWord(String word) {
        Set<String> hyponyms = new HashSet<>();
        if (wordToSynsets.containsKey(word)) {
            for (String synset : wordToSynsets.get(word)) {
                hyponyms.addAll(getHyponymsForSynset(synset));
            }
        }
        return hyponyms;
    }

    private Set<String> getHyponymsForSynset(String synset) {
        Set<String> hyponyms = new HashSet<>();
        if (synsetToWords.containsKey(synset)) {
            for (String word : synsetToWords.get(synset)) {
                hyponyms.addAll(wordToSynsets.get(word));
            }
        }
        return hyponyms;
    }

    public Set<String> findHyponymsForWords(List<String> words) {
        Set<String> hyponyms = new HashSet<>();
        if (words.isEmpty()) {
            return hyponyms; // Return an empty set if there are no input words.
        }

        // Start with the hyponyms of the first word.
        hyponyms.addAll(getHyponymsForWord(words.get(0)));

        for (int i = 1; i < words.size(); i++) {
            Set<String> hyponymsForWord = getHyponymsForWord(words.get(i));
            hyponyms.retainAll(hyponymsForWord); // Take the intersection of hyponyms.
        }

        return hyponyms;
    }

    public Set<String> findHyponymsForWords(List<String> words, int k, int startYear, int endYear) {
        Set<String> hyponyms = new HashSet<>();
        if (words.isEmpty()) {
            return hyponyms; // Return an empty set if there are no input words.
        }

        // Start with the hyponyms of the first word.
        hyponyms.addAll(getHyponymsForWord(words.get(0)));

        for (int i = 1; i < words.size(); i++) {
            Set<String> hyponymsForWord = getHyponymsForWord(words.get(i));
            hyponyms.retainAll(hyponymsForWord); // Take the intersection of hyponyms.
        }

        // Calculate popularity of hyponyms based on the specified time frame
        Map<String, Integer> hyponymPopularity = new HashMap<>();
        for (String hyponym : hyponyms) {
            if (wordFrequencies.containsKey(hyponym)) {
                int totalFrequency = wordFrequencies.get(hyponym);
                hyponymPopularity.put(hyponym, totalFrequency);
            }
        }

        // Sort the hyponyms by popularity
        List<String> sortedHyponyms = hyponymPopularity.keySet().stream()
                .sorted((w1, w2) -> hyponymPopularity.get(w2) - hyponymPopularity.get(w1))
                .toList();

        // Return the top k hyponyms
        if (sortedHyponyms.size() > k) {
            return new HashSet<>(sortedHyponyms.subList(0, k));
        } else {
            return new HashSet<>(sortedHyponyms);
        }
    }


}


