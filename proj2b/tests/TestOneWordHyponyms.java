import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import org.junit.jupiter.api.Test;
import main.AutograderBuddy;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Tests the most basic case for Hyponyms where the list of words is one word long, and k = 0.*/
public class TestOneWordHyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    public static final String WORDS_FILE = "data/ngrams/top_49887_words.csv";
    public static final String SMALL_WORDS_FILE = "data/ngrams/very_short.csv";

    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String HYPONYM_FILE = "data/wordnet/hyponyms.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";


    @Test
    public void testOccurrenceAndChangeK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                SMALL_WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("occurrence", "change");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[\"alteration\",\"change\",\"increase\",\"jump\",\"leap\",\"modification\",\"saltation\",\"transition\"]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testActK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                SMALL_WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("act");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[\"act\",\"action\",\"change\",\"demotion\",\"human_action\",\"human_activity\",\"variation\"]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testSingleWordHyponymsWithK() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SYNSET_FILE, HYPONYM_FILE);
        // Setup your handler with necessary file paths
        NgordnetQuery query = new NgordnetQuery(List.of("food"), 1950, 1990, 5);
        String actual = studentHandler.handle(query);
        String expected = "[\"course\",\"date\",\"must\",\"special\",\"water\"]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testHyponymsWithNonexistentWord() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        NgordnetQuery query = new NgordnetQuery(List.of("nonexistentword"), 0, 0, 0);
        String actual = studentHandler.handle(query);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testHyponymsWithInvalidK() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        NgordnetQuery query = new NgordnetQuery(List.of("change"), 0, 0, -5);
        String actual = studentHandler.handle(query);
        String expected = "[\"alteration\",\"change\",\"demotion\",\"increase\",\"jump\",\"leap\",\"modification\",\"saltation\",\"transition\",\"variation\"]";
        assertThat(actual).isEqualTo(expected);
    }


}
