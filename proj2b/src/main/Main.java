package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetServer;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();
        
        /* The following code might be useful to you.
        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        NGramMap ngm = new NGramMap(wordFile, countFile);
        */

        //create an instance of WordNetGraph with appropriate file paths
        WordNetGraph wordNetGraph = new WordNetGraph("synsets.txt", "hyponyms.txt", "wordFrequencies.txt");
        //create an instance of HyponymsHandler and pass the WordNetGraph instance
        HyponymsHandler hyponymsHandler = new HyponymsHandler(wordNetGraph);



        hns.startUp();
        hns.register("history", new DummyHistoryHandler());
        hns.register("historytext", new DummyHistoryTextHandler());
        hns.register("hyponyms", new NgordnetQueryHandler() {
            @Override
            public String handle(browser.NgordnetQuery q) {
                // You can parse the query to get words, startYear, endYear, and k
                ArrayList<String> words = new ArrayList<>(q.words());
                int startYear = q.startYear();
                int endYear = q.endYear();
                int k = q.k();

                // If needed, you can handle the time frame (startYear and endYear) here
                // The current implementation of HyponymsHandler doesn't use time filtering

                NgordnetQuery newQuery = new NgordnetQuery(words, startYear, endYear, k);

                // Call the HyponymsHandler to get hyponyms
                String result = hyponymsHandler.handle(newQuery);

                return result;
            }
        });

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}
