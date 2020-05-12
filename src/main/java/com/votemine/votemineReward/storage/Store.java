package com.votemine.votemineReward.storage;

import com.votemine.votemineReward.models.PointsBalance;
import com.votemine.votemineReward.models.Transaction;

import java.util.List;

public interface Store {

    PointsBalance getBalance(String uuid);
    List<Transaction> getTransactions(String uuid);
    void insert(Transaction transaction);
}
