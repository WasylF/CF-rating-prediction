package com.wslfinc.cf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class ContestProcessor {

    private static final ContestsNames names = new ContestsNames();

    public static JSONArray getNamesIds() {
        return names.getValue(1);
    }

    public static ArrayList<Integer> getActiveContestIds() {
        // It's stub!!!
        List<Integer> active = Arrays.asList(781, 782);

        return new ArrayList<>(active);
    }

    public static String getName(int contestId) {
        JSONArray cNames = names.getValue(1);
        if (cNames != null) {
            for (Object o : cNames) {
                JSONObject contest = (JSONObject) o;
                String name = contest.getString("name");
                if (!name.isEmpty() && contest.getInt("contestId") == contestId) {
                    return name;
                }
            }
        }
        return "Name not found";
    }
}
