package com.wslfinc.cf.sdk.rating;

import static com.wslfinc.cf.sdk.CodeForcesSDK.getActiveContestants;
import com.wslfinc.cf.sdk.entities.additional.Contestant;
import com.wslfinc.cf.sdk.entities.additional.ContestantResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

/**
 *
 * @author Wsl_F
 */
public class RatingCalculator {

    private int minDelta = 300;
    private int maxDelta = 700;

    public void setMinDelta(int minDelta) {
        this.minDelta = minDelta;
    }

    public void setMaxDelta(int maxDelta) {
        this.maxDelta = maxDelta;
    }

    int numberOfContestants;
    ArrayList<Contestant> allContestants;

    public RatingCalculator(List<Contestant> allContestants) {
        this.allContestants = (ArrayList<Contestant>) allContestants;
        this.numberOfContestants = allContestants.size();
        recalculateRanks();
        Collections.sort(this.allContestants);
        Collections.reverse(allContestants);
    }

    public static RatingCalculator getRatingCalculator(int contestId) {
        List<Contestant> active = getActiveContestants(contestId);
        return new RatingCalculator(active);
    }

    private void recalculateRanks() {
        Comparator<Contestant> rankComp = new Comparator<Contestant>() {
            @Override
            public int compare(Contestant contestant1, Contestant contestan2) {
                return Integer.compare(contestant1.getRank(), contestan2.getRank());
            }
        };

        Collections.sort(allContestants, rankComp);

        {
            int i = 0;
            while (i < numberOfContestants) {
                int first = i;
                int last = first;
                int rank = allContestants.get(first).getRank();
                while (last < numberOfContestants
                        && allContestants.get(last).getRank() == rank) {
                    last++;
                }
                rank = last;
                for (int j = first; j < last; j++) {
                    allContestants.get(j).setRank(rank);
                }
                i = last;
            }
        }
    }

    private static double getEloWinProbability(double ra, double rb) {
        return 1.0 / (1 + Math.pow(10, (rb - ra) / 400.0));
    }

    private double getSeed(Contestant contestant) {
        double seed = 1;
        int rating = contestant.getPrevRating();
        for (Contestant opponent : allContestants) {
            if (contestant != opponent) {
                seed += getEloWinProbability(opponent.getPrevRating(), rating);
            } 
        }

        return seed;
    }

    private double getSeed(int rating, Contestant contestant) {
        double seed = 1;
        for (Contestant opponent : allContestants) {
            if (opponent != contestant) {
                seed += getEloWinProbability(opponent.getPrevRating(), rating);
            }
        }

        return seed;
    }

    private double getAverageRank(Contestant contestant) {
        double realRank = contestant.getRank();
        double expectedRank = getSeed(contestant);
        double average = Math.sqrt(realRank * expectedRank);

        return average;
    }

    private int getRatingToRank(Contestant contestant) {
        double averageRank = getAverageRank(contestant);

        int left = contestant.getPrevRating() - 2 * minDelta;
        int right = contestant.getPrevRating() + 2 * maxDelta;

        while (right - left > 1) {
            int mid = (left + right) / 2;
            double seed = getSeed(mid, contestant);
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
            int expR = getRatingToRank(contestant);
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
                int delta = deltas.get(i) + inc;
                deltas.set(i, delta);
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
                int delta = deltas.get(i) + inc;
                deltas.set(i, delta);
            }
        }

        return deltas;
    }

    public List<ContestantResult> getNewRatings() {
        if (numberOfContestants < 2) {
            return new ArrayList<>();
        }
        
        ArrayList<Integer> deltas = calculateDeltas();

        List<ContestantResult> results = new ArrayList<>(numberOfContestants);

        for (int i = 0; i < numberOfContestants; i++) {
            Contestant contestant = allContestants.get(i);
            int nextRating = contestant.getPrevRating() + deltas.get(i);
            results.add(new ContestantResult(contestant, getSeed(contestant), nextRating));
        }

        return results;
    }
}
