package com.wslfinc.cf;

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
}
