package main;

import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        NGramMap ngm = new NGramMap(wordFile, countFile);
        return new HyponymsHandler(synsetFile,hyponymFile,ngm);
    }
}
