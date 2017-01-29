package com.wslfinc.cf.sdk.entities;

import com.wslfinc.cf.sdk.JsonExtractor;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class RanklistRow {

    /**
     * Party object. Party that took a corresponding place in the contest.
     */
    Party party;

    /**
     * Integer. Party place in the contest.
     */
    int rank;

    /**
     * Floating point number. Total ammount of points, scored by the party.
     */
    double points;

    /**
     * Integer. Total penalty (in ICPC meaning) of the party.
     */
    int penalty;

    /**
     * Integer.
     */
    int successfulHackCount;

    /**
     * Integer.
     */
    int unsuccessfulHackCount;

    /**
     * List of ProblemResult objects. Party results for each problem. Order of
     * the problems is the same as in "problems" field of the returned object.
     */
    List<ProblemResult> problemResults;

    /**
     * Integer. For IOI contests only. Time in seconds from the start of the
     * contest to the last submission that added some points to the total score
     * of the party.
     */
    int lastSubmissionTimeSeconds;

    public RanklistRow(Party party, int rank, double points, int penalty, int successfulHackCount, int unsuccessfulHackCount, List<ProblemResult> problemResults, int lastSubmissionTimeSeconds) {
        this.party = party;
        this.rank = rank;
        this.points = points;
        this.penalty = penalty;
        this.successfulHackCount = successfulHackCount;
        this.unsuccessfulHackCount = unsuccessfulHackCount;
        this.problemResults = problemResults;
        this.lastSubmissionTimeSeconds = lastSubmissionTimeSeconds;
    }

    public RanklistRow(JSONObject ranklistRow) {
        this.party = new Party(ranklistRow.getJSONObject("party"));
        this.rank = JsonExtractor.getInt(ranklistRow, "rank");
        this.points = JsonExtractor.getDouble(ranklistRow, "points");
        this.penalty = JsonExtractor.getInt(ranklistRow, "penalty");
        this.successfulHackCount = JsonExtractor.getInt(ranklistRow, "successfulHackCount");
        this.unsuccessfulHackCount = JsonExtractor.getInt(ranklistRow, "successfulHackCount");
        this.problemResults = JsonExtractor.getProblemResults(ranklistRow);
        this.lastSubmissionTimeSeconds = JsonExtractor.getInt(ranklistRow, "lastSubmissionTimeSeconds");
    }

    public Party getParty() {
        return party;
    }

    public int getRank() {
        return rank;
    }

    public double getPoints() {
        return points;
    }

    public int getPenalty() {
        return penalty;
    }

    public int getSuccessfulHackCount() {
        return successfulHackCount;
    }

    public int getUnsuccessfulHackCount() {
        return unsuccessfulHackCount;
    }

    public List<ProblemResult> getProblemResults() {
        return problemResults;
    }

    public int getLastSubmissionTimeSeconds() {
        return lastSubmissionTimeSeconds;
    }

}
