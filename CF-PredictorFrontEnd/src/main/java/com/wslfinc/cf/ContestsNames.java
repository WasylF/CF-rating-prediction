package com.wslfinc.cf;

import com.github.wslf.datastructures.cache.Cacheable;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Wsl_F
 */
public class ContestsNames extends Cacheable<JSONArray, Integer> {

  public ContestsNames() {
    // TTL 15 min
    super(1, 1, 15 * 60 * 1000);
  }

  @Override
  public JSONArray getValueManually(Integer id) {
    String requestURL = "https://codeforcescontests.github.io/RatingAfterRounds/contests/contestNames.html";
    try {
      JSONObject contestNames = JsonReader.read(requestURL);
      JSONArray contests = contestNames.getJSONArray("result");
      return contests;
    } catch (Exception ex) {
      System.err.println("Couldn't get request results " + requestURL
        + "\n" + ex.getMessage());
    }

    return null;
  }

}
