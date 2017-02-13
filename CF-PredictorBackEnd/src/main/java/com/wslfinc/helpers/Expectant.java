package com.wslfinc.helpers;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author Wsl_F
 */
public class Expectant {

    /**
     *
     * @param time_ms time in milliseconds
     * @return successfulness
     */
    public static boolean delay(int time_ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(time_ms);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

}
