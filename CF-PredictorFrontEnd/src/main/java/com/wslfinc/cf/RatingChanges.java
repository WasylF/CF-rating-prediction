package com.wslfinc.cf;

import com.github.wslf.datastructures.cache.Cacheable;
import static com.wslfinc.cf.Constants.*;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class RatingChanges extends Cacheable<JSONObject, Integer> {

    public RatingChanges() {
        super(10, 2, 15_000);
    }

    @Override
    public JSONObject getValueManually(Integer contestId) {
        String requestURL = BACK_END_URL + "/GetNextRatingServlet?contestId=" + contestId;
        try {
            JSONObject json = JsonReader.read(requestURL);
            return json;
        } catch (Exception ex) {
            System.err.println("Couldn't get request results " + requestURL
                    + "\n" + ex.getMessage());
        }

        return JSON_FAIL;
    }

}
