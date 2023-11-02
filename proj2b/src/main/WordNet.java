package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {
    private final Graph graph;
    private final Map<String, List<Integer>> wordToSynsets;

    public WordNet(String synsetsFile, String hyponymsFile) {
        graph = new Graph();
        wordToSynsets = new HashMap<>();
        parseSynsets(synsetsFile);
        parseHyponyms(hyponymsFile);
    }

    private void parseSynsets(String synsetsFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(synsetsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int synsetId = Integer.parseInt(parts[0]);
                String[] words = parts[1].split(" ");
                for (String word : words) {
                    wordToSynsets.computeIfAbsent(word, k -> new ArrayList<>()).add(synsetId);
                }
                // Add synset to the graph
                graph.createNode(synsetId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseHyponyms(String hyponymsFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(hyponymsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int synsetId = Integer.parseInt(parts[0]);
                for (int i = 1; i < parts.length; i++) {
                    int hyponymId = Integer.parseInt(parts[i]);
                    // Add an edge from synsetId to hyponymId
                    graph.addEdge(synsetId, hyponymId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> findHyponyms(String word) {
        List<Integer> synsetIds = wordToSynsets.get(word);
        if (synsetIds == null) {
            return new ArrayList<>(); // Word not found
        }

        List<String> hyponyms = new ArrayList<>();
        for (int synsetId : synsetIds) {
            hyponyms.addAll(getHyponymsForSynset(synsetId));
        }

        return hyponyms;
    }

    private List<String> getHyponymsForSynset(int synsetId) {
        List<String> hyponyms = new ArrayList<>();
        List<Integer> reachableSynsets = graph.findReachableVertices(synsetId);
        for (int reachableSynsetId : reachableSynsets) {
            hyponyms.addAll(getWordsForSynset(reachableSynsetId));
        }
        return hyponyms;
    }

    private List<String> getWordsForSynset(int synsetId) {
        String[] words = findWordsForSynsetId(synsetId);
        return List.of(words);
    }

    private String[] findWordsForSynsetId(int synsetId) {
        // Implement this method to return words for a given synset ID
        // You can parse the synsets file again to find words for a specific synset
        // This logic depends on the structure of your dataset
        // The provided code assumes you have a way to look up words for a synset ID
        return new String[0];
    }
}

