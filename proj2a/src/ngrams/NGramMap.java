package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    private static final int MIN_YR = 1400;
    private static final int MAX_YR = 2100;
    private final Map<String, TimeSeries> wordMap;
    private final TimeSeries totalCH = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
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
        TimeSeries tSeriesCopy = wordMap.get(word);
        return new TimeSeries(tSeriesCopy, startYear, endYear);
    }


    public TimeSeries countHistory(String word) {
        return countHistory(word, MIN_YR, MAX_YR);
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
        TimeSeries sumWeight = new TimeSeries();
        for (String word: words) {
            if (wordMap.containsKey(word)) {
                TimeSeries wHist = weightHistory(word, startYear, endYear);
                sumWeight = sumWeight.plus(wHist);
            }

        }
        return sumWeight;
    }

    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YR, MAX_YR);
    }
}

