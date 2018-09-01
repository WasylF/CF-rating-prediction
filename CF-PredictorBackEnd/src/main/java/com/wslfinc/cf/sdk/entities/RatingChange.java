package com.wslfinc.cf.sdk.entities;

import com.wslfinc.cf.sdk.JsonExtractor;
import org.json.JSONObject;

/**
 * @author Wsl_F
 */
public class RatingChange {

  /**
   * Integer.
   */
  int contestId;

  /**
   * String. Localized.
   */
  String contestName;

  /**
   * String. Codeforces user handle.
   */
  String handle;

  /**
   * Integer. Place of the user in the contest. This field contains user rank
   * on the moment of rating update. If afterwards rank changes (e.g. someone
   * get disqualified), this field will not be update and will contain old
   * rank.
   */
  int rank;

  /**
   * Integer. Time, when rating for the contest was update, in unix-format.
   */
  long ratingUpdateTimeSeconds;

  /**
   * Integer. User rating before the contest.
   */
  int oldRating;

  /**
   * Integer. User rating after the contest.
   */
  int newRating;

  public RatingChange(int contestId, String contestName, String handle, int rank, long ratingUpdateTimeSeconds, int oldRating, int newRating) {
    this.contestId = contestId;
    this.contestName = contestName;
    this.handle = handle;
    this.rank = rank;
    this.ratingUpdateTimeSeconds = ratingUpdateTimeSeconds;
    this.oldRating = oldRating;
    this.newRating = newRating;
  }

  public RatingChange(JSONObject ratingChange) {
    setAll(ratingChange);
  }

  public final void setAll(JSONObject ratingChange) {
    this.contestId = JsonExtractor.getInt(ratingChange, "contestId");
    this.contestName = JsonExtractor.getString(ratingChange, "contestName");
    this.handle = JsonExtractor.getString(ratingChange, "handle");
    this.rank = JsonExtractor.getInt(ratingChange, "rank");
    this.ratingUpdateTimeSeconds = JsonExtractor.getLong(ratingChange, "ratingUpdateTimeSeconds");
    this.oldRating = JsonExtractor.getInt(ratingChange, "oldRating");
    this.newRating = JsonExtractor.getInt(ratingChange, "newRating");
  }

  public int getContestId() {
    return contestId;
  }

  public String getContestName() {
    return contestName;
  }

  public String getHandle() {
    return handle;
  }

  public int getNewRating() {
    return newRating;
  }

  public int getOldRating() {
    return oldRating;
  }

  public int getRank() {
    return rank;
  }

  public long getRatingUpdateTimeSeconds() {
    return ratingUpdateTimeSeconds;
  }

}
