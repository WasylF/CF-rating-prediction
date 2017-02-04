package com.wslfinc;

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
        args = new String[]{"getNewRating", "744"};
        String option = args.length == 0 ? "getNewRating" : args[0];
        switch (option) {
            case "getPastRating":
                getPastRating(args);
                break;
            case "getNewRating":
                for (int i = 0; i < 5; i++) {
                    long start = System.nanoTime();
                    getNewRating(args);
                    long finish = System.nanoTime();
                    System.out.println("time: " + (finish - start));
                }
                break;
            default:
                System.out.println("Option \"" + option + "\" hasn't recognized");
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
}
