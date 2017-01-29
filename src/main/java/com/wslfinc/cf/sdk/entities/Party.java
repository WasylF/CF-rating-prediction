package com.wslfinc.cf.sdk.entities;

import com.wslfinc.cf.sdk.JsonExtractor;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class Party {

    /**
     * Integer. Id of the contest, in which party is participating.
     */
    int contestId;

    /**
     * List of Member objects. Members of the party.
     */
    List<Member> members;

    /**
     * Enum: CONTESTANT, PRACTICE, VIRTUAL, MANAGER, OUT_OF_COMPETITION.
     */
    ParticipantType participantType;

    /**
     * Integer. Can be absent. If party is a team, then it is a unique team id.
     * Otherwise, this field is absent.
     */
    int teamId;

    /**
     * String. Localized. Can be absent. If party is a team or ghost, then it is
     * a localized name of the team. Otherwise, it is absent.
     */
    String teamName;

    /**
     * Boolean. If true then this party is a ghost. It participated in the
     * contest, but not on Codeforces. For example, Andrew Stankevich Contests
     * in Gym has ghosts of the participants from Petrozavodsk Training Camp.
     */
    boolean ghost;

    /**
     * Integer. Can be absent. Room of the party. If absent, then the party has
     * no room.
     */
    int room;

    /**
     * Integer. Can be absent. Time, when this party started a contest.
     */
    long startTimeSeconds;

    public Party(int contestId, List<Member> members, ParticipantType participantType, int teamId, String teamName, boolean ghost, int room, long startTimeSeconds) {
        this.contestId = contestId;
        this.members = members;
        this.participantType = participantType;
        this.teamId = teamId;
        this.teamName = teamName;
        this.ghost = ghost;
        this.room = room;
        this.startTimeSeconds = startTimeSeconds;
    }

    public Party(JSONObject party) {
        setAll(party);
    }

    public final void setAll(JSONObject party) {
        this.contestId = JsonExtractor.getInt(party, "contestId");
        this.members = JsonExtractor.getMembers(party);
        this.participantType = ParticipantType.valueOf(JsonExtractor.getString(party, "participantType"));
        this.teamId = JsonExtractor.getInt(party, "teamId");
        this.teamName = JsonExtractor.getString(party, "teamName");
        this.ghost = JsonExtractor.getBoolean(party, "ghost");
        this.room = JsonExtractor.getInt(party, "room");
        this.startTimeSeconds = JsonExtractor.getLong(party, "startTimeSeconds");
    }

    public int getContestId() {
        return contestId;
    }

    public List<Member> getMembers() {
        return members;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public int getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public boolean isGhost() {
        return ghost;
    }

    public int getRoom() {
        return room;
    }

    public long getStartTimeSeconds() {
        return startTimeSeconds;
    }

}
