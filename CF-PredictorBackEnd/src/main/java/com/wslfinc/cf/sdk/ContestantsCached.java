package com.wslfinc.cf.sdk;

import static com.wslfinc.cf.sdk.Constants.JSON_RESULTS;
import static com.wslfinc.cf.sdk.Constants.PAST_RATING_URL_PREFIX;
import static com.wslfinc.cf.sdk.Constants.PAST_RATING_URL_SUFFIX;
import com.wslfinc.cf.sdk.entities.additional.Contestant;
import com.wslfinc.helpers.web.JsonReader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.github.wslf.datastructures.cache.Cacheable;

/**
 *
 * @author Wsl_F
 */
public class ContestantsCached extends Cacheable<List<Contestant>, Integer> {

    public ContestantsCached() {
        // TTL 1 day
        super(10, 2, 24 * 60 * 60 * 1000);
    }

    @Override
    public List<Contestant> getValueManually(Integer key) {
        try {
            String url = PAST_RATING_URL_PREFIX + key + PAST_RATING_URL_SUFFIX;
            JSONObject response = JsonReader.read(url);
            if (ResponseChecker.isValid(response)) {
                JSONArray contestants = response.getJSONArray(JSON_RESULTS);

                List<Contestant> result = new ArrayList<>();
                for (Object contestant : contestants) {
                    result.add(new Contestant((JSONObject) contestant));
                }
                return result;
            }
        } catch (Exception exception) {
            System.err.println("Failed to get all contestants on contestId: " + key);
            System.err.println(exception.getMessage());
        }

        return new ArrayList<>();
    }

}
