package com.votemine.votemineReward.storage;

import com.votemine.votemineReward.models.PointsBalance;
import com.votemine.votemineReward.models.Transaction;

import java.util.List;

public interface Storage {

    PointsBalance getBalance(String uuid);
    List<Transaction> getTransactions(String uuid);
    void insert(Transaction transaction);
}
