package com.wslfinc.cf.sdk.entities;

/**
 * @author Wsl_F
 */
public enum SubmissionType {

  /**
   * If type is PRELIMINARY then points can decrease (if, for example,
   * solution will fail during system test).
   */
  PRELIMINARY,
  /**
   * party can only increase points for this problem by submitting better
   * solutions.
   */
  FINAL
}
