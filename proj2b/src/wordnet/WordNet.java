package wordnet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Represents the WordNet, managing synsets and their hyponym relationships.
 */
public class WordNet {
    private final Map<Integer, Set<String>> synsetMap; // Maps synset ID to a set of nouns
    private final Map<String, Set<Integer>> nounToSynsetIdMap; // Maps a noun to its synset IDs
    private final Graph<Integer> hyponymGraph; // Graph to store hyponym relationships

    public WordNet(String synsetsFile, String hyponymsFile) {
        synsetMap = new HashMap<>();
        nounToSynsetIdMap = new HashMap<>();
        hyponymGraph = new Graph<>();
        loadSynsets(synsetsFile);
        loadHyponyms(hyponymsFile);
    }

    private void loadSynsets(String synsetsFile) {
        try (Scanner scanner = new Scanner(new File(synsetsFile))) {
            while (scanner.hasNextLine()) {
                processSynsetLine(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void processSynsetLine(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        Set<String> nouns = new HashSet<>(Arrays.asList(parts[1].split(" ")));

        synsetMap.put(id, nouns);
        nouns.forEach(noun -> nounToSynsetIdMap.computeIfAbsent(noun, k -> new HashSet<>()).add(id));
    }

    private void loadHyponyms(String hyponymsFile) {
        try (Scanner scanner = new Scanner(new File(hyponymsFile))) {
            while (scanner.hasNextLine()) {
                processHyponymLine(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void processHyponymLine(String line) {
        String[] parts = line.split(",");
        int synsetId = Integer.parseInt(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            int hyponymId = Integer.parseInt(parts[i]);
            hyponymGraph.addEdge(synsetId, hyponymId);
        }
    }

    public Set<String> getCommonHyponyms(List<String> words) {
        Set<String> commonHyponyms = new HashSet<>();
        for (String word : words) {
            Set<String> hyponyms = getHyponymsForWord(word);
            if (commonHyponyms.isEmpty()) {
                commonHyponyms.addAll(hyponyms);
            } else {
                commonHyponyms.retainAll(hyponyms);
            }
        }
        return commonHyponyms;
    }

    private Set<String> getHyponymsForWord(String word) {
        Set<String> hyponyms = new HashSet<>();
        nounToSynsetIdMap.getOrDefault(word, Collections.emptySet())
                .forEach(synsetId -> {
                    Set<Integer> connectedNodes = hyponymGraph.getConnectedNodes(synsetId);
                    connectedNodes.forEach(hyponymId -> hyponyms.addAll(synsetMap.getOrDefault(hyponymId, Collections.emptySet())));
                });
        return hyponyms;
    }
}
