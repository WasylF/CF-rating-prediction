package com.wslfinc.cf.sdk;

import static com.wslfinc.cf.sdk.CodeForcesSDK.*;
import static com.wslfinc.Constants.*;
import com.wslfinc.cf.sdk.entities.*;
import com.wslfinc.cf.sdk.entities.additional.*;
import com.wslfinc.helpers.web.JsonReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class ContestantProcessing {

    static final int MAX_CACHED_CONTESTS = 3;

    /**
     * 10 minutes
     */
    static final int MAX_TIME_INTERVAL_MS = 600_000;

    static final int INITIAL_PRIORITY = 50;

    /**
     * Key - contestId, Value - all "old" contestants before this contest. Map
     * contains not more than {@code MAX_CACHED_CONTESTS} items
     */
    static Map<Integer, List<Contestant>> allContestants = new HashMap<>();

    /**
     * Key - contestId, Value - {number of requests, time of last request}.
     */
    static Map<Integer, long[]> allContestantsUsing = new HashMap<>();

    private static void decreaseAllContestatntsUsing(int expectId) {
        long time = System.currentTimeMillis();
        for (Integer contestId : allContestantsUsing.keySet()) {
            if (contestId != expectId) {
                if (allContestantsUsing.get(contestId)[0] <= 1
                        || time - allContestantsUsing.get(contestId)[1] > MAX_TIME_INTERVAL_MS) {
                    allContestantsUsing.remove(contestId);
                    allContestants.remove(contestId);
                } else {
                    allContestantsUsing.get(contestId)[0]--;
                }
            }
        }
    }

    private static List<Contestant> getAllContestantsChached(int contestId) {
        long time = System.currentTimeMillis();
        allContestantsUsing.get(contestId)[0]++;
        allContestantsUsing.get(contestId)[1] = time;
        return allContestants.get(contestId);
    }

    private static List<Contestant> getAllContestantsStraight(int contestId) {
        try {
            int previousContestId = ContestProcessing.getPreviousFinishedContestId(contestId);
            String url = PAST_RATING_URL_PREFIX + previousContestId + PAST_RATING_URL_SUFFIX;
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

    /**
     * IT'S NOT OFFICIAL CODEFORCES API!!!
     *
     * Getting rating of all contestants just before round № {@code contestId}
     *
     * @param contestId Id of the contest. It is not the round number. It can be
     * seen in contest URL. {@code 1 <= contestId <= MAXIMAL_CONTEST_ID}
     * @return List of Contestant. Rank field of every contestant contains
     * default value (-1).
     */
    static List<Contestant> getAllContestants(int contestId) {
        List<Contestant> contestants;
        if (allContestants.containsKey(contestId)) {
            contestants = getAllContestantsChached(contestId);
        } else {
            contestants = getAllContestantsStraight(contestId);
            if (allContestants.size() < MAX_CACHED_CONTESTS) {
                allContestants.put(contestId, contestants);
                long time = System.currentTimeMillis();
                long[] using = new long[]{INITIAL_PRIORITY, time};
                allContestantsUsing.put(contestId, using);
            }
        }

        return contestants;
    }

    /**
     * IT'S NOT OFFICIAL CODEFORCES API!!!
     *
     * Getting rating of all contestants just before round № {@code contestId}
     *
     * @param contestId Id of the contest. It is not the round number. It can be
     * seen in contest URL. {@code 1 <= contestId <= MAXIMAL_CONTEST_ID}
     * @return List of Contestants that particapating in contest
     * #{@code contestId}. All fields of Contestant are filled.
     */
    static List<Contestant> getActiveContestants(int contestId) {
        List<Contestant> registredContestants = getAllContestants(contestId);
        List<RanklistRow> rows = getRanklistRows(contestId);

        Map<String, Integer> prevRating = new HashMap<>();
        for (Contestant contestant : registredContestants) {
            prevRating.put(contestant.getHandle(), contestant.getPrevRating());
        }

        return getActiveContestants(registredContestants, prevRating, rows);
    }

    static List<Contestant> getActiveContestants(List<Contestant> oldCOntestants,
            Map<String, Integer> prevRating, List<RanklistRow> rows) {
        List<Contestant> active = new ArrayList<>();
        for (RanklistRow row : rows) {
            for (Member member : row.getParty().getMembers()) {
                String handle = member.getHandle();
                if (!prevRating.containsKey(handle)) {
                    prevRating.put(handle, INITIAL_RATING);
                }

                int prevR = prevRating.get(handle);
                int rank = row.getRank();
                active.add(new Contestant(handle, rank, prevR));
            }
        }

        return active;
    }

}
