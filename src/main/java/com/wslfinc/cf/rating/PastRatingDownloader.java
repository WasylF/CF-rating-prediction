package com.wslfinc.cf.rating;

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
            rating.put(handle, new ArrayList<Integer>(currentMaxContestId));
        }

        ArrayList<Integer> ratings = rating.get(handle);
        ratings.ensureCapacity(currentMaxContestId);
        while (ratings.size() < currentMaxContestId) {
            ratings.add(prevRating);
        }

        ratings.set(currentMaxContestId - 1, nextRating);
    }

    public static boolean getRatingAfterContest(int maxContestId, String filePrefix) {
        rating = new TreeMap<>();
        currentMaxContestId = 0;
        ArrayList<Contest> contests = (ArrayList<Contest>) CodeForcesSDK.getContestsList(false);
        Collections.sort(contests);

        int n = contests.size();
        currentMaxContestId = Math.min(contests.get(n - 1).getId(), maxContestId);

        for (int i = 0; i < n; i++) {
            int contestId = contests.get(i).getId();
            if (contestId > maxContestId) {
                break;
            }
            List<RatingChange> ratingChanges = CodeForcesSDK.getRatingChanges(contestId);
            for (RatingChange ratingChange : ratingChanges) {
                addRatingChange(ratingChange);
            }
        }

        return writeToFiles(filePrefix);
    }

    private static JSONObject getRating(int contestId, Set<String> allHandles) {
        JSONObject document = new JSONObject();

        document.append("status", SUCCESSFUL_STATUS);
        LinkedList<JSONObject> list = new LinkedList<>();
        for (String handle : allHandles) {
            JSONObject user = new JSONObject();
            user.append("handle", handle);
            user.append("rating", rating.get(handle).get(contestId - 1));
            list.add(user);
        }
        document.append(JSON_RESULTS, new JSONArray(list));

        return document;
    }

    private static boolean writeToFiles(String filePrefix) {
        Set<String> allHandles = rating.keySet();
        boolean result = true;
        for (int i = 0; i < currentMaxContestId; i++) {
            try {
                String fileName = filePrefix + "/contest_" + (i + 1) + ".html";
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName));
                JSONObject json = getRating(i + 1, allHandles);
                json.write(writer);
                writer.write("\n");
                writer.close();
            } catch (Exception ex) {
                result = false;
            }
        }

        return result;
    }
}
