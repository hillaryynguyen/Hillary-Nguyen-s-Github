package ngrams;

import java.util.Collection;
import java.util.TreeMap;
import edu.princeton.cs.algs4.In;
import java.util.List;
import java.util.ArrayList;


public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    In words, counts;
    TimeSeries yearTotals;
    TreeMap<String, TimeSeries> wordMap;
    private final TimeSeries totalCountHistory = new TimeSeries();


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

    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }

        TimeSeries wordData = new TimeSeries(wordMap.get(word), startYear, endYear); // Make a copy
        return wordData;
    }

    public TimeSeries countHistory(String word) {
        return countHistory(word, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public TimeSeries totalCountHistory() {
        TimeSeries tCountHistorycopy = (TimeSeries) totalCountHistory.clone();
        return tCountHistorycopy;
    }

    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wCount = countHistory(word, startYear, endYear);
        TimeSeries totalCount = new TimeSeries(totalCountHistory, startYear, endYear);
        return wCount.dividedBy(totalCount);
    }

    public TimeSeries weightHistory(String word) {
        return weightHistory(word, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries summedHistory = new TimeSeries();

        for (String word : words) {
            if (wordMap.containsKey(word)) {
                TimeSeries wH = weightHistory(word, startYear, endYear);
                summedHistory = summedHistory.plus(wH);
            }
        }
        return summedHistory;
    }

    public TimeSeries summedWeightHistory (Collection < String > words) {
        return summedWeightHistory(words, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
