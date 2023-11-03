package main;

import browser.NgordnetQueryHandler;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        WordNetGraph wordNetGraph = new WordNetGraph(synsetFile, hyponymFile);

        // Create an instance of HyponymsHandler and pass the WordNetGraph instance
        HyponymsHandler hyponymsHandler = new HyponymsHandler(wordNetGraph);

        // Return the HyponymsHandler
        return hyponymsHandler;

        //throw new RuntimeException("Please fill out AutograderBuddy.java!");
    }
}
