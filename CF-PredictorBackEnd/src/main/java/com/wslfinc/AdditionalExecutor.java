package com.wslfinc;

import com.wslfinc.cf.EvaluateMyRatingCalculation;
import static com.wslfinc.cf.sdk.Constants.*;
import com.wslfinc.cf.sdk.rating.PastRatingDownloader;
import com.wslfinc.cf.sdk.CodeForcesSDK;
import com.wslfinc.cf.sdk.entities.additional.*;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class AdditionalExecutor {

    public static void main(String[] args) throws Exception {
        //args = new String[]{"getPastRating", "767"};
        args = new String[]{"getPastRating", "767", "761"};
        //args = new String[]{"testRating", "750", "767"};//592
        //args = new String[]{"matchesIdToNames", "false"};

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
            case "matchesIdToNames":
                matchesIdToNames(args);
                break;
            default:
                System.out.println("Option \"" + args[0] + "\" hasn't recognized");
        }
    }

    public static void getPastRating(String[] args) {
        int maxId = Integer.valueOf(args[1]);
        int loadFromId = args.length == 3 ? Integer.valueOf(args[2]) : -1;
        boolean succes = 
                PastRatingDownloader.getRatingBeforeContest(loadFromId, maxId, PATH_TO_PROJECT + "/contests");
        if (succes) {
            System.out.println("Rating from past contests was successfully downloaded and processed!");
        } else {
            System.out.println("There were some troubles with dowloading and processing past rating");
        }
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
        EvaluateMyRatingCalculation.calculateRatingDiff(minId, maxId);
    }

    private static void matchesIdToNames(String[] args) {
        boolean gym = Boolean.getBoolean(args[1]);
        List<String> contests = CodeForcesSDK.getContestNames(gym);
        List<JSONObject> list = new ArrayList<>(contests.size());
        for (int contestId = contests.size() - 1; contestId > 0; contestId--) {
            JSONObject json = new JSONObject();
            json.put("contestId", contestId);
            json.put("name", contests.get(contestId));
            list.add(json);
        }

        JSONObject contestNames = new JSONObject();
        contestNames.put("status", "OK");
        contestNames.put("result", new JSONArray(list));

        String fileName = PATH_TO_PROJECT + "/contests/contestNames.json";
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            contestNames.write(writer);
            writer.write("\n");
        } catch (Exception ex) {
            System.err.println("Couldn't write contestNames\n"
                    + ex.getMessage());
        }
    }

}
