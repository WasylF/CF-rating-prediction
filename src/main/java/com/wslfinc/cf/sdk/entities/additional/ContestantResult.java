package com.wslfinc.cf.sdk.entities.additional;

/**
 *
 * @author Wsl_F
 */
public class ContestantResult {

    Contestant contestant;
    double seed;
    int nextRating;

    public ContestantResult(Contestant contestant, double seed, int nextRating) {
        this.contestant = contestant;
        this.seed = seed;
        this.nextRating = nextRating;
    }

    public double getSeed() {
        return seed;
    }

    public int getNextRating() {
        return nextRating;
    }

    public String getHandle() {
        return contestant.handle;
    }

    public int getRank() {
        return contestant.rank;
    }

    public int getPrevRating() {
        return contestant.prevRating;
    }
}
