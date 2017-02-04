package com.wslfinc;

import com.wslfinc.cf.TestMyRatingCalculation;
import static com.wslfinc.cf.sdk.Constants.*;
import com.wslfinc.cf.sdk.rating.PastRatingDownloader;
import com.wslfinc.cf.sdk.CodeForcesSDK;
import com.wslfinc.cf.sdk.entities.additional.*;

import java.util.List;

/**
 *
 * @author Wsl_F
 */
public class AdditionalExecutor {

    public static void main(String[] args) throws Exception {
        args = new String[]{"getNewRating", "614"};
        //args = new String[]{"testRating", "592", "764"};

        switch (args[0]) {
            case "getPastRating":
                getPastRating(args);
                break;
            case "getNewRating":
                getNewRating(args);
                break;
            case "testRating":
                testMyRating(args);
                break;
            default:
                System.out.println("Option \"" + args[0] + "\" hasn't recognized");
        }
    }

    public static void getPastRating(String[] args) {
        int maxId = Integer.valueOf(args[1]);
        PastRatingDownloader.getRatingAfterContest(maxId, PATH_TO_PROJECT + "/contests");
    }

    public static void getNewRating(String[] args) {
        int contestId = Integer.valueOf(args[1]);
        List<ContestantResult> newRatings = CodeForcesSDK.getNewRatings(contestId);
        System.out.println("Handle Rank Seed PrevRating NextRating");
        for (ContestantResult cr : newRatings) {
            System.out.println(cr.getHandle() + " " + cr.getRank() + " "
                    + cr.getSeed() + " " + cr.getPrevRating() + " " + cr.getNextRating());
        }
    }

    private static void testMyRating(String[] args) {
        int minId = Integer.valueOf(args[1]);
        int maxId = Integer.valueOf(args[2]);
        TestMyRatingCalculation.calculateRatingDif(minId, maxId);
    }
}
