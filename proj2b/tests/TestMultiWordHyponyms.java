/*import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import main.AutograderBuddy;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Tests the case where the list of words is length greater than 1, but k is still zero. */
/*
public class TestMultiWordHyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    public static final String WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String LARGE_WORDS_FILE = "data/ngrams/top_49887_words.csv";

    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String LARGE_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String LARGE_HYPONYM_FILE = "data/wordnet/hyponyms.txt";

    /** This is an example from the spec.*/
    /*
    @Test
    public void testOccurrenceAndChangeK0() throws IOException {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("occurrence", "change");

        NgordnetQuery query = new NgordnetQuery(List.of("change", "occurrence"), 0, 0, 0);
        String actual = studentHandler.handle(query);
        String expected = "[\"alteration\",\"change\",\"increase\",\"jump\",\"leap\",\"modification\",\"saltation\",\"transition\"]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testMultipleWordHyponymsWithK() throws IOException {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE,LARGE_HYPONYM_FILE);
        NgordnetQuery query = new NgordnetQuery(List.of("food","cake"), 1950, 1990, 5);
        String actual = studentHandler.handle(query);
        String expected = "[\"biscuit\",\"cake\",\"kiss\",\"snap\",\"wafer\"]";
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void testMultipleWordsHyponymsWithK0() throws IOException {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        NgordnetQuery query = new NgordnetQuery(List.of("change", "occurrence"), 0, 0, 0);
        String actual = studentHandler.handle(query);
        String expected = "[\"alteration\",\"change\",\"increase\",\"jump\",\"leap\",\"modification\",\"saltation\",\"transition\"]";
        assertThat(actual).isEqualTo(expected);
    }




}
