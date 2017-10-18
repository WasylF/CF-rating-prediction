package com.wslfinc.cf.sdk;

import static com.wslfinc.cf.sdk.CodeForcesSDK.*;
import static com.wslfinc.cf.sdk.Constants.*;
import com.wslfinc.cf.sdk.entities.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Wsl_F
 */
public class ContestProcessing {

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

    /**
     * Getting finished contests with id lower or equals to {@code maxId}
     *
     * @param maxId maximal contestId
     * @param gym true if include gym contests
     * @return Sorted by start time list of contests with id in range
     * [1..{@code maxId}]
     */
    public static List<Contest> getFinishedContests(int maxId, boolean gym) {
        ArrayList<Contest> contests = (ArrayList<Contest>) API.getContestsList(gym);
        List<Contest> results = new LinkedList<>();
        int n = contests.size();
        for (int i = 0; i < n; i++) {
            Contest contest = contests.get(i);
            if (contest.isFinished() && contest.getId() <= maxId) {
                results.add(contest);
            }
        }

        Collections.sort(results);

        return results;
    }

    public static List<String> getContestNames(boolean gym) {
        List<Contest> contests = getContestsList(gym);
        List<String> result = new ArrayList<>(contests.size());

        for (Contest contest : contests) {
            int contestId = contest.getId();
            while (result.size() <= contestId) {
                result.add("");
            }
            result.set(contestId, contest.getName());
        }

        return result;
    }
}
