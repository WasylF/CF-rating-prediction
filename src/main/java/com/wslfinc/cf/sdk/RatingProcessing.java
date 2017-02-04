package com.wslfinc.cf.sdk;

import com.wslfinc.cf.sdk.entities.additional.ContestantResult;
import java.util.List;

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
}
