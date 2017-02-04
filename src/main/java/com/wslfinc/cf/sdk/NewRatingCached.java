package com.wslfinc.cf.sdk;

import com.wslfinc.cf.sdk.entities.additional.ContestantResult;
import com.wslfinc.cf.sdk.rating.RatingCalculator;
import java.util.List;

/**
 *
 * @author Wsl_F
 */
public class NewRatingCached extends Cacheble<ContestantResult> {

    @Override
    protected List<ContestantResult> getStraight(int contestId) {
        RatingCalculator ratingCalculator = RatingCalculator.getRatingCalculator(contestId);
        return ratingCalculator.getNewRatings();
    }

}
