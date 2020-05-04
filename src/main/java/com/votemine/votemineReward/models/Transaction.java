package com.votemine.votemineReward.models;

import java.sql.Timestamp;

public interface Transaction {

    String of();
    int getPoints();
    Timestamp at();
    String reason();
}
