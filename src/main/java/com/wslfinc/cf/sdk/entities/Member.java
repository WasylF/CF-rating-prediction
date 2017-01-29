package com.wslfinc.cf.sdk.entities;

import com.wslfinc.cf.sdk.JsonExtractor;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class Member {

    /**
     * String. Codeforces user handle.
     */
    String handle;

    public Member(String handle) {
        this.handle = handle;
    }

    public Member(JSONObject member) {
        this.handle = JsonExtractor.getString(member, "handle");
    }

    public String getHandle() {
        return handle;
    }

}
