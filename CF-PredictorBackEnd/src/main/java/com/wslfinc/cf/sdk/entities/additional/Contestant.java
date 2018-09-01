package com.wslfinc.cf.sdk.entities.additional;

import com.wslfinc.cf.sdk.JsonExtractor;
import org.json.JSONObject;

/**
 * @author Wsl_F
 */
public class Contestant implements Comparable<Contestant> {

  private static final int DEFAULT_RANK = -1;
  /**
   * Codeforces handle of user.
   */
  String handle;
  /**
   * User's current rank on contest. {@code DEFAULT_RANK} = -1 = unknown =
   * doesn't participate
   */
  int rank;
  /**
   * User's rating before contest.
   */
  int prevRating;

  public String getHandle() {
    return handle;
  }

  public void setHandle(String handle) {
    this.handle = handle;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public int getPrevRating() {
    return prevRating;
  }

  public void setPrevRating(int prevRating) {
    this.prevRating = prevRating;
  }

  public final void setAll(String handle, int rank, int prevRating) {
    this.handle = handle;
    this.rank = rank;
    this.prevRating = prevRating;
  }

  public final void setAll(JSONObject contestant) {
    this.handle = JsonExtractor.getString(contestant, "handle");
    this.prevRating = JsonExtractor.getInt(contestant, "rating");
    this.rank = Math.max(JsonExtractor.getInt(contestant, "rank"), DEFAULT_RANK);
  }

  public Contestant(String handle, int rank, int prevRating) {
    setAll(handle, rank, prevRating);
  }

  public Contestant(String handle, int prevRating) {
    this(handle, DEFAULT_RANK, prevRating);
  }

  public Contestant(JSONObject contestant) {
    setAll(contestant);
  }

  @Override
  public int compareTo(Contestant o) {
    if (this.prevRating == o.prevRating) {
      return this.handle.compareTo(o.handle);
    }
    return this.prevRating < o.prevRating ? -1 : 1;
  }
}
