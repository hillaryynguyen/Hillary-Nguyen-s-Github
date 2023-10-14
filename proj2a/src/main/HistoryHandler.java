package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngordnet.Plotter;
import ngordnet.TimeSeries;


public class HistoryHandler implements NgordnetQueryHandler {
    private final NGramMap nGramMap;

    public HistoryHandler(NGramMap nGramMap) {
        this.nGramMap = nGramMap;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        // Create a list to hold the TimeSeries data for the specified words
        List<TimeSeries> timeSeriesList = nGramMap.buildTimeSeries(words, startYear, endYear);

        // Generate a chart with the retrieved data
        Plotter.plotTS(timeSeriesList, words, "Year", "Relative Frequency");

        // Encode the chart as a base-64 string
        String base64EncodedImage = Plotter.getChartString();

        return base64EncodedImage;
    }
}
