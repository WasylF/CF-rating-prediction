package com.wslfinc.cf.sdk.rating;

import static com.wslfinc.cf.sdk.Constants.*;
import com.wslfinc.cf.sdk.CodeForcesSDK;
import com.wslfinc.cf.sdk.entities.Contest;
import com.wslfinc.cf.sdk.entities.RatingChange;
import com.wslfinc.cf.sdk.entities.additional.ContestantResult;
import com.wslfinc.helpers.web.JsonReader;
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

    public static boolean getRatingBeforeContest(int maxId, String filePrefix) {
        boolean result = getRatingBeforeContest(-1, maxId, filePrefix);
        return validate(maxId, filePrefix) && result;
    }

    public static TreeMap<String, Integer> loadRatingFromId(int loadContestId, String filePrefix) {
        if (loadContestId > 0) {
            try {
                String path = "file://" + getFileName("" + loadContestId, filePrefix);
                JSONObject json = JsonReader.read(path);
                return new TreeMap<>(toMap(json));
            } catch (Exception ex) {
                System.err.println(ex.toString());
            }
        }

        return new TreeMap<>();
    }

    public static boolean getRatingBeforeContest(int loadFromId, int maxId, String filePrefix) {
        List<Contest> contests = CodeForcesSDK.getFinishedContests(maxId, false);
        TreeMap<String, Integer> rating = loadRatingFromId(loadFromId, filePrefix);
        boolean loaded = false;

        boolean result = true;
        int n = contests.size();
        for (int i = 0; i < n; i++) {
            int contestId = contests.get(i).getId();
            loaded |= contestId == loadFromId;
            if (!loaded) {
                continue;
            }
            System.out.println("Writing rating before contest: " + contestId);
            if (!writeToFiles(filePrefix, rating, "" + contestId)) {
                result = false;
                System.err.println("Couldn't write rating after contest " + contestId);
            }
            addFromCF(contestId, rating);
            //addFromPredictor(contestId, rating);
        }

        result &= writeToFiles(filePrefix, rating, "current");
        return result;
    }

    private static void addFromCF(int contestId, TreeMap<String, Integer> rating) {
        List<RatingChange> ratingChanges = CodeForcesSDK.getRatingChanges(contestId);
        for (RatingChange ratingChange : ratingChanges) {
            rating.put(ratingChange.getHandle(), ratingChange.getNewRating());
        }
    }

    private static void addFromPredictor(int contestId, TreeMap<String, Integer> rating) {
        List<ContestantResult> ratingChanges = CodeForcesSDK.getNewRatings(contestId);
        for (ContestantResult ratingChange : ratingChanges) {
            rating.put(ratingChange.getHandle(), ratingChange.getNextRating());
        }
    }

    private static JSONObject toJSON(TreeMap<String, Integer> rating) {
        List<JSONObject> list = new ArrayList<>(rating.size());

        for (String handle : rating.keySet()) {
            JSONObject contestant = new JSONObject();
            contestant.put("handle", handle);
            contestant.put("rating", rating.get(handle));
            list.add(contestant);
        }

        JSONObject contest = new JSONObject();
        contest.put("status", SUCCESSFUL_STATUS);
        contest.put(JSON_RESULTS, new JSONArray(list));

        return contest;
    }

    private static String getFileName(String contestId, String filePrefix) {
        return filePrefix + "/contest_" + contestId + ".html";
    }

    private static boolean writeToFiles(String filePrefix, TreeMap<String, Integer> rating, String contestId) {
        boolean result = true;
        String fileName = getFileName(contestId, filePrefix);
        JSONObject json = toJSON(rating);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            json.write(writer, 2, 0);
            writer.write("\n");
        } catch (Exception ex) {
            System.err.println("Couldn't write past rating to the file\n"
                    + ex.getMessage());
            result = false;
        }

        return result;
    }

    private static Map<String, Integer> toMap(JSONObject json) {
        if (!json.has("status") || !json.has(JSON_RESULTS)) {
            return new HashMap<>();
        }

        Map<String, Integer> ratings = new HashMap<>();
        JSONArray ratingsArray = json.getJSONArray(JSON_RESULTS);
        for (Object rating : ratingsArray) {
            JSONObject r = (JSONObject) rating;
            ratings.put(r.getString("handle"), r.getInt("rating"));
        }

        return ratings;
    }

    private static boolean validate(int maxId, String filePrefix) {
        boolean result = true;
        /*
        List<Contest> contests = CodeForcesSDK.getFinishedContests(maxId, false);
        for (Contest contest : contests) {
            int contestId = contest.getId();
            String path = "file://" + getFileName("" + contestId, filePrefix);
            try {
                JSONObject json = JsonReader.read(path);
                Map<String, Integer> rating = toMap(json);
                List<RatingChange> ratingChanges = CodeForcesSDK.getRatingChanges(contestId);
                for (RatingChange ratingChange : ratingChanges) {
                    int prevRating = ratingChange.getOldRating();
                    if (prevRating != INITIAL_RATING) {
                        String handle = ratingChange.getHandle();
                        if (!rating.containsKey(handle)
                                || rating.get(handle) != prevRating) {
                            System.err.println("Wrong rating on contest: "
                                    + contestId + " handle: " + handle);
                            result = false;
                        }
                    }
                }
            } catch (Exception ex) {
                System.err.println("Couldn't read file " + path + "\n" + ex.getMessage());
                result = false;
            }
        }
         */
        return result;
    }
}
