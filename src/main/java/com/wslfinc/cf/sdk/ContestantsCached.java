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

/**
 *
 * @author Wsl_F
 */
public class ContestantsCached extends Cacheble<Contestant> {

    public ContestantsCached() {
        // TTL 1 day
        super(86_400_000);
    }

    @Override
    protected List<Contestant> getStraight(int contestId) {
        try {
            String url = PAST_RATING_URL_PREFIX + contestId + PAST_RATING_URL_SUFFIX;
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
            System.err.println("Failed to get all contestants on contestId: " + contestId);
            System.err.println(exception.getMessage());
        }

        return new ArrayList<>();
    }

}
