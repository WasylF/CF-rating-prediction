package com.wslfinc.cf.sdk;

import static com.wslfinc.Constants.*;
import com.wslfinc.cf.rating.RatingCalculator;
import com.wslfinc.cf.sdk.api.CodeForcesAPI;
import com.wslfinc.cf.sdk.entities.*;
import com.wslfinc.cf.sdk.entities.additional.Contestant;
import com.wslfinc.cf.sdk.entities.additional.ContestantResult;
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

    private static int getPreviousFinishedContest(int contestId) {
        int previousContestId = contestId;
        String url;
        do {
            previousContestId--;
            url = PAST_RATING_URL_PREFIX + previousContestId + PAST_RATING_URL_SUFFIX;
        } while (!JsonReader.isExists(url));

        return previousContestId;
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
    public static List<Contestant> getAllContestants(int contestId) {
        try {
            int previousContestId = getPreviousFinishedContest(contestId);
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
     * @return List of Contestants that particapating in contest
     * #{@code contestId}. All fields of Contestant are filled.
     */
    public static List<Contestant> getActiveContestants(int contestId) {
        List<Contestant> registredContestants = getAllContestants(contestId);
        List<RanklistRow> rows = getRanklistRows(contestId);

        Map<String, Integer> prevRating = new HashMap<>();
        for (Contestant contestant : registredContestants) {
            prevRating.put(contestant.getHandle(), contestant.getPrevRating());
        }

        return getActiveContestants(registredContestants, prevRating, rows);
    }

    public static List<Contestant> getActiveContestants(List<Contestant> oldCOntestants,
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
     *
     * @param contestId Id of the contest. It is not the round number. It can be
     * seen in contest URL. {@code 1 <= contestId <= MAXIMAL_CONTEST_ID}
     * @return all rows of ranklist
     */
    public static List<RanklistRow> getRanklistRows(int contestId) {
        Contest contest = new Contest(contestId);
        Object problems = new Object();
        List<RanklistRow> rows = new ArrayList<>();
        getContestStanding(contestId, 1, MAXIMAL_CONTESTANTS, false, contest, problems, rows);
        return rows;
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

    /**
     * Calculates rating after round {@code  contestId}
     * @param contestId
     * @return nextRating for all contestants
     */
    public static List<ContestantResult> getNewRatings(int contestId) {
        List<Contestant> active = getActiveContestants(contestId);
        RatingCalculator ratingCalculator = new RatingCalculator(active);
        return ratingCalculator.getNewRatings();
    }
}
