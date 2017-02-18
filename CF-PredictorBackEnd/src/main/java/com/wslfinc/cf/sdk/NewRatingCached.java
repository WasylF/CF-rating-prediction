package com.wslfinc.cf.sdk;

import com.wslfinc.cf.sdk.entities.additional.ContestantResult;
import com.wslfinc.cf.sdk.rating.RatingCalculator;
import java.util.List;
import com.github.wslf.datastructures.cache.Cacheable;

/**
 *
 * @author Wsl_F
 */
public class NewRatingCached extends Cacheable<List<ContestantResult>, Integer> {

    public NewRatingCached(long timeToUpdate) {
        super(10, 2, timeToUpdate);
    }

    @Override
    public List<ContestantResult> getValueManually(Integer key) {
        RatingCalculator ratingCalculator = RatingCalculator.getRatingCalculator(key);
        long before = System.currentTimeMillis();
        List<ContestantResult> result = ratingCalculator.getNewRatings();
        long after = System.currentTimeMillis();
        System.out.println("Rating for contest #" + key
                + " calculated in: " + (after - before) + " millis");
        return result;
    }

}
