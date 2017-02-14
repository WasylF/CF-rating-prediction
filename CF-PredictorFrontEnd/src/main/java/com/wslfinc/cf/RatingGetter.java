package com.wslfinc.cf;

import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class RatingGetter {
    
    private static final RatingExpection cache = new RatingExpection(60_000);
    
    public static JSONObject getNewRating(int contestId) {
        return cache.getJSONValue(contestId);
    }
    
    public static String getNewRatingString(int contestId) {
        return cache.getStringValue(contestId);
    }
    
    public static List<Integer> getCachedIds() {
        return cache.getCachedIds();
    }
    
    public static void removeFromCache(int contestId) {
        cache.removeFromCache(contestId);
    }
}
