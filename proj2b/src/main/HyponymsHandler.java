package main;

import browser.NgordnetQueryHandler;
import browser.NgordnetQuery;
import org.apache.commons.collections.set.SynchronizedSet;
import spark.QueryParamsMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import spark.Request;
import spark.Response;



public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNetGraph wordNetGraph;

    public HyponymsHandler(WordNetGraph wordNetGraph) {
        this.wordNetGraph = wordNetGraph;
    }

    @Override
    public String handle(NgordnetQuery q) {
        String wordsParam = q.get("words");

        if (wordsParam == null || wordsParam.isEmpty()) {
            return "Please enter one or more words.";
        }

        List<String> words = Arrays.asList(wordsParam.split(","));

        if (words.isEmpty()) {
            return "Please enter one or more words.";
        }

        Set<String> commonHyponyms = wordNetGraph.getHyponymsForWords(words);

        if (commonHyponyms.isEmpty()) {
            return "No common hyponyms found for the given words.";
        }

        List<String> sortedHyponyms = new ArrayList<>(commonHyponyms);
        Collections.sort(sortedHyponyms);

        return "Common Hyponyms: " + sortedHyponyms.toString();
    }

}
