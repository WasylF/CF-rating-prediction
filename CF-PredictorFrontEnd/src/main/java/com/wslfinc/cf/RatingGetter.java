package com.wslfinc.cf;

import com.github.wslf.utils.web.WebReader;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.wslfinc.cf.Constants.GITHUB_RATING_URL;

/**
 * @author Wsl_F
 */
public class RatingGetter {

  private static final RatingChanges cache = new RatingChanges();
  private static WebReader webReader = new WebReader();

  public static JSONObject getNewRatingJSON(int contestId) {
    return new JSONObject(getNewRatingString(contestId));
  }

  public static String getNewRatingString(int contestId) {
    //List<Integer> active = ContestProcessor.getActiveContestIds();

    String ghUrl = GITHUB_RATING_URL + "/nextRating/contest_" + contestId + ".html";
    if (webReader.isExists(ghUrl)) {
      try {
        return webReader.read(ghUrl);
      } catch (IOException ex) {
        System.err.println("Couldn't get next rating from github: " + ex.getMessage());
      }
    }

    return cache.getValue(contestId);
    //return JSON_FAIL_STRING;
  }

  public static List<Integer> getCachedIds() {
    return new ArrayList<>(cache.getCachedKeys());
  }

  public static void removeFromCache(int contestId) {
    cache.remove(contestId);
  }
}
