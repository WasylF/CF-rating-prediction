package com.wslfinc.cf.sdk;

import static com.wslfinc.Constants.*;
import com.wslfinc.cf.sdk.api.CodeForcesAPI;
import com.wslfinc.cf.sdk.entities.*;
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
public class CodeForcesSDK {

    private final static CodeForcesAPI API = CodeForcesAPI.getInstance();

    /**
     *
     * @param contestId codeforces contest id (!!! not equals cf round number)
     * @return rating changes
     */
    public static List<RatingChange> getRatingChanges(int contestId) {
        return API.getRatingChanges(contestId);
    }

    /**
     *
     * @param gym include gym contests
     * @return rating changes
     */
    public static List<Contest> getContestsList(boolean gym) {
        return API.getContestsList(gym);
    }

    /**
     *
     * @param contestId Id of the contest. It is not the round number. It can be
     * seen in contest URL.
     * @param from 1-based index of the standings row to start the ranklist.
     * @param count Number of standing rows to return.
     * @param showUnofficial if true than all participants (virtual, out of
     * competition) are shown. Otherwise, only official contestants are shown.
     * @param contest returning result
     * @param problems returning result
     * @param rows returning result
     * @return successfulness
     */
    public static boolean getContestStanding(int contestId, int from, int count, boolean showUnofficial,
            Contest contest, Object problems, List<RanklistRow> rows) {
        return API.getContestStanding(contestId, from, count, showUnofficial, contest, problems, rows);
    }

    /**
     * Returns information about one user.
     *
     * @param handle user handle
     * @return {@code User}
     */
    public static User getUserInfo(String handle) {
        return API.getUserInfo(handle);
    }

    /**
     * Returns information about one or several users
     *
     * @param handles List of user handles
     * @return {@code List<User>}
     */
    public static List<User> getUserInfo(List<String> handles) {
        return API.getUserInfo(handles);
    }

    /**
     * Returns rating history of the specified user.
     *
     * @param handle User's handle
     * @return
     */
    public static List<RatingChange> getRatingHistory(String handle) {
        return API.getRatingHistory(handle);
    }

    /**
     * IT'S NOT OFFICIAL CODEFORCES API!!!
     *
     * Getting rating of all contestants just before round № {@code contestId}
     *
     * @param contestId Id of the contest. It is not the round number. It can be
     * seen in contest URL. {@code 1 <= contestId <= MAXIMAL_CONTEST_ID}
     * @return List of Contestant
     */
    public static List<Contestant> getAllContestants(int contestId) {
        try {
            contestId--;
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

    /**
     *
     * @param contestId Id of the contest. It is not the round number. It can be
     * seen in contest URL. {@code 1 <= contestId <= MAXIMAL_CONTEST_ID}
     * @return contests
     */
    public static Contest getContest(int contestId) {
        Contest contest = new Contest(contestId);
        Object problems = new Object();
        List<RanklistRow> rows = new ArrayList<>();
        API.getContestStanding(contestId, 1, 1, false, contest, problems, rows);
        return contest;
    }

    /**
     * Check has contest finished or not?
     *
     * @param contestId Id of the contest. It is not the round number. It can be
     * seen in contest URL. {@code 1 <= contestId <= MAXIMAL_CONTEST_ID}
     * @return true if contest finished
     */
    public static boolean isFinished(int contestId) {
        Contest contest = getContest(contestId);
        return contest.getPhase() == ContestPhase.FINISHED;
    }
}
