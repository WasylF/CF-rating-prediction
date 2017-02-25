package com.wslfinc.cf;

import static com.wslfinc.cf.Constants.JSON_FAIL_STRING;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class RatingGetter {
    
    private static final RatingChanges cache = new RatingChanges();
    
    public static JSONObject getNewRating(int contestId) {
        List<Integer> active = ContestProcessor.getActiveContestIds();
        if (active.contains(contestId)) {
            return cache.getValue(contestId);
        } else {
            return new JSONObject(JSON_FAIL_STRING);
        }
    }
    
    public static String getNewRatingString(int contestId) {
        return getNewRating(contestId).toString();
    }
    
    public static List<Integer> getCachedIds() {
        return new ArrayList<>(cache.getCachedKeys());
    }
    
    public static void removeFromCache(int contestId) {
        cache.remove(contestId);
    }
}
