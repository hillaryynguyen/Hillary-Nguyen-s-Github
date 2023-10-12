package main;

import browser.NgordnetQuery;
import edu.stanford.cs.introcs.StdStats;
import ngordnet.NGramMap;
import ngordnet.Plotter;
import ngordnet.TimeSeries;

import java.util.List;

public class HistoryHandler implements NgordnetQueryHandler {
    private final NGramMap ngm;

    public HistoryHandler(NGramMap map) {
        ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        // Create a list to hold the TimeSeries data for the specified words
        List<TimeSeries> timeSeriesList = ngm.buildTimeSeries(words, startYear, endYear);

        // Create a list of labels for the time series
        List<String> labels = words;

        // Generate a chart with the retrieved data
        Plotter.plotTS(timeSeriesList, labels, "Year", "Relative Frequency");

        // Encode the chart as a base-64 string
        String base64EncodedImage = Plotter.getChartString();

        return base64EncodedImage;
    }
}
