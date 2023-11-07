package wordnet;

import java.util.HashSet;
import java.util.Set;

public class Synset {
    private int id;
    private Set<String> nouns;
    private String definition;

    public Synset(int id, Set<String> nouns, String definition) {
        this.id = id;
        this.nouns = nouns;
        this.definition = definition;
    }

    public int getId() {
        return id;
    }

    public Set<String> getNouns() {
        return nouns;
    }

    public String getDefinition() {
        return definition;
    }
}