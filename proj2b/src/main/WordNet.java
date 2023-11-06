package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordNet {
    private Map<Integer, Synset> synsets = new HashMap<>();
    private Map<String, Set<Integer>> wordToSynsetIds = new HashMap<>();
    private Graph<Integer> hyponymGraph = new Graph<>();
    public WordNet(String synsetsFile, String hyponymsFile) {
        this.hyponymGraph = new Graph<>();
        parseSynsets(synsetsFile);
        parseHyponyms(hyponymsFile);
    }

    private void parseSynsets(String synsetsFile) {
        try (Scanner scanner = new Scanner(new File(synsetsFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String[] nounList = parts[1].split(" ");
                Set<String> nouns = new HashSet<>(Arrays.asList(nounList));
                String definition = parts[2]; // May need more processing if definitions contain commas

                // Add the synset
                Synset synset = new Synset(id, nouns, definition);
                addSynset(synset);

                // Map each noun to the synset ID
                for (String noun : nouns) {
                    wordToSynsetIds.computeIfAbsent(noun, k -> new HashSet<>()).add(id);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Handle missing file
        }
    }


    private void parseHyponyms(String hyponymsFile) {
        try (Scanner scanner = new Scanner(new File(hyponymsFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int synsetId = Integer.parseInt(parts[0]);
                for (int i = 1; i < parts.length; i++) {
                    int hyponymId = Integer.parseInt(parts[i]);
                    // Add an edge from synsetId to hyponymId
                    hyponymGraph.addEdge(synsetId, hyponymId);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Handle missing file
        }
    }

    public Set<String> getCommonHyponyms(List<String> words) {
        Set<String> commonHyponyms = null; // This will eventually hold the common hyponyms

        for (String word : words) {
            Set<String> hyponymsOfWord = getHyponymsForSingleWord(word);

            if (commonHyponyms == null) {
                // For the first word, initialize commonHyponyms with its hyponyms
                commonHyponyms = new HashSet<>(hyponymsOfWord);
            } else {
                // For subsequent words, retain only common hyponyms
                commonHyponyms.retainAll(hyponymsOfWord);
            }
        }

        return commonHyponyms != null ? commonHyponyms : Collections.emptySet();
    }

    private Set<String> getHyponymsForSingleWord(String word) {
        Set<String> result = new HashSet<>();
        if (wordToSynsetIds.containsKey(word)) {
            for (Integer synsetId : wordToSynsetIds.get(word)) {
                Set<Integer> hyponymIds = hyponymGraph.getAllConnectedNodes(synsetId);
                for (Integer hyponymId : hyponymIds) {
                    Synset synset = synsets.get(hyponymId);
                    if (synset != null) {
                        result.addAll(synset.getNouns());
                    }
                }
            }
        }
        return result;
    }

    public void addSynset(Synset synset) {
        synsets.put(synset.getId(), synset);
        for (String noun : synset.getNouns()) {
            wordToSynsetIds.computeIfAbsent(noun, k -> new HashSet<>()).add(synset.getId());
        }
        hyponymGraph.addNode(synset.getId());
    }

    public void addHyponym(int synsetId, int hyponymId) {
        hyponymGraph.addEdge(synsetId, hyponymId);
    }

    public Set<String> getHyponyms(List<String> words) {
        Set<String> result = new HashSet<>();
        for (String word : words) { // Iterate over each word in the list
            if (wordToSynsetIds.containsKey(word)) {
                Set<Integer> synsetIds = wordToSynsetIds.get(word);
                for (Integer synsetId : synsetIds) {
                    Set<Integer> hyponymIds = hyponymGraph.getAllConnectedNodes(synsetId);
                    for (Integer hyponymId : hyponymIds) {
                        Synset synset = synsets.get(hyponymId);
                        if (synset != null) {
                            result.addAll(synset.getNouns());
                        }
                    }
                }
            }
        }
        return result;
    }

    // Methods to get hyponyms will be added here
}


