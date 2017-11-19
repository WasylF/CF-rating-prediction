package com.wslfinc.cf;

import com.github.wslf.datastructures.cache.Cacheable;
import static com.wslfinc.cf.Constants.*;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class RatingChanges extends Cacheable<String, Integer> {

    public RatingChanges() {
        super(10, 3, 15_000);
    }

    @Override
    public String getValueManually(Integer contestId) {
        System.out.println("Updating rating prediction for contest " + contestId + " time " + System.currentTimeMillis());
        String requestURL = BACK_END_URL + "/GetNextRatingServlet?contestId=" + contestId;
        try {
            JSONObject json = JsonReader.read(requestURL);
            return json.toString();
        } catch (Exception ex) {
            System.err.println("Couldn't get request results " + requestURL
                    + "\n" + ex.getMessage());
        }

        return JSON_FAIL_STRING;
    }

}
