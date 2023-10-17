package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import java.util.List;
import java.util.ArrayList;
import plotting.Plotter;


public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap ngramMap;

    public HistoryHandler(NGramMap map) {
        ngramMap = map;
    }
    @Override
    public String handle(NgordnetQuery query) {
        List<String> words = query.words();
        int startYear = query.startYear();
        int endYear = query.endYear();

        ArrayList<TimeSeries> tsList = new ArrayList<>();
        for (String word: words) {
            tsList.add(ngramMap.weightHistory(word, startYear, endYear));
        }
        return Plotter.encodeChartAsString(Plotter.generateTimeSeriesChart(words, tsList));


    }

}
