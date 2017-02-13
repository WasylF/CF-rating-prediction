package com.wslfinc.cf;

/**
 *
 * @author Wsl_F
 */
public class CalculatingStraigth implements Runnable {

    final RatingExpection object;
    final int contestId;

    public CalculatingStraigth(RatingExpection object, int contestId) {
        this.object = object;
        this.contestId = contestId;
    }

    @Override
    public void run() {
        object.updateValueInCache(contestId);
    }

}
