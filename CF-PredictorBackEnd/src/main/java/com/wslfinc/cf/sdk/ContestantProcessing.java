package com.wslfinc.cf.sdk;

import static com.wslfinc.cf.sdk.CodeForcesSDK.*;
import static com.wslfinc.cf.sdk.Constants.*;
import com.wslfinc.cf.sdk.entities.*;
import com.wslfinc.cf.sdk.entities.additional.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wsl_F
 */
public class ContestantProcessing {
    
    private static ContestantsCached allContestants = new ContestantsCached();

    /**
     * IT'S NOT OFFICIAL CODEFORCES API!!!
     *
     * Getting rating of all contestants just before round № {@code contestId}
     *
     * @param contestId Id of the contest. It is not the round number. It can be
     * seen in contest URL. {@code 1 <= contestId <= MAXIMAL_CONTEST_ID}
     * @return List of Contestant. Rank field of every contestant contains
     * default value (-1).
     */
    static List<Contestant> getAllContestants(int contestId) {
        return allContestants.getValue(contestId);
    }

    /**
     * IT'S NOT OFFICIAL CODEFORCES API!!!
     *
     * Getting rating of all contestants just before round № {@code contestId}
     *
     * @param contestId Id of the contest. It is not the round number. It can be
     * seen in contest URL. {@code 1 <= contestId <= MAXIMAL_CONTEST_ID}
     * @return List of Contestants that particapating in contest
     * #{@code contestId}. All fields of Contestant are filled.
     */
    @Deprecated
    static List<Contestant> getActiveContestants(int contestId) {
        List<Contestant> registredContestants = getAllContestants(contestId);
        List<RanklistRow> rows = getRanklistRows(contestId);
        
        Map<String, Integer> prevRating = new HashMap<>();
        for (Contestant contestant : registredContestants) {
            prevRating.put(contestant.getHandle(), contestant.getPrevRating());
        }
        
        return getActiveContestants(registredContestants, prevRating, rows);
    }
    
    static ArrayList<Team> getActiveTeams(int contestId) {
        List<Contestant> registredContestants = getAllContestants(contestId);
        List<RanklistRow> rows = getRanklistRows(contestId);
        
        Map<String, Integer> prevRating = new HashMap<>();
        for (Contestant contestant : registredContestants) {
            prevRating.put(contestant.getHandle(), contestant.getPrevRating());
        }
        
        return getActiveTeams(contestId, registredContestants, prevRating, rows);
    }
    
    @Deprecated
    static List<Contestant> getActiveContestants(List<Contestant> oldCOntestants,
            Map<String, Integer> prevRating, List<RanklistRow> rows) {
        List<Contestant> active = new ArrayList<>();
        for (RanklistRow row : rows) {
            for (Member member : row.getParty().getMembers()) {
                String handle = member.getHandle();
                if (!prevRating.containsKey(handle)) {
                    prevRating.put(handle, INITIAL_RATING);
                }
                
                int prevR = prevRating.get(handle);
                int rank = row.getRank();
                active.add(new Contestant(handle, rank, prevR));
            }
        }
        
        return active;
    }
    
    static ArrayList<Team> getActiveTeams(int contestId, List<Contestant> oldCOntestants,
            Map<String, Integer> prevRating, List<RanklistRow> rows) {
        ArrayList<Team> active = new ArrayList<>();
        for (RanklistRow row : rows) {
            ArrayList<Contestant> temates = new ArrayList<>();
            for (Member member : row.getParty().getMembers()) {
                String handle = member.getHandle();
                if (!prevRating.containsKey(handle)) {
                    prevRating.put(handle, INITIAL_RATING);
                }
                
                int prevR = prevRating.get(handle);
                int rank = row.getRank();
                if (ContestProcessing.isEducational(contestId) && prevR >= 1900) {
                    temates.clear();
                    break;
                }
                temates.add(new Contestant(handle, rank, prevR));
            }
            
            if (temates.size() > 0) {
                String name = temates.size() == 1 ? temates.get(0).getHandle()
                        : row.getParty().getTeamName();
                active.add(new Team(temates, name));
            }
        }
        
        return active;
    }
    
}
