package ngrams;

import java.util.Collection;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashSet;
import edu.princeton.cs.algs4.In;
import java.util.List;
import java.util.ArrayList;


public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    In words, counts;
    TimeSeries yearTotals;
    TreeMap<String, TimeSeries> wordMap;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        yearTotals = new TimeSeries();
        wordMap = new TreeMap<>();
        counts = new In(countsFilename);
        words = new In(wordsFilename);

        while (words.hasNextLine()) {
            String nextLine = words.readLine();
            String[] wSplitLine = nextLine.split("\t");

            String word = wSplitLine[0];
            int year = Integer.parseInt(wSplitLine[1]);
            double numAppears = Double.parseDouble(wSplitLine[2]);

            if (wordMap.get(word) == null) {
                TimeSeries newWordData = new TimeSeries();
                wordMap.put(word, newWordData);
            }

            TimeSeries thisWordsData = wordMap.get(word);
            thisWordsData.put(year, numAppears);
        }
    }

    public List<Integer> years() {
        List<Integer> yearList = new ArrayList<>();
        for (TimeSeries ts : wordMap.values()) {
            yearList.addAll(ts.years());
        }
        return yearList;
    }

    public TimeSeries countHistory (String word,int startYear, int endYear){
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }

        TimeSeries wordData = new TimeSeries(wordMap.get(word), startYear, endYear); // Make a copy
        return wordData;
    }

    public TimeSeries countHistory (String word) {
        return countHistory(word, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public TimeSeries totalCountHistory () {
        if (yearTotals.isEmpty()) {
            for (TimeSeries wordData : wordMap.values()) {
                yearTotals = yearTotals.plus(wordData);
            }
        }

        int startYear = yearTotals.firstKey();
        int endYear = yearTotals.lastKey();

        return new TimeSeries(yearTotals, startYear, endYear);
    }

    public TimeSeries weightHistory (String word,int startYear, int endYear){
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }

        TimeSeries wordData = wordMap.get(word);
        TimeSeries subseries = new TimeSeries(wordData, startYear, endYear);

        TimeSeries totalWords = new TimeSeries(yearTotals, startYear, endYear);
        return subseries.dividedBy(totalWords);
    }

    public TimeSeries weightHistory (String word){
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }

        TimeSeries wordData = wordMap.get(word);
        return wordData;
    }

    public TimeSeries summedWeightHistory (Collection < String > words,int startYear, int endYear){
        TimeSeries summedHistory = new TimeSeries();

        for (int year = startYear; year <= endYear; year++) {
            double totalRelativeFrequency = 0.0;
            for (String word : words) {
                if (wordMap.containsKey(word) && yearTotals.containsKey(year)) {
                    TimeSeries data = wordMap.get(word);
                    double count = data.containsKey(year) ? data.get(year) : 0.0;
                    double totalWords = yearTotals.get(year);

                    double relativeFrequency = (totalWords != 0.0) ? count / totalWords : 0.0;
                    totalRelativeFrequency += relativeFrequency;
                }
            }

            summedHistory.put(year, totalRelativeFrequency);
        }

        return summedHistory;
    }

    public TimeSeries summedWeightHistory (Collection < String > words) {
        TimeSeries summedHistory = new TimeSeries();

        for (int year : years()) {
            double totalRelativeFrequency = 0.0;
            for (String word : words) {
                if (wordMap.containsKey(word)) {
                    TimeSeries wordData = wordMap.get(word);
                    double count = wordData.containsKey(year) ? wordData.get(year) : 0.0;
                    double totalWords = yearTotals.containsKey(year) ? yearTotals.get(year) : 0.0;

                    double relativeFrequency = (totalWords != 0.0) ? count / totalWords : 0.0;
                    totalRelativeFrequency += relativeFrequency;
                }
            }

            summedHistory.put(year, totalRelativeFrequency);
        }

        return summedHistory;
    }

    private int getFirstYear() {
        if (yearTotals.isEmpty()) {
            return 0;
        }
        return yearTotals.firstKey();
    }

    private int getLastYear() {
        if (yearTotals.isEmpty()) {
            return 0;
        }
        return yearTotals.lastKey();
    }
}
