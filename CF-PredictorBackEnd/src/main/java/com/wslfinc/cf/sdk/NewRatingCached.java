package com.wslfinc.cf.sdk;

import com.wslfinc.cf.sdk.entities.additional.ContestantResult;
import java.util.List;
import com.github.wslf.datastructures.cache.Cacheable;
import com.wslfinc.cf.sdk.rating.RatingCalculatorTeam;

/**
 *
 * @author Wsl_F
 */
public class NewRatingCached extends Cacheable<List<ContestantResult>, Integer> {

    public NewRatingCached(long timeToUpdate) {
        super(10, 3, timeToUpdate);
    }
    
    @Override
    public List<ContestantResult> getValueManually(Integer key) {
        long before = System.currentTimeMillis();
        RatingCalculatorTeam ratingCalculator = RatingCalculatorTeam.getRatingCalculator(key);
        //RatingCalculator ratingCalculator = RatingCalculator.getRatingCalculator(key);
        long before2 = System.currentTimeMillis();
        List<ContestantResult> result = ratingCalculator.getNewRatings();
        long after = System.currentTimeMillis();
        System.out.println("Rating for contest #" + key
                + " calculated total in: " + (after - before) + " millis"
                + ", excluding cf request: " + (after - before2));
        return result;
    }

}
