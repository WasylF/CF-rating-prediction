package com.wslfinc.cf.sdk.entities.additional;

import static com.wslfinc.cf.sdk.Constants.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class ContestantResult {

    Contestant contestant;
    double seed;
    int nextRating;

    public ContestantResult(Contestant contestant, double seed, int nextRating) {
        this.contestant = contestant;
        this.seed = seed;
        this.nextRating = nextRating;
    }

    public double getSeed() {
        return seed;
    }

    public int getNextRating() {
        return nextRating;
    }

    public String getHandle() {
        return contestant.handle;
    }

    public int getRank() {
        return contestant.rank;
    }

    public int getPrevRating() {
        return contestant.prevRating;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("handle", contestant.handle);
        json.put("rank", contestant.rank);
        json.put("oldRating", contestant.prevRating);
        json.put("newRating", nextRating);
        json.put("seed", seed);
        return json;
    }

    public JSONObject deltasToJSON() {
        JSONObject json = new JSONObject();
        json.put("handle", contestant.handle);
        int delta = nextRating - contestant.prevRating;
        json.put("delta", delta);
        return json;
    }

    public static JSONObject toJSON(List<ContestantResult> nextRating) {
        List<JSONObject> list = new ArrayList<>(nextRating.size());
        for (ContestantResult result : nextRating) {
            list.add(result.toJSON());
        }

        JSONArray array = new JSONArray(list);
        JSONObject json = new JSONObject();
        json.put(JSON_STATUS, SUCCESSFUL_STATUS);
        json.put(JSON_RESULTS, array);

        return json;
    }
}
