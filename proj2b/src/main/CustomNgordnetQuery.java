package main;

import browser.NgordnetQueryHandler;

public class CustomNgordnetQuery extends NgordnetQueryHandler {
    public CustomNgordnetQuery(String words, int startYear, int endYear, int k) {
        super(words, startYear, endYear, k);
    }

    // Custom method to access the startYear
    public int getCustomStartYear() {
        return this.getStartYear();
    }
}
