package com.wslfinc.cf.sdk;

import org.json.JSONObject;
import static com.wslfinc.cf.sdk.Constants.*;

/**
 *
 * @author Wsl_F
 */
public class ResponseChecker {

    /**
     * Check does {@code obj} valid response.
     *
     * @param obj JSON object
     * @return true if {@code obj} is valid CF response, false otherwise
     */
    public static boolean isValid(JSONObject obj) {
        return obj != null && SUCCESSFUL_STATUS.equals(obj.getString("status"));
    }
}
