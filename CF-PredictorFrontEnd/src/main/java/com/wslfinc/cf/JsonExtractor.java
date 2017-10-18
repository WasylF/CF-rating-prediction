package com.wslfinc.cf;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class JsonExtractor {

    static int NEGATIVE_INFINITY = - 1_000_000_000;

    /**
     * Extracting String value from JSON object if it exists, otherwise
     * returning empty string
     *
     * @param obj JSON object, could be NULL
     * @param name filed name
     * @return String value or empty string
     */
    public static String getString(JSONObject obj, String name) {
        String value = "";
        if (obj != null && name != null && obj.has(name)) {
            try {
                value = obj.getString(name);
            } catch (JSONException ex) {
                System.err.println("Couldn't extract String parameter: "
                        + name + " from JSON: " + obj + "\n" + ex.getMessage());
                value = "";
            }
        }

        return value;
    }

    /**
     * Extracting int value from JSON object if it exists, otherwise returning
     * {@code NEGATIVE_INFINITY}
     *
     * @param obj JSON object, could be NULL
     * @param name filed name
     * @return int value
     */
    public static int getInt(JSONObject obj, String name) {
        int value = NEGATIVE_INFINITY;
        if (obj != null && name != null && obj.has(name)) {
            try {
                value = obj.getInt(name);
            } catch (JSONException ex) {
                System.err.println("Couldn't extract int parameter: "
                        + name + " from JSON: " + obj + "\n" + ex.getMessage());
                value = NEGATIVE_INFINITY;
            }
        }

        return value;
    }

    /**
     * Extracting long value from JSON object if it exists, otherwise returning
     * {@code NEGATIVE_INFINITY}
     *
     * @param obj JSON object, could be NULL
     * @param name filed name
     * @return long value
     */
    public static long getLong(JSONObject obj, String name) {
        long value = NEGATIVE_INFINITY;
        if (obj != null && name != null && obj.has(name)) {
            try {
                value = obj.getLong(name);
            } catch (JSONException ex) {
                System.err.println("Couldn't extract long parameter: "
                        + name + " from JSON: " + obj + "\n" + ex.getMessage());
                value = NEGATIVE_INFINITY;
            }
        }

        return value;
    }

    /**
     * Extracting boolean value from JSON object if it exists, otherwise
     * returning {@code FALSE}
     *
     * @param obj JSON object, could be NULL
     * @param name filed name
     * @return boolean value
     */
    public static boolean getBoolean(JSONObject obj, String name) {
        boolean value = false;
        if (obj != null && name != null && obj.has(name)) {
            try {
                value = obj.getBoolean(name);
            } catch (JSONException ex) {
                System.err.println("Couldn't extract long parameter: "
                        + name + " from JSON: " + obj + "\n" + ex.getMessage());
                value = false;
            }
        }

        return value;
    }

    /**
     * Extracting double value from JSON object if it exists, otherwise
     * returning {@code NEGATIVE_INFINITY}
     *
     * @param obj JSON object, could be NULL
     * @param name filed name
     * @return double value
     */
    public static double getDouble(JSONObject obj, String name) {
        double value = NEGATIVE_INFINITY;
        if (obj != null && name != null && obj.has(name)) {
            try {
                value = obj.getLong(name);
            } catch (JSONException ex) {
                System.err.println("Couldn't extract long parameter: "
                        + name + " from JSON: " + obj + "\n" + ex.getMessage());
                value = NEGATIVE_INFINITY;
            }
        }

        return value;
    }

}
