package com.wslfinc.cf.sdk;

import static com.wslfinc.cf.sdk.CodeForcesSDK.*;
import static com.wslfinc.cf.sdk.Constants.*;
import com.wslfinc.cf.sdk.entities.*;
import com.wslfinc.helpers.web.JsonReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wsl_F
 */
public class ContestProcessing {

    private static final int DEFAULT_PREVIOUS_CONTEST = -1;

    private static ArrayList<Integer> previousContest = new ArrayList<>();

    private static void ensureCapacityPreviousContest(int contestId) {
        previousContest.ensureCapacity(contestId + 1);
        while (previousContest.size() <= contestId) {
            previousContest.add(DEFAULT_PREVIOUS_CONTEST);
        }
    }

    private static int getPreviousFinishedContestStraight(int contestId) {
        int previousContestId = contestId;
        String url;
        do {
            previousContestId--;
            url = PAST_RATING_URL_PREFIX + previousContestId + PAST_RATING_URL_SUFFIX;
        } while (!JsonReader.isExists(url));

        return previousContestId;
    }

    static int getPreviousFinishedContestId(int contestId) {
        ensureCapacityPreviousContest(contestId);
        if (previousContest.get(contestId) == DEFAULT_PREVIOUS_CONTEST) {
            int prev = getPreviousFinishedContestStraight(contestId);
            previousContest.set(contestId, prev);
        }

        return previousContest.get(contestId);
    }

    /**
     *
     * @param contestId Id of the contest. It is not the round number. It can be
     * seen in contest URL. {@code 1 <= contestId <= MAXIMAL_CONTEST_ID}
     * @return contests
     */
    static Contest getContest(int contestId) {
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
    static List<RanklistRow> getRanklistRows(int contestId) {
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
    static boolean isFinished(int contestId) {
        Contest contest = getContest(contestId);
        return contest.getPhase() == ContestPhase.FINISHED;
    }

}
