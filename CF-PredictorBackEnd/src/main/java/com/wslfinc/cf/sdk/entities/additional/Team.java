package com.wslfinc.cf.sdk.entities.additional;

import java.util.ArrayList;

/**
 *
 * @author Wsl_F
 */
public class Team implements Comparable<Team>{

    Contestant[] contestants;
    int rank;
    int prevRating;
    String name;

    public Team(ArrayList<Contestant> teamates, String name) {
        this(teamates.toArray(new Contestant[teamates.size()]), name);
    }
    
    public Team(Contestant[] contestants, String name) {
        this.name = name;
        this.contestants = contestants;
        calcRating();
        rank = contestants[0].rank;
    }

    private static double getEloWinProbability(double ra, double rb) {
        return 1.0 / (1 + Math.pow(10, (rb - ra) / 400.0));
    }

    private void calcRating() {
        if (contestants.length == 1) {
            prevRating = contestants[0].prevRating;
            return;
        }
        
        double left = 1;
        double right = 10000;

        for (int tt = 0; tt < 20; tt++) {
            double r = (left + right) / 2.0;
            double rWinsProbability = 1.0;
            for (Contestant teamate : contestants) {
                rWinsProbability *= getEloWinProbability(r, teamate.prevRating);
            }
            double rating = Math.log10(1 / (rWinsProbability) - 1) * 400 + r;

            if (rating > r) {
                left = r;
            } else {
                right = r;
            }
        }

        prevRating = (int)Math.round((left + right) / 2.0);
    }

    @Override
    public int compareTo(Team o) {
        if (this.prevRating == o.prevRating) {
            return this.contestants[0].handle.compareTo(o.contestants[0].handle);
        }
        return this.prevRating < o.prevRating ? -1 : 1;
    }

    public Contestant[] getContestants() {
        return contestants;
    }

    public int getRank() {
        return rank;
    }

    public int getPrevRating() {
        return prevRating;
    }

    public void setRank(int rank) {
        this.rank = rank;
        for (Contestant contestant : contestants) {
            contestant.setRank(rank);
        }
    }

    public String getName() {
        return name;
    }

}
