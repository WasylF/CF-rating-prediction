package com.wslfinc.cf;

import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class Constants {

    public static final String BACK_END_URL
            = //"https://cf-predictor-backend.herokuapp.com";
            "http://cf-predictor-backend.us-west-2.elasticbeanstalk.com";

    public static final String JSON_FAIL_STRING = "{ \"status\" : \"FAIL\" }";

    public static final JSONObject JSON_FAIL = new JSONObject(JSON_FAIL_STRING);
}
