package ngrams;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.util.Collection;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.*;


import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    private final Map<String, Map<Integer, Integer>> wordData;
    private final Map<Integer, Integer> totalCountData;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        // Initialize data structures
        wordData = new HashMap<>();
        totalCountData = new HashMap<>();

        // Read data from words file
        try {
            // Read data from words file
            BufferedReader wordsReader = new BufferedReader(new FileReader(new File(wordsFilename)));
            String line;
            while ((line = wordsReader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 3) {
                    String word = parts[0];
                    try {
                        int year = Integer.parseInt(parts[1]);
                        int count = Integer.parseInt(parts[2]);
                        wordData.computeIfAbsent(word, k -> new HashMap<>()).put(year, count);
                    } catch (NumberFormatException e) {
                        // Handle invalid data
                        e.printStackTrace();
                    }
                }
            }
            wordsReader.close();

            // Read data from counts file
            BufferedReader countsReader = new BufferedReader(new FileReader(new File(countsFilename)));
            while ((line = countsReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    int year = Integer.parseInt(parts[0]);
                    int totalWords = Integer.parseInt(parts[1]);
                    totalCountData.put(year, totalWords);
                }
            }
            countsReader.close();
        } catch (IOException e) {
            // Handle file I/O exception
            e.printStackTrace();
        }
    }




    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if (!wordData.containsKey(word)) {
            return new TimeSeries();
        }

        Map<Integer, Integer> data = wordData.get(word);
        return createTimeSeriesFromMap(data, startYear, endYear);
    }
    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        if (!wordData.containsKey(word)) {
            return new TimeSeries();
        }

        Map<Integer, Integer> data = wordData.get(word);
        return createTimeSeriesFromMap(data);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        return createTimeSeriesFromMap(totalCountData);
    }

    private TimeSeries createTimeSeriesFromMap(Map<Integer, Integer> data) {
        TimeSeries timeSeries = new TimeSeries();
        for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
            timeSeries.put(entry.getKey(), (double) entry.getValue());
        }
        return timeSeries;
    }

    private TimeSeries createTimeSeriesFromMap(Map<Integer, Integer> data, int startYear, int endYear) {
        TimeSeries timeSeries = new TimeSeries();
        for (int year = startYear; year <= endYear; year++) {
            if (data.containsKey(year)) {
                timeSeries.put(year, (double) data.get(year));
            }
        }
        return timeSeries;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if (!wordData.containsKey(word)) {
            return new TimeSeries();
        }

        TimeSeries wordHistory = new TimeSeries();
        for (int year = startYear; year <= endYear; year++) {
            int count = wordData.get(word).getOrDefault(year, 0);
            int totalWords = totalCountData.getOrDefault(year, 0);

            if (totalWords != 0) {
                double relativeFrequency = (double) count / totalWords;
                wordHistory.put(year, relativeFrequency);
            } else {
                // Handle division by zero or missing data
                wordHistory.put(year, 0.0);
            }
        }

        return wordHistory;
    }



    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        if (!wordData.containsKey(word)) {
            return new TimeSeries();
        }

        Map<Integer, Integer> data = wordData.get(word);
        TimeSeries wordHistory = createTimeSeriesFromMap(data);

        TimeSeries normalizedWordHistory = new TimeSeries();
        for (int year : wordHistory.years()) {
            int count = wordHistory.get(year).intValue();
            int totalWords = totalCountData.getOrDefault(year, 0);

            if (totalWords != 0) {
                double relativeFrequency = (double) count / totalWords;
                normalizedWordHistory.put(year, relativeFrequency);
            } else {
                normalizedWordHistory.put(year, 0.0);
            }
        }

        return normalizedWordHistory;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries summedHistory = new TimeSeries();

        for (int year = startYear; year <= endYear; year++) {
            double totalRelativeFrequency = 0.0;
            for (String word : words) {
                if (wordData.containsKey(word)) {
                    int count = wordData.get(word).getOrDefault(year, 0);
                    int totalWords = totalCountData.getOrDefault(year, 0);

                    double relativeFrequency = (double) count / totalWords;
                    totalRelativeFrequency += relativeFrequency;
                }
            }

            summedHistory.put(year, totalRelativeFrequency);
        }

        return summedHistory;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries summedHistory = new TimeSeries();

        for (int year : totalCountData.keySet()) {
            double totalRelativeFrequency = 0.0;
            for (String word : words) {
                if (wordData.containsKey(word)) {
                    int count = wordData.get(word).getOrDefault(year, 0);
                    int totalWords = totalCountData.get(year);

                    double relativeFrequency = (double) count / totalWords;
                    totalRelativeFrequency += relativeFrequency;
                }
            }

            summedHistory.put(year, totalRelativeFrequency);
        }

        return summedHistory;
    }


    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
