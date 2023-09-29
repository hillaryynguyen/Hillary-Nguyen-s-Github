package gh2;

import java.util.LinkedList;

import deque.ArrayDeque;
import deque.Deque;

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor
    private static final int SAMPLE_RATE = 44100;

    /* Buffer for storing sound data. */
    private Deque<Double> buffer;


    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        buffer = new ArrayDeque<>();
        int cap = (int) Math.round(SR/frequency);
        for (int x = 0; x < cap; x++) {
            buffer.addFirst(0.0);
        }

    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        for (int x = 0; x < buffer.size(); x++) {
            double r = Math.random() - 0.5;
            buffer.removeFirst(); // Remove the old value
            buffer.addFirst(r);

        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double one = buffer.get(0);
        double two = buffer.get(1);
        double avg = ((one + two) / 2) * DECAY;
        buffer.removeFirst();
        buffer.addLast(avg);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}
