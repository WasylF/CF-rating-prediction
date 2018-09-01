package com.wslfinc.cf.sdk;

import com.wslfinc.cf.sdk.entities.Member;
import com.wslfinc.cf.sdk.entities.ProblemResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.wslfinc.cf.sdk.Constants.NEGATIVE_INFINITY;

/**
 * @author Wsl_F
 */
public class JsonExtractor {

  /**
   * Extracting String value from JSON object if it exists, otherwise
   * returning empty string
   *
   * @param obj  JSON object, could be NULL
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
   * @param obj  JSON object, could be NULL
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
   * @param obj  JSON object, could be NULL
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
   * @param obj  JSON object, could be NULL
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
   * @param obj  JSON object, could be NULL
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

  /**
   * Extracting {@code List<Memeber>} value from JSON object if it exists,
   * otherwise returning empty list
   *
   * @param party JSON object party, could be NULL
   * @return {@code List<Memeber>}
   */
  public static List<Member> getMembers(JSONObject party) {
    List<Member> value = new ArrayList<>();

    if (party != null && party.has("members")) {
      try {
        JSONArray array = party.getJSONArray("members");
        for (Object member : array) {
          value.add(new Member((JSONObject) member));
        }
      } catch (JSONException ex) {
        System.err.println("Couldn't extract members: "
          + " from JSON: " + party + "\n" + ex.getMessage());
        value = new ArrayList<>();
      }
    }

    return value;
  }

  /**
   * Extracting {@code List<problemResults>} value from JSON object if it
   * exists, otherwise returning empty list
   *
   * @param ranklistRow JSON object ranklistRow, could be NULL
   * @return {@code List<problemResults>}
   */
  public static List<ProblemResult> getProblemResults(JSONObject ranklistRow) {
    List<ProblemResult> value = new ArrayList<>();

    if (ranklistRow != null && ranklistRow.has("problemResults")) {
      try {
        JSONArray array = ranklistRow.getJSONArray("problemResults");
        for (Object probleResult : array) {
          value.add(new ProblemResult((JSONObject) probleResult));
        }
      } catch (JSONException ex) {
        System.err.println("Couldn't extract problemResults: "
          + " from JSON: " + ranklistRow + "\n" + ex.getMessage());
        value = new ArrayList<>();
      }
    }

    return value;
  }

}
