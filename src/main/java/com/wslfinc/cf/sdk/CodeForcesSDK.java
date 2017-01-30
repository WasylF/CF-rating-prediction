package com.wslfinc.cf.sdk;

import com.wslfinc.cf.sdk.api.CodeForcesAPI;
import com.wslfinc.cf.sdk.entities.*;
import java.util.List;

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
    public List<RatingChange> getRatingChanges(int contestId) {
        return API.getRatingChanges(contestId);
    }

    /**
     *
     * @param gym include gym contests
     * @return rating changes
     */
    public List<Contest> getContestsList(boolean gym) {
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
    public boolean getContestStanding(int contestId, int from, int count, boolean showUnofficial,
            Contest contest, Object problems, List<RanklistRow> rows) {
        return API.getContestStanding(contestId, from, count, showUnofficial, contest, problems, rows);
    }

    /**
     * Returns information about one user.
     *
     * @param handle user handle
     * @return {@code User}
     */
    public User getUserInfo(String handle) {
        return API.getUserInfo(handle);
    }

    /**
     * Returns information about one or several users
     *
     * @param handles List of user handles
     * @return {@code List<User>}
     */
    public List<User> getUserInfo(List<String> handles) {
        return API.getUserInfo(handles);
    }

    /**
     * Returns rating history of the specified user.
     *
     * @param handle User's handle
     * @return
     */
    public List<RatingChange> getRatingHistory(String handle) {
        return API.getRatingHistory(handle);
    }

}
