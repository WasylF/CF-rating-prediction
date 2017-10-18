package com.wslfinc.cf.sdk.entities;

import com.wslfinc.cf.sdk.JsonExtractor;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class User {

    /**
     * String. Codeforces user handle.
     */
    String handle;

    /**
     * String. Shown only if user allowed to share his contact info.
     */
    String email;

    /**
     * String. User id for VK social network. Shown only if user allowed to
     * share his contact info.
     */
    String vkId;

    /**
     * String. Shown only if user allowed to share his contact info.
     */
    String openId;

    /**
     * String. Localized. Can be absent.
     */
    String firstName;

    /**
     * String. Localized. Can be absent.
     */
    String lastName;

    /**
     * String. Localized. Can be absent.
     */
    String country;

    /**
     * String. Localized. Can be absent.
     */
    String city;

    /**
     * String. Localized. Can be absent.
     */
    String organization;

    /**
     * Integer. User contribution.
     */
    int contribution;

    /**
     * String. Localized.
     */
    String rank;

    /**
     * Integer.
     */
    int rating;

    /**
     * String. Localized.
     */
    String maxRank;

    /**
     * Integer.
     */
    int maxRating;

    /**
     * Integer. Time, when user was last seen online, in unix format.
     */
    long lastOnlineTimeSeconds;

    /**
     * Integer. Time, when user was registered, in unix format.
     */
    long registrationTimeSeconds;

    /**
     * Integer. Amount of users who have this user in friends.
     */
    int friendOfCount;

    /**
     * String. User's avatar URL.
     */
    String avatar;

    /**
     * String. User's title photo URL.
     */
    String titlePhoto;

    public User(String handle, String email, String vkId, String openId, String firstName, String lastName, String country, String city, String organization, int contribution, String rank, int rating, String maxRank, int maxRating, long lastOnlineTimeSeconds, long registrationTimeSeconds, int friendOfCount, String avatar, String titlePhoto) {
        this.handle = handle;
        this.email = email;
        this.vkId = vkId;
        this.openId = openId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
        this.organization = organization;
        this.contribution = contribution;
        this.rank = rank;
        this.rating = rating;
        this.maxRank = maxRank;
        this.maxRating = maxRating;
        this.lastOnlineTimeSeconds = lastOnlineTimeSeconds;
        this.registrationTimeSeconds = registrationTimeSeconds;
        this.friendOfCount = friendOfCount;
        this.avatar = avatar;
        this.titlePhoto = titlePhoto;
    }

    public User(JSONObject user) {
        setAll(user);
    }

    public final void setAll(JSONObject user) {
        this.handle = JsonExtractor.getString(user, "handle");
        this.email = JsonExtractor.getString(user, "email");
        this.vkId = JsonExtractor.getString(user, "vkId");
        this.openId = JsonExtractor.getString(user, "openId");
        this.firstName = JsonExtractor.getString(user, "firstName");
        this.lastName = JsonExtractor.getString(user, "lastName");
        this.country = JsonExtractor.getString(user, "country");
        this.city = JsonExtractor.getString(user, "city");
        this.organization = JsonExtractor.getString(user, "organization");
        this.contribution = JsonExtractor.getInt(user, "contribution");
        this.rank = JsonExtractor.getString(user, "rank");
        this.rating = JsonExtractor.getInt(user, "rating");
        this.maxRank = JsonExtractor.getString(user, "maxRank");
        this.maxRating = JsonExtractor.getInt(user, "maxRating");
        this.lastOnlineTimeSeconds = JsonExtractor.getLong(user, "lastOnlineTimeSeconds");
        this.registrationTimeSeconds = JsonExtractor.getLong(user, "registrationTimeSeconds");
        this.friendOfCount = JsonExtractor.getInt(user, "friendOfCount");
        this.avatar = JsonExtractor.getString(user, "avatar");
        this.titlePhoto = JsonExtractor.getString(user, "titlePhoto");
    }

    public String getHandle() {
        return handle;
    }

    public String getEmail() {
        return email;
    }

    public String getVkId() {
        return vkId;
    }

    public String getOpenId() {
        return openId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getOrganization() {
        return organization;
    }

    public int getContribution() {
        return contribution;
    }

    public String getRank() {
        return rank;
    }

    public int getRating() {
        return rating;
    }

    public String getMaxRank() {
        return maxRank;
    }

    public int getMaxRating() {
        return maxRating;
    }

    public long getLastOnlineTimeSeconds() {
        return lastOnlineTimeSeconds;
    }

    public long getRegistrationTimeSeconds() {
        return registrationTimeSeconds;
    }

    public int getFriendOfCount() {
        return friendOfCount;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getTitlePhoto() {
        return titlePhoto;
    }

}
