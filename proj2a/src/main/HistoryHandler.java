package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import java.util.List;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.Styler;

public class HistoryHandler extends NgordnetQueryHandler {
    private final NGramMap nGramMap;

    public HistoryHandler(NGramMap nGramMap) {
        this.nGramMap = nGramMap;
    }

    @Override
    public String handle(NgordnetQuery q) {
        // Generate the chart using XChart library
        XYChart chart = createChart(q);

        // Encode the chart as a base-64 image
        String base64Image = encodeChartAsBase64(chart);

        return base64Image;
    }

    private XYChart createChart(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        XYChart chart = new XYChart(800, 400);

        // Set legend position
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);

        // Add data series to the chart
        for (String word : words) {
            TimeSeries wordTimeSeries = nGramMap.weightHistory(word, startYear, endYear);
            chart.addSeries(word, wordTimeSeries.years(), wordTimeSeries.data());
        }

        // Customize the chart further if needed

        return chart;
    }

    private String encodeChartAsBase64(XYChart chart) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            BitmapEncoder.saveBitmap(chart, stream, BitmapFormat.PNG);
            byte[] imageBytes = stream.toByteArray();
            return "data:image/png;base64," + java.util.Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ""; // Handle encoding error gracefully
        }
    }
}
