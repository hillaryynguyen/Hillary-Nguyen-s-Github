package ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;


/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();

        copyTimeSeriesWithinRange(ts, startYear, endYear);
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        List<Integer> yearList = new ArrayList<>(keySet());
        return yearList;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> dataList = new ArrayList<>();
        for (int year : years()) {
            dataList.add(get(year));
        }
        return dataList;
    }
    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     * <p>
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries result = new TimeSeries(ts, MIN_YEAR, MAX_YEAR);
        for (int year : this.years()) {
            double thisValue = this.get(year);
            double tsValue = ts.containsKey(year) ? ts.get(year) : 0.0; // Handle missing years
            result.put(year, thisValue + tsValue);
        }
        return result;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     * <p>
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries result = new TimeSeries();

        for (int year : years()) {
            if (!ts.containsKey(year)) {
                throw new IllegalArgumentException("Missing year in TS: " + year);
            }

            double thisValue = get(year);
            double tsValue = ts.get(year);

            if (tsValue == 0.0) {
                throw new IllegalArgumentException("Division by zero at year " + year);
            }

            result.put(year, thisValue / tsValue);
        }

        return result;
    }

    // Private helper method to copy TimeSeries within a specified range of years.
    private void copyTimeSeriesWithinRange(TimeSeries ts, int startYear, int endYear) {
        for (int year : ts.keySet()) {
            if (year >= startYear && year <= endYear) {
                put(year, ts.get(year));
            }
        }
    }
}

