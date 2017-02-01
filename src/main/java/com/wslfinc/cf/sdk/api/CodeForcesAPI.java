package com.wslfinc.cf.sdk.api;

import com.wslfinc.cf.sdk.entities.RatingChange;
import com.wslfinc.helpers.Expectant;
import java.util.List;

import static com.wslfinc.Constants.*;
import com.wslfinc.cf.sdk.entities.Contest;
import com.wslfinc.cf.sdk.entities.RanklistRow;
import com.wslfinc.cf.sdk.entities.User;
import java.time.LocalDateTime;

/**
 *
 * @author Wsl_F
 */
public class CodeForcesAPI {

    private static final CodeForcesAPI instance = new CodeForcesAPI();

    private long totalAPICalls;

    private CodeForcesAPI() {
        totalAPICalls = 0;
    }

    public static CodeForcesAPI getInstance() {
        return instance;
    }

    private void beforeAPICall() {
        Expectant.delay(API_DELAY_MS);
        totalAPICalls++;
        System.out.println("Doing API request #" + totalAPICalls
                + ": " + LocalDateTime.now());
    }

    public long getTotalAPICalls() {
        return this.totalAPICalls;
    }

    /**
     *
     * @param contestId codeforces contest id (!!! not equals cf round number)
     * @return rating changes
     */
    public List<RatingChange> getRatingChanges(int contestId) {
        beforeAPICall();
        return ContestAPI.getRatingChanges(contestId);
    }

    /**
     *
     * @param gym include gym contests
     * @return rating changes
     */
    public List<Contest> getContestsList(boolean gym) {
        beforeAPICall();
        return ContestAPI.getContestsList(gym);
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
    public boolean getContestStanding(int contestId, int from, int count, boolean showUnofficial,
            Contest contest, Object problems, List<RanklistRow> rows) {
        beforeAPICall();
        return ContestAPI.getContestStanding(contestId, from, count, showUnofficial, contest, problems, rows);
    }

    /**
     * Returns information about one user.
     *
     * @param handle user handle
     * @return {@code User}
     */
    public User getUserInfo(String handle) {
        beforeAPICall();
        return UserAPI.getUserInfo(handle);
    }

    /**
     * Returns information about one or several users
     *
     * @param handles List of user handles
     * @return {@code List<User>}
     */
    public List<User> getUserInfo(List<String> handles) {
        beforeAPICall();
        return UserAPI.getUserInfo(handles);
    }

    /**
     * Returns rating history of the specified user.
     *
     * @param handle User's handle
     * @return
     */
    public List<RatingChange> getRatingHistory(String handle) {
        beforeAPICall();
        return UserAPI.getRatingHistory(handle);
    }

}