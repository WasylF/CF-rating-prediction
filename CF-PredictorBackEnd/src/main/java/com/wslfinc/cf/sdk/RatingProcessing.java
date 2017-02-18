package com.wslfinc.cf.sdk;

import com.wslfinc.cf.sdk.entities.additional.ContestantResult;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Wsl_F
 */
public class RatingProcessing {
    
    private static NewRatingCached newRatings = new NewRatingCached();

    /**
     * Calculates rating after round {@code  contestId}
     *
     * @param contestId
     * @return nextRating for all contestants
     */
    public static List<ContestantResult> getNewRatings(int contestId) {
        return newRatings.getValue(contestId);
    }
    
    public static Set<Integer> getCachedIds() {
        return newRatings.getCachedKeys();
    }
    
    public static void removeFromCache(int contestId) {
        newRatings.remove(contestId);
    }
}
