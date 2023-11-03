package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetServer;

public class Main {
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();
        
        /* The following code might be useful to you.
        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        NGramMap ngm = new NGramMap(wordFile, countFile);
        */

        //create an instance of WordNetGraph with appropriate file paths
        WordNetGraph wordNetGraph = new WordNetGraph("synsets.txt", "hyponyms.txt");
        //create an instance of HyponymsHandler and pass the WordNetGraph instance
        HyponymsHandler hyponymsHandler = new HyponymsHandler(wordNetGraph);



        hns.startUp();
        hns.register("history", new DummyHistoryHandler());
        hns.register("historytext", new DummyHistoryTextHandler());
        hns.register("hyponyms", new NgordnetQueryHandler() {
            @Override
            public String handle(NgordnetQuery q) {
                return hyponymsHandler.handle(q);
            }
        });

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}
