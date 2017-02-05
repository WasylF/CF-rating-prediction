package com.wslfinc.cf.sdk;

import com.wslfinc.cf.sdk.entities.additional.ContestantResult;
import com.wslfinc.cf.sdk.rating.RatingCalculator;
import java.util.List;

/**
 *
 * @author Wsl_F
 */
public class NewRatingCached extends Cacheble<ContestantResult> {

    public NewRatingCached() {
        // TTL 30 sec
        super(30_000);
    }

    
    @Override
    protected List<ContestantResult> getStraight(int contestId) {
        long before = System.currentTimeMillis();
        RatingCalculator ratingCalculator = RatingCalculator.getRatingCalculator(contestId);
        List<ContestantResult> result = ratingCalculator.getNewRatings();
        long after = System.currentTimeMillis();
        System.out.println("Rating for contest #" + contestId 
                + " calculated in: " + (after-before) + " millis");
        return result;
    }

}
