package com.wslfinc.cf.sdk;

import com.wslfinc.cf.sdk.api.CodeForcesAPI;
import com.wslfinc.cf.sdk.entities.*;
import com.wslfinc.cf.sdk.entities.additional.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wsl_F
 */
public class CodeForcesSDK {

    final static CodeForcesAPI API = CodeForcesAPI.getInstance();

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
     * @return list of contests
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
     * @return List of Contestant. Rank field of every contestant contains
     * default value (-1).
     */
    public static List<Contestant> getAllContestants(int contestId) {
        return ContestantProcessing.getAllContestants(contestId);
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
        return ContestantProcessing.getActiveContestants(contestId);
    }

    public static List<Team> getActiveTeams(int contestId) {
        return ContestantProcessing.getActiveTeams(contestId);
    }

    /**
     *
     * @param contestId Id of the contest. It is not the round number. It can be
     * seen in contest URL. {@code 1 <= contestId <= MAXIMAL_CONTEST_ID}
     * @return contests
     */
    public static Contest getContest(int contestId) {
        return ContestProcessing.getContest(contestId);
    }

    /**
     *
     * @param contestId Id of the contest. It is not the round number. It can be
     * seen in contest URL. {@code 1 <= contestId <= MAXIMAL_CONTEST_ID}
     * @return all rows of ranklist
     */
    public static List<RanklistRow> getRanklistRows(int contestId) {
        return ContestProcessing.getRanklistRows(contestId);
    }

    /**
     * Check has contest finished or not?
     *
     * @param contestId Id of the contest. It is not the round number. It can be
     * seen in contest URL. {@code 1 <= contestId <= MAXIMAL_CONTEST_ID}
     * @return true if contest finished
     */
    public static boolean isFinished(int contestId) {
        return ContestProcessing.isFinished(contestId);
    }

    /**
     * Calculates rating after round {@code  contestId}
     *
     * @param contestId
     * @return nextRating for all contestants
     */
    public static List<ContestantResult> getNewRatings(int contestId) {
        return RatingProcessing.getNewRatings(contestId);
    }

    /**
     * Getting finished contests with id lower or equals to {@code maxId}
     *
     * @param maxId maximal contestId
     * @param gym true if include gym contests
     * @return Sorted by start time list of contests with id in range
     * [1..{@code maxId}]
     */
    public static List<Contest> getFinishedContests(int maxId, boolean gym) {
        return ContestProcessing.getFinishedContests(maxId, gym);
    }

    public static List<String> getContestNames(boolean gym) {
        return ContestProcessing.getContestNames(gym);
    }

    public static List<Integer> getCachedIds() {
        return new ArrayList<>(RatingProcessing.getCachedIds());
    }

    public static void removeFromCache(int contestId) {
        RatingProcessing.removeFromCache(contestId);
    }

    public static void changeTimeToUodate(long timeToUpdateMS) {
        RatingProcessing.changeTimeToUpdate(timeToUpdateMS);
    }
}
