package com.wslfinc.cf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 * Stores data at the cache.
 *
 * @author Wsl_F
 */
public class RatingExpection {

    protected static final int MAX_CACHED_CONTESTS = 5;

    long TIME_TO_RECALCULATE_MS;
    long MAX_TIME_INTERVAL_MS = 60_000;
    int INITIAL_USING = MAX_CACHED_CONTESTS * 2 + 1;

    private String backEndUrl = "http://cfpredictorbackend.us-west-2.elasticbeanstalk.com";

    /**
     * Key - contestId, Value - cached JSON value. Map contains not more than
     * {@code MAX_CACHED_CONTESTS} items
     */
    private volatile Map<Integer, JSONObject> cacheJSON = new HashMap<>();
    /**
     * Key - contestId, Value - cached String value - representation of JSON.
     * Map contains not more than {@code MAX_CACHED_CONTESTS} items
     */
    private volatile Map<Integer, String> cacheString = new HashMap<>();

    /**
     * Key - contestId, Value - {number of requests, time of last request, time
     * of adding}.
     */
    private volatile Map<Integer, long[]> cacheUsing = new HashMap<>();

    public RatingExpection(long recalculateTimeMS) {
        this.TIME_TO_RECALCULATE_MS = recalculateTimeMS;
    }

    public void removeFromCache(int contestId) {
        cacheUsing.remove(contestId);
        cacheJSON.remove(contestId);
        cacheString.remove(contestId);
    }

    public void removeFromCache(List<Integer> forRemove) {
        for (int contestId : forRemove) {
            removeFromCache(contestId);
        }
    }

    private void decreaseUsing() {
        long time = System.currentTimeMillis();
        List<Integer> forRemove = new LinkedList<>();
        for (Integer contestId : cacheUsing.keySet()) {
            if (cacheUsing.get(contestId)[0] <= 1
                    || time - cacheUsing.get(contestId)[1] > MAX_TIME_INTERVAL_MS) {
                forRemove.add(contestId);
            } else {
                cacheUsing.get(contestId)[0]--;
                if (time - cacheUsing.get(contestId)[2] > TIME_TO_RECALCULATE_MS) {
                    // to prevent calling few times before getting new result
                    cacheUsing.get(contestId)[2] = time;
                    CalculatingStraigth calculator = new CalculatingStraigth(this, contestId);
                    new Thread(calculator).start();
                }
            }
        }

        removeFromCache(forRemove);
    }

    private JSONObject getChachedJSON(int contestId) {
        long time = System.currentTimeMillis();
        cacheUsing.get(contestId)[0] += 2;
        cacheUsing.get(contestId)[1] = time;
        return cacheJSON.get(contestId);
    }

    private String getChachedString(int contestId) {
        long time = System.currentTimeMillis();
        cacheUsing.get(contestId)[0] += 2;
        cacheUsing.get(contestId)[1] = time;
        return cacheString.get(contestId);
    }

    /**
     * Getting value from cache if this is possible otherwise - getStraight.
     * Also add value to the cache if it's needed.
     *
     * @param contestId contestId of cached value
     * @return value
     */
    public JSONObject getJSONValue(int contestId) {
        JSONObject result;
        if (cacheJSON.containsKey(contestId)) {
            result = getChachedJSON(contestId);
        } else {
            result = getStraight(contestId);
            if (result != null) {
                addIfNeed(contestId, result);
            }
        }

        decreaseUsing();

        return result;
    }

    public String getStringValue(int contestId) {
        String result;
        if (cacheString.containsKey(contestId)) {
            result = getChachedString(contestId);
        } else {
            JSONObject json = getStraight(contestId);
            if (json != null) {
                result = json.toString();
                addIfNeed(contestId, json);
            } else {
                result = "{\"status\" : \"FAIL\"}";
            }
        }

        decreaseUsing();

        return result;
    }

    private void addIfNeed(int contestId, JSONObject value) {
        if (value != null && cacheJSON.size() < MAX_CACHED_CONTESTS) {
            cacheJSON.put(contestId, value);
            cacheString.put(contestId, value.toString());
            long time = System.currentTimeMillis();
            long[] using = new long[]{INITIAL_USING, time, time};
            cacheUsing.put(contestId, using);
        }
    }

    void updateValueInCache(int contestId) {
        JSONObject value = getStraight(contestId);
        if (value == null) {
            removeFromCache(contestId);
            return;
        }
        cacheJSON.put(contestId, value);
        cacheString.put(contestId, value.toString());
        long time = System.currentTimeMillis();
        long lastTimeUsing;
        long reqNumber;
        if (cacheUsing.containsKey(contestId)) {
            lastTimeUsing = cacheUsing.get(contestId)[1];
            reqNumber = cacheUsing.get(contestId)[0];
        } else {
            lastTimeUsing = time - MAX_TIME_INTERVAL_MS / 2;
            reqNumber = 2;
        }
        long[] using = new long[]{reqNumber, lastTimeUsing, time};
        cacheUsing.put(contestId, using);
    }

    private JSONObject getStraight(int contestId) {
        String requestURL = backEndUrl + "/GetNextRatingServlet?contestId=" + contestId;
        try {
            JSONObject json = JsonReader.read(requestURL);
            return json;
        } catch (Exception ex) {
            System.err.println("Couldn't get request results " + requestURL
                    + "\n" + ex.getMessage());
        }

        return null;
    }

    public List<Integer> getCachedIds() {
        return new ArrayList<>(cacheJSON.keySet());
    }
}
