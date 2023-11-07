package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetServer;
import java.util.ArrayList;
import ngrams.NGramMap;

public class Main {
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();

        String wordFile = "./data/ngrams/top_49887_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        String synsets = "./data/wordnet/synsets.txt";
        String hyponyms = "./data/wordnet/hyponyms.txt";
        NGramMap ngm = new NGramMap(wordFile, countFile);


        hns.startUp();
        hns.register("history", new DummyHistoryHandler());
        hns.register("historytext", new DummyHistoryTextHandler());
        // Register your HyponymsHandler
        hns.register("hyponyms", new HyponymsHandler(synsets,hyponyms,ngm));


        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}
/* The following code might be useful to you.
        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        NGramMap ngm = new NGramMap(wordFile, countFile);
        */