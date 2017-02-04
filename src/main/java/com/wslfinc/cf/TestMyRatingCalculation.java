package com.wslfinc.cf;

import com.wslfinc.cf.sdk.CodeForcesSDK;
import com.wslfinc.cf.sdk.entities.Contest;
import com.wslfinc.cf.sdk.entities.ContestType;
import com.wslfinc.cf.sdk.entities.RatingChange;
import com.wslfinc.cf.sdk.entities.additional.ContestantResult;
import java.util.List;

/**
 *
 * @author Wsl_F
 */
public class TestMyRatingCalculation {

    // 592
    public static void calculateRatingDif(int minContestId, int maxContestId) {

        System.out.println("MaxDif AverageDif");
        for (int contestId = minContestId; contestId <= maxContestId; contestId++) {
            Contest contest = CodeForcesSDK.getContest(contestId);
            if (contest.getType() == ContestType.CF) {
                List<ContestantResult> myRating
                        = CodeForcesSDK.getNewRatings(contestId);
                List<RatingChange> ofRating
                        = CodeForcesSDK.getRatingChanges(contestId);
                int totalMaxDif = 0;
                int totalSum = 0;
                String totalMaxDifHandle = "";
                for (ContestantResult cR : myRating) {
                    int maxDif = 0;
                    for (RatingChange rCh : ofRating) {
                        if (cR.getHandle().equals(rCh.getHandle())) {
                            int dif = cR.getNextRating() - rCh.getNewRating();
                            dif = Math.abs(dif);
                            if (dif > maxDif) {
                                maxDif = dif;
                            }
                            if (cR.getPrevRating() != rCh.getOldRating()) {
                                System.out.println(cR.getHandle());
                            }
                        }
                    }
                    if (maxDif > totalMaxDif) {
                        totalMaxDif = maxDif;
                        totalMaxDifHandle = cR.getHandle();
                    }
                    totalSum += maxDif;
                }

                double average = totalSum;
                average /= myRating.size();
                System.out.println(contestId + ") "
                        + totalMaxDifHandle + ": " + totalMaxDif + " " + average);
            }
        }
    }
}
