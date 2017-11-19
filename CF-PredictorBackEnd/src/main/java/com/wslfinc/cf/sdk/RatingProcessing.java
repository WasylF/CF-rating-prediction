package com.wslfinc.cf.sdk;

import com.wslfinc.cf.sdk.entities.additional.ContestantResult;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Wsl_F
 */
public class RatingProcessing {

    private static volatile NewRatingCached newRatings = new NewRatingCached(30_000);

    /**
     * Calculates rating after round {@code  contestId}
     *
     * @param contestId
     * @return nextRating for all contestants
     */
    public static List<ContestantResult> getNewRatings(int contestId) {
        List<ContestantResult> result = null;
        result = newRatings.getValue(contestId);
        return result;
    }

    public static Set<Integer> getCachedIds() {
        return newRatings.getCachedKeys();
    }

    public static void removeFromCache(int contestId) {
        newRatings.remove(contestId);
    }

    public static void changeTimeToUpdate(long timeToUpdate) {
        newRatings = new NewRatingCached(timeToUpdate);
    }
}
