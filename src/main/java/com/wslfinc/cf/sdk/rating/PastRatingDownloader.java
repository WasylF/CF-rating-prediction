package com.wslfinc.cf.sdk.rating;

import static com.wslfinc.Constants.*;
import com.wslfinc.cf.sdk.CodeForcesSDK;
import com.wslfinc.cf.sdk.entities.Contest;
import com.wslfinc.cf.sdk.entities.RatingChange;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class is responsible for getting from codeforces all users rating for
 * all previous contests.
 *
 * @author Wsl_F
 */
public class PastRatingDownloader {

    private static Map<String, ArrayList<Integer>> rating;
    private static int currentMaxContestId;

    private static void addRatingChange(RatingChange ratingChange) {
        String handle = ratingChange.getHandle();
        int prevRating = ratingChange.getOldRating();
        int nextRating = ratingChange.getNewRating();

        if (!rating.containsKey(handle)) {
            rating.put(handle, new ArrayList<Integer>(currentMaxContestId + 1));
        }

        addRating(handle, prevRating, nextRating);
    }

    private static ArrayList<Integer> addRating(String handle, int prevRating, int nextRating) {
        ArrayList<Integer> ratings = rating.get(handle);
        ratings.ensureCapacity(currentMaxContestId + 1);
        while (ratings.size() <= currentMaxContestId) {
            ratings.add(prevRating);
        }
        ratings.set(currentMaxContestId, nextRating);
        return ratings;
    }

    private static void addIfNotExists(int maxContestId) {
        currentMaxContestId = maxContestId;

        for (String handle : rating.keySet()) {
            ArrayList<Integer> r = rating.get(handle);
            int curRating = r.get(r.size() - 1);
            addRating(handle, curRating, curRating);
        }
    }

    public static boolean getRatingAfterContest(int maxContestId, String filePrefix) {
        rating = new TreeMap<>();
        currentMaxContestId = 0;
        ArrayList<Contest> contests = (ArrayList<Contest>) CodeForcesSDK.getContestsList(false);
        Collections.sort(contests);

        int n = contests.size();

        for (int i = 0; i < n; i++) {
            int contestId = contests.get(i).getId();
            if (contestId > maxContestId) {
                break;
            }

            if (CodeForcesSDK.isFinished(contestId)) {
                currentMaxContestId = Math.max(contests.get(i).getId(), currentMaxContestId);
                List<RatingChange> ratingChanges = CodeForcesSDK.getRatingChanges(contestId);
                for (RatingChange ratingChange : ratingChanges) {
                    addRatingChange(ratingChange);
                }
            }
        }

        addIfNotExists(maxContestId);
        return writeToFiles(filePrefix);
    }

    private static JSONObject getRating(int contestId, Set<String> allHandles) {
        JSONObject document = new JSONObject();

        document.put("status", SUCCESSFUL_STATUS);
        LinkedList<JSONObject> list = new LinkedList<>();
        for (String handle : allHandles) {
            JSONObject user = new JSONObject();
            user.put("handle", handle);
            user.put("rating", rating.get(handle).get(contestId));
            list.add(user);
        }
        document.put(JSON_RESULTS, new JSONArray(list));

        return document;
    }

    private static boolean writeToFiles(String filePrefix) {
        Set<String> allHandles = rating.keySet();
        boolean result = true;
        for (int i = 0; i <= currentMaxContestId; i++) {
            try {
                String fileName = filePrefix + "/contest_" + i + ".html";
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName));
                JSONObject json = getRating(i, allHandles);
                json.write(writer);
                writer.write("\n");
                writer.close();
            } catch (Exception ex) {
                System.err.println("Couldn't write past rating to the files\n"
                        + ex.getMessage());
                result = false;
            }
        }

        return result;
    }
}
