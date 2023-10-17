package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import java.util.List;


public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap ngramMap;

    public HistoryTextHandler(NGramMap map) {
        ngramMap = map;
    }
    @Override
    public String handle(NgordnetQuery query) {
        List<String> words = query.words();
        int startYear = query.startYear();
        int endYear = query.endYear();

        String result = "";
        for (String word : words) {
            TimeSeries ts = ngramMap.weightHistory(word, startYear, endYear);
            result += word + ": " + ts.toString() + "\n";
        }
        return result;
    }
}
