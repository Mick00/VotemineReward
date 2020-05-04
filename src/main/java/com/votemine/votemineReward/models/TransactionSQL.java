package com.votemine.votemineReward.models;

import java.sql.Timestamp;

public class TransactionSQL implements Transaction {

    private String uuid;
    private int points;
    private Timestamp time;
    private String reason;

    public TransactionSQL(String uuid, int points, String reason) {
        this.uuid = uuid;
        this.points = points;
        this.reason = reason;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public TransactionSQL(String uuid, int points, String reason, Timestamp time) {
        this.uuid = uuid;
        this.points = points;
        this.time = time;
        this.reason = reason;
    }

    @Override
    public String of() {
        return uuid;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public Timestamp at() {
        return time;
    }

    @Override
    public String reason() {
        return reason;
    }
}
