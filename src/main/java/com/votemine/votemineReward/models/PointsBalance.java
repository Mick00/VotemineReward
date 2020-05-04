package com.votemine.votemineReward.models;

public interface PointsBalance {

    int get();
    void add(int nPoints);
    void add(int nPoints, String reason);
    void remove(int points);
    void remove(int points, String reason);
}
