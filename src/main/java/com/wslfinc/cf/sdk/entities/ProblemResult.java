package com.wslfinc.cf.sdk.entities;

import com.wslfinc.cf.sdk.JsonExtractor;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class ProblemResult {

    /**
     * Floating point number.
     */
    double points;

    /**
     * Integer. Penalty (in ICPC meaning) of the party for this problem.
     */
    int penalty;

    /**
     * Integer. Number of incorrect submissions.
     */
    int rejectedAttemptCount;

    /**
     * Enum: PRELIMINARY, FINAL. If type is PRELIMINARY then points can decrease
     * (if, for example, solution will fail during system test). Otherwise,
     * party can only increase points for this problem by submitting better
     * solutions.
     */
    SubmissionType type;

    /**
     * Integer. Number of seconds after the start of the contest before the
     * submission, that brought maximal amount of points for this problem.
     */
    int bestSubmissionTimeSeconds;

    public ProblemResult(double points, int penalty, int rejectedAttemptCount, SubmissionType type, int bestSubmissionTimeSeconds) {
        this.points = points;
        this.penalty = penalty;
        this.rejectedAttemptCount = rejectedAttemptCount;
        this.type = type;
        this.bestSubmissionTimeSeconds = bestSubmissionTimeSeconds;
    }

    public ProblemResult(JSONObject problemResult) {
        this.points = JsonExtractor.getDouble(problemResult, "points");
        this.penalty = JsonExtractor.getInt(problemResult, "penalty");
        this.rejectedAttemptCount = JsonExtractor.getInt(problemResult, "rejectedAttemptCount");
        this.type = SubmissionType.valueOf(JsonExtractor.getString(problemResult, "type"));
        this.bestSubmissionTimeSeconds = JsonExtractor.getInt(problemResult, "bestSubmissionTimeSeconds");
    }

    public double getPoints() {
        return points;
    }

    public int getPenalty() {
        return penalty;
    }

    public int getRejectedAttemptCount() {
        return rejectedAttemptCount;
    }

    public SubmissionType getType() {
        return type;
    }

    public int getBestSubmissionTimeSeconds() {
        return bestSubmissionTimeSeconds;
    }

}
