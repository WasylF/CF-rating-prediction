package com.wslfinc.cf;

/**
 * @author Wsl_F
 */
public class RatingDiff implements Comparable<RatingDiff> {

  int contestId;
  String handle;
  int predictedRating;
  int realRating;
  int diff;
  int prevRating;
  int rank;
  double seed;

  public RatingDiff(int contestId, String handle, int preditedRating, int realRating, int prevRating, int rank, double seed) {
    this.contestId = contestId;
    this.handle = handle;
    this.predictedRating = preditedRating;
    this.realRating = realRating;
    this.prevRating = prevRating;
    this.rank = rank;
    this.seed = seed;
    this.diff = Math.abs(preditedRating - realRating);
  }

  @Override
  public int compareTo(RatingDiff rd) {
    if (this.diff == rd.diff) {
      return this.handle.compareTo(rd.handle);
    }

    return this.diff < rd.diff ? -1 : 1;
  }

}
