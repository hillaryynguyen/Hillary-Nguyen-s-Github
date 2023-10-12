import ngrams.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;


import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }
    private TimeSeries ts1;  // Initialize the ts1 variable

    @BeforeEach
    void setUp() {
        ts1 = new TimeSeries();  // Initialize ts1 with a valid TimeSeries object
        // You can add data to ts1 as needed for your tests
        ts1.put(2000, 100.0);
        ts1.put(2001, 150.0);
        // Add more data if necessary
    }

    @Test
    void testPlus() {
        TimeSeries ts2 = new TimeSeries();
        ts2.put(2000, 50.0);
        ts2.put(2002, 75.0);

        TimeSeries result = ts1.plus(ts2);

        assertThat(result).isNotNull();
        assertThat(result.get(2000)).isEqualTo(150.0);  // Modify the expected value based on your data
        // Add more assertions as needed
    }

    @Test
    void testDividedBy() {
        TimeSeries ts2 = new TimeSeries();
        ts2.put(2000, 50.0);
        ts2.put(2001, 25.0);

        TimeSeries result = ts1.dividedBy(ts2);

        assertThat(result).isNotNull();
        assertThat(result.get(2000)).isEqualTo(2.0);  // Modify the expected value based on your data
        // Add more assertions as needed
    }

        // Other test methods

}