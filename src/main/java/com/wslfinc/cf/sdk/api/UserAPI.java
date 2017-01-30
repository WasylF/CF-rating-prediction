package com.wslfinc.cf.sdk.api;

import com.wslfinc.helpers.web.JsonReader;
import org.json.JSONObject;

import static com.wslfinc.Constants.*;
import com.wslfinc.cf.sdk.entities.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

/**
 *
 * @author Wsl_F
 */
class UserAPI {

    /**
     * Returns information about one user.
     *
     * @param handle user handle
     * @return {@code User}
     */
    static User getUserInfo(String handle) {
        List<String> user = new ArrayList<>();
        user.add(handle);
        List<User> result = getUserInfo(user);
        return result.get(0);
    }

    /**
     * Returns information about one or several users
     *
     * @param handles List of user handles
     * @return {@code List<User>}
     */
    static List<User> getUserInfo(List<String> handles) {
        StringBuilder url = new StringBuilder(API_PREFIX + "/user.info?handles=");
        for (String handle : handles) {
            url.append(handle).append(';');
        }

        try {
            JSONObject response = JsonReader.read(url.toString());
            if (response != null || ResponseChecker.isValid(response)) {
                JSONArray users = response.getJSONArray(JSON_RESULTS);

                List<User> usersList = new ArrayList<>();
                for (Object user : users) {
                    usersList.add(new User((JSONObject) user));
                }

                return usersList;
            }
        } catch (Exception ex) {
            System.err.println("Couldn't get users info. "
                    + "url: " + url + "\n" + ex.getMessage());
        }

        return new ArrayList<>();
    }

    /**
     * Returns rating history of the specified user.
     *
     * @param handle User's handle
     * @return
     */
    static List<RatingChange> getRatingHistory(String handle) {
        String url = API_PREFIX + "/user.rating?handle=" + handle;
        try {
            JSONObject response = JsonReader.read(url);
            if (response != null || ResponseChecker.isValid(response)) {
                JSONArray ratingChanges = response.getJSONArray(JSON_RESULTS);

                List<RatingChange> ratingHistory = new ArrayList<>();
                for (Object ratingChange : ratingChanges) {
                    ratingHistory.add(new RatingChange((JSONObject) ratingChange));
                }

                return ratingHistory;
            }
        } catch (Exception ex) {
            System.err.println("Couldn't get user rating history. "
                    + "url: " + url + "\n" + ex.getMessage());
        }

        return new ArrayList<>();
    }
}
