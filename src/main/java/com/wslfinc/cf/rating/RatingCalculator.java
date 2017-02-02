package com.wslfinc.cf.rating;

import com.wslfinc.cf.sdk.entities.additional.Contestant;
import com.wslfinc.cf.sdk.entities.additional.ContestantResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wsl_F
 */
public class RatingCalculator {

    int numberOfContestants;
    ArrayList<Contestant> allContestants;
    Map<String, Double> seeds;

    public RatingCalculator(List<Contestant> allContestants) {
        this.allContestants = (ArrayList<Contestant>) allContestants;
        Collections.sort(this.allContestants);
        Collections.reverse(allContestants);
        this.numberOfContestants = allContestants.size();

        this.seeds = null;
    }

    private static double getEloWinProbability(double ra, double rb) {
        return 1.0 / (1 + Math.pow(10, (rb - ra) / 400.0));
    }

    private double getSeed(Contestant contestant) {
        String handle = contestant.getHandle();
        int rating = contestant.getPrevRating();
        return getSeed(handle, rating);
    }

    private double getSeed(String handle, int rating) {
        double seed = 1;
        for (Contestant opponent : allContestants) {
            if (!handle.equals(opponent.getHandle())) {
                seed += getEloWinProbability(opponent.getPrevRating(), rating);
            }
        }

        return seed;
    }

    public void calculateSeeds() {
        seeds = new HashMap<>();
        for (Contestant contestant : allContestants) {
            double seed = getSeed(contestant);
            String handle = contestant.getHandle();
            seeds.put(handle, seed);
        }
    }

    public Map<String, Double> getSeeds() {
        if (seeds == null) {
            calculateSeeds();
        }

        return seeds;
    }

    private double getAverageRank(Contestant contestant) {
        double realRank = contestant.getRank();
        double expectedRank = seeds.get(contestant.getHandle());
        double average = Math.sqrt(realRank * expectedRank);

        return average;
    }

    private int getRatingExpection(Contestant contestant) {
        double averageRank = getAverageRank(contestant);

        int left = -1_000;
        int right = 10_000;
        String handle = contestant.getHandle();

        while (right - left > 1) {
            int mid = (left + right) / 2;
            double seed = getSeed(handle, mid);
            if (seed < averageRank) {
                right = mid;
            } else {
                left = mid;
            }
        }

        return left;
    }

    private ArrayList<Integer> calculateDeltas() {
        ArrayList<Integer> deltas = new ArrayList<>();

        for (int i = 0; i < numberOfContestants; i++) {
            Contestant contestant = allContestants.get(i);
            int expR = getRatingExpection(contestant);
            deltas.add((expR - contestant.getPrevRating()) / 2);
        }

        // Total sum should not be more than zero.
        {
            int sum = 0;
            for (int delta : deltas) {
                sum += delta;
            }
            int inc = -sum / numberOfContestants - 1;
            for (int i = 0; i < numberOfContestants; i++) {
                deltas.set(i, deltas.get(i) + inc);
            }
        }

        // Sum of top-4*sqrt should be adjusted to zero.
        {
            int sum = 0;
            int zeroSumCount = Math.min((int) (4 * Math.round(Math.sqrt(numberOfContestants))), numberOfContestants);

            for (int i = 0; i < zeroSumCount; i++) {
                sum += deltas.get(i);
            }
            int inc = Math.min(Math.max(-sum / zeroSumCount, -10), 0);
            for (int i = 0; i < numberOfContestants; i++) {
                deltas.set(i, deltas.get(i) + inc);
            }
        }

        return deltas;
    }

    public List<ContestantResult> getNewRatings() {
        getSeeds();

        ArrayList<Integer> deltas = calculateDeltas();

        List<ContestantResult> results = new ArrayList<>(numberOfContestants);

        for (int i = 0; i < numberOfContestants; i++) {
            Contestant contestant = allContestants.get(i);
            int nextRating = contestant.getPrevRating() + deltas.get(i);
            results.add(new ContestantResult(contestant, seeds.get(contestant.getHandle()), nextRating));
        }

        return results;
    }
}
