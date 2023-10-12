package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import java.util.List;


public class HistoryTextHandler extends NgordnetQueryHandler {
    private final NGramMap nGramMap;

    public HistoryTextHandler(NGramMap nGramMap) {
        this.nGramMap = nGramMap;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder result = new StringBuilder();
        result.append("You entered the following info into the browser:\n");
        result.append("Words: ").append(words).append("\n");
        result.append("Start Year: ").append(startYear).append("\n");
        result.append("End Year: ").append(endYear).append("\n");

        for (String word : words) {
            TimeSeries wordTimeSeries = nGramMap.weightHistory(word, startYear, endYear);
            result.append(word).append(": ").append(wordTimeSeries.toString()).append("\n");
        }

        return result.toString();
    }
}
