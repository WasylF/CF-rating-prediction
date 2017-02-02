package com.wslfinc.cf.sdk.entities;

import com.wslfinc.cf.sdk.JsonExtractor;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class Contest implements Comparable<Contest> {

    /**
     * Integer.
     */
    int id;

    /**
     * String. Localized.
     */
    String name;

    /**
     * Enum: CF, IOI, ICPC. Scoring system used for the contest.
     */
    ContestType type;

    /**
     * Enum: BEFORE, CODING, PENDING_SYSTEM_TEST, SYSTEM_TEST, FINISHED.
     */
    ContestPhase phase;

    /**
     * Boolean. If true, then the ranklist for the contest is frozen and shows
     * only submissions, created before freeze.
     */
    boolean frozen;

    /**
     * Integer. Duration of the contest in seconds.
     */
    int durationSeconds;

    /**
     * Integer. Can be absent. Contest start time in unix format.
     */
    long startTimeSeconds;

    /**
     * Integer. Can be absent. Number of seconds, passed after the start of the
     * contest. Can be negative.
     */
    int relativeTimeSeconds;

    /**
     * String. Can be absent. Handle of the user, how created the contest.
     */
    String preparedBy;

    /**
     * String. Can be absent. URL for contest-related website.
     */
    String websiteUrl;

    /**
     * String. Localized. Can be absent.
     */
    String description;

    /**
     * Integer. Can be absent. From 1 to 5. Larger number means more difficult
     * problems.
     */
    int difficulty;

    /**
     * String. Localized. Can be absent. Human-readable type of the contest from
     * the following categories: Official ACM-ICPC Contest, Official School
     * Contest, Opencup Contest, School/University/City/Region Championship,
     * Training Camp Contest, Official International Personal Contest, Training
     * Contest.
     */
    String kind;

    /**
     * String. Localized. Can be absent. Name of the ICPC Region for official
     * ACM-ICPC contests.
     */
    String icpcRegion;

    /**
     * String. Localized. Can be absent.
     */
    String country;

    /**
     * String. Localized. Can be absent.
     */
    String city;

    /**
     * String. Can be absent.
     */
    String season;

    public Contest(int id) {
        this.id = id;
    }
    
    public Contest(int id, String name, ContestType type, ContestPhase phase, boolean frozen, int durationSeconds, int startTimeSeconds, int relativeTimeSeconds, String preparedBy, String websiteUrl, String description, int difficulty, String kind, String icpcRegion, String country, String city, String season) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.phase = phase;
        this.frozen = frozen;
        this.durationSeconds = durationSeconds;
        this.startTimeSeconds = startTimeSeconds;
        this.relativeTimeSeconds = relativeTimeSeconds;
        this.preparedBy = preparedBy;
        this.websiteUrl = websiteUrl;
        this.description = description;
        this.difficulty = difficulty;
        this.kind = kind;
        this.icpcRegion = icpcRegion;
        this.country = country;
        this.city = city;
        this.season = season;
    }

    public Contest(JSONObject contest) {
        setAll(contest);
    }

    public final void setAll(JSONObject contest) {
        this.id = JsonExtractor.getInt(contest, "id");
        this.name = JsonExtractor.getString(contest, "name");
        this.type = ContestType.valueOf(JsonExtractor.getString(contest, "type"));
        this.phase = ContestPhase.valueOf(JsonExtractor.getString(contest, "phase"));
        this.frozen = JsonExtractor.getBoolean(contest, "frozen");
        this.durationSeconds = JsonExtractor.getInt(contest, "durationSeconds");
        this.startTimeSeconds = JsonExtractor.getLong(contest, "startTimeSeconds");
        this.relativeTimeSeconds = JsonExtractor.getInt(contest, "relativeTimeSeconds");
        this.preparedBy = JsonExtractor.getString(contest, "preparedBy");
        this.websiteUrl = JsonExtractor.getString(contest, "websiteUrl");
        this.description = JsonExtractor.getString(contest, "description");
        this.difficulty = JsonExtractor.getInt(contest, "difficulty");
        this.kind = JsonExtractor.getString(contest, "kind");
        this.icpcRegion = JsonExtractor.getString(contest, "icpcRegion");
        this.country = JsonExtractor.getString(contest, "country");
        this.city = JsonExtractor.getString(contest, "city");
        this.season = JsonExtractor.getString(contest, "season");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContestType getType() {
        return type;
    }

    public void setType(ContestType type) {
        this.type = type;
    }

    public ContestPhase getPhase() {
        return phase;
    }

    public void setPhase(ContestPhase phase) {
        this.phase = phase;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public long getStartTimeSeconds() {
        return startTimeSeconds;
    }

    public void setStartTimeSeconds(long startTimeSeconds) {
        this.startTimeSeconds = startTimeSeconds;
    }

    public int getRelativeTimeSeconds() {
        return relativeTimeSeconds;
    }

    public void setRelativeTimeSeconds(int relativeTimeSeconds) {
        this.relativeTimeSeconds = relativeTimeSeconds;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getIcpcRegion() {
        return icpcRegion;
    }

    public void setIcpcRegion(String icpcRegion) {
        this.icpcRegion = icpcRegion;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    @Override
    public int compareTo(Contest contest) {
        if (this.id == contest.id) {
            return 0;
        }

        if (this.startTimeSeconds == contest.startTimeSeconds) {
            return this.id < contest.id ? -1 : 1;
        }

        return this.startTimeSeconds < contest.startTimeSeconds ? -1 : 1;
    }

}
