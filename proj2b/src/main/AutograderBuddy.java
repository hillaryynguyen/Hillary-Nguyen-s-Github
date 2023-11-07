package main;

import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import main.HyponymsHandler;
import java.io.IOException;

public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) throws IOException{

        NGramMap ngm = new NGramMap(wordFile, countFile);
        return new HyponymsHandler(synsetFile,hyponymFile,ngm);
    }
}
