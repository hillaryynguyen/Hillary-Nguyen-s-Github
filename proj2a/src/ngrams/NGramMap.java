package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class NGramMap {
    private static final int MIN_YR = 1400;
    private static final int MAX_YR = 2100;
    private final Map<String, TimeSeries> wordMap;
    private final TimeSeries totalCH = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        wordMap = new HashMap<>();
        In wordFile = new In(wordsFilename);
        In countFile = new In(countsFilename);
        while (wordFile.hasNextLine()) {
            String[] lineList = wordFile.readLine().split("\\t");
            String fWord = lineList[0];
            int year1 = Integer.parseInt(lineList[1]);
            Double theCount = Double.parseDouble(lineList[2]);
            if (year1 >= MIN_YR && year1 <= MAX_YR) {
                if (!wordMap.containsKey(fWord)) {
                    wordMap.put(fWord, new TimeSeries());
                }
                wordMap.get(fWord).put(year1, theCount);
            }
        }
        while (countFile.hasNextLine()) {
            String[] lineList = countFile.readLine().split(",");
            int year2 = Integer.parseInt(lineList[0]);
            double totalCount = Double.parseDouble(lineList[1]);
            if (year2 >= MIN_YR && year2 <= MAX_YR) {
                totalCH.put(year2, totalCount);
            }
        }
    }


    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        for (int year = startYear; year <= endYear; year++) {
            if (wordMap.get(word).get(year) != null) {
                ts.put(year, wordMap.get(word).get(year));
            }
        }
        return ts;
    }


    public TimeSeries countHistory(String word) {
        if (wordMap.containsKey(word)) {
            return wordMap.get(word);
        }
        return new TimeSeries();
    }

    public TimeSeries totalCountHistory() {
        TimeSeries totalCHCopy = (TimeSeries) totalCH.clone();
        return totalCHCopy;
    }


    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordCount = countHistory(word, startYear, endYear);
        TimeSeries totalCount = new TimeSeries(totalCH, startYear, endYear);
        return wordCount.dividedBy(totalCount);
    }

    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YR, MAX_YR);
    }

    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries summedWeight = new TimeSeries();
        for (String word: words) {
            if (wordMap.containsKey(word)) {
                TimeSeries wHist = weightHistory(word, startYear, endYear);
                summedWeight = summedWeight.plus(wHist);
            }

        }
        return summedWeight;
    }

    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YR, MAX_YR);
    }
}

