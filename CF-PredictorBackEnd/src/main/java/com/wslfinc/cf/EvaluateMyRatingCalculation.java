package com.wslfinc.cf;

import com.wslfinc.cf.sdk.CodeForcesSDK;
import com.wslfinc.cf.sdk.entities.Contest;
import com.wslfinc.cf.sdk.entities.RatingChange;
import com.wslfinc.cf.sdk.entities.additional.ContestantResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Wsl_F
 */
public class EvaluateMyRatingCalculation {

  // 592
  public static void calculateRatingDiff(int minContestId, int maxContestId) {
    List<RatingDiff> difference = new ArrayList<>();
    int totalPredictions = 0;

    for (int contestId = minContestId; contestId <= maxContestId; contestId++) {
      Contest contest = CodeForcesSDK.getContest(contestId);
      /*if (contest.getType() == ContestType.CF) */
      {
        List<ContestantResult> predictedRating
          = CodeForcesSDK.getNewRatings(contestId);
        List<RatingChange> realRating
          = CodeForcesSDK.getRatingChanges(contestId);

        totalPredictions += realRating.size();
        for (ContestantResult rPredict : predictedRating) {
          for (RatingChange rReal : realRating) {
            if (rPredict.getHandle().equals(rReal.getHandle())) {
              if (rPredict.getNextRating() != rReal.getNewRating()) {
                RatingDiff rd
                  = new RatingDiff(contestId,
                  rPredict.getHandle(),
                  rPredict.getNextRating(),
                  rReal.getNewRating(),
                  rReal.getOldRating(),
                  rReal.getRank(),
                  rPredict.getSeed());

                difference.add(rd);
              }
              break;
            }
          }
        }
      }
    }

    outputStatistic(difference, totalPredictions);
  }

  private static void outputStatistic(List<RatingDiff> difference, int totalPredictions) {
    System.out.println("*************************************************");
    System.out.println("*************************************************");
    System.out.println("****     RATING   DIFFERENCE   STATISTIC     ****");
    System.out.println("*************************************************");
    System.out.println("*************************************************");

    System.out.println("Total predictions: " + totalPredictions);
    double p = difference.size() * 100;
    p /= totalPredictions;
    System.out.println("Wrong of them: " + difference.size()
      + "  ( " + p + " %)");

    int totalDiff = 0;
    int maxDiff = 0;
    for (RatingDiff rd : difference) {
      totalDiff += rd.diff;
      if (rd.diff > maxDiff) {
        maxDiff = rd.diff;
      }
    }

    System.out.println("Total sum of differences: " + totalDiff);
    System.out.println("Maximal difference: " + maxDiff);
    p = totalDiff;
    p /= difference.size();
    System.out.println("Average mistake: " + p);

    Collections.sort(difference);
    Collections.reverse(difference);

    System.out.println("\n");
    System.out.println("difference real previous predicted rank seed contestId handle");
    for (int i = 0; i < difference.size(); i++) {
      RatingDiff diff = difference.get(i);
      System.out.println(diff.diff + " " + diff.realRating + " "
        + diff.prevRating + " " + diff.predictedRating + " "
        + diff.rank + " " + diff.seed + " "
        + diff.contestId + " " + diff.handle);
    }

    System.out.println("\n");
    System.out.println("*************************************************");
    System.out.println("*************************************************");
    System.out.println("****            END     STATISTIC            ****");
    System.out.println("*************************************************");
    System.out.println("*************************************************");
  }
}
