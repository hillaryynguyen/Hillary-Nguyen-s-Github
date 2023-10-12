package ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
        // TODO: Fill in this constructor.
        for (int year = startYear; year <= endYear; year++) {
            if (ts.containsKey(year)) {
                this.put(year, ts.get(year));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        // TODO: Fill in this method.
        List<Integer> yearList = new ArrayList<>();

        for (int year : keySet()) {
            yearList.add(year);
        }
        return yearList;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        // TODO: Fill in this method.
        List<Double> dataList = new ArrayList<>();
        for (int year : this.years()) {
            dataList.add(this.get(year));
        }
        return dataList;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();
        for (int year : this.years()) {
            double thisValue = this.get(year);
            double tsValue = ts.containsKey(year) ? ts.get(year) : 0.0; // Handle missing years
            result.put(year, thisValue + tsValue);
        }
        for (int year : ts.years()) {
            if (!this.containsKey(year)) {
                // Handle missing years in 'this' TimeSeries
                result.put(year, ts.get(year));
            }
        }
        return result;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();
        for (int year : this.years()) {
            if (!ts.containsKey(year)) {
                throw new IllegalArgumentException("Year " + year + " not found in the provided TimeSeries");
            }
            double thisValue = this.get(year);
            double tsValue = ts.get(year);
            result.put(year, thisValue / tsValue);
        }
        for (int year : ts.years()) {
            if (!this.containsKey(year)) {
                // Handle missing years in 'this' TimeSeries
                throw new IllegalArgumentException("Year " + year + " not found in 'this' TimeSeries");
            }
        }
        return result;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
